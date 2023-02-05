import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class project4 {

        public static void main(String[] args) throws IOException {

            FileReader input = new FileReader(args[0]);
//            FileReader input = new FileReader("C:/Users/M.Erkam/Desktop/Erkam/2. Sınıf 1. Dönem/" +
//                    "Cmpe250/Projects/Project4/testcases/input/inp9.in");
            BufferedReader readFile = new BufferedReader(input);    // It takes input
            FileWriter output = new FileWriter(args[1]);
//            FileWriter output = new FileWriter("C:/Users/M.Erkam/Desktop/output.txt");
//            double startTime = System.nanoTime();
            int pointNo = Integer.parseInt(readFile.readLine().split(" ")[0]);
            int flagNo = Integer.parseInt(readFile.readLine().split(" ")[0]);
            String[] start_end = readFile.readLine().split(" ");
            String start= start_end[0];
            String end = start_end[1];
            String[] flags_array = readFile.readLine().split(" ");

            Graph graph = new Graph(pointNo);
            graph.adjacencyList = new HashMap<>();


            while (readFile.ready()){       // It puts the inputs in adjacency list and create a list if it is absent
                String a = readFile.readLine();

                String[] adjacents = a.split(" ");
                String name = adjacents[0];

                graph.adjacencyList.computeIfAbsent(name, k -> new ArrayList<>((adjacents.length-1)/2));


                for (int i = 1; i < adjacents.length - 1;){
                    int value = Integer.parseInt(adjacents[i+1]);

                    graph.adjacencyList.computeIfAbsent(adjacents[i], k -> new ArrayList<>((adjacents.length-1)/2));

                    graph.adjacencyList.get(name).add(new AbstractMap.SimpleEntry<>(adjacents[i], value));

                    graph.adjacencyList.get(adjacents[i]).add(new AbstractMap.SimpleEntry<>(name, value));

                    i += 2;
                }
            }

            HashSet<String> flags = new HashSet<>(Arrays.asList(flags_array));  // It put the flags in a hashset

            String flagStart = "";
            for (String node : flags){
                flagStart = node;
                break;

            }

            int shortestPath = graph.shortestPath(start, end);
            if (shortestPath != Integer.MAX_VALUE){
                output.write(shortestPath + "\n");
            }else{
                output.write(-1 + "\n");
            }

            int flag = graph.findFlag(flags, flagStart, 0);
            output.write(flag + "\n");
//            double endTime = System.nanoTime();
//            System.out.println((endTime-startTime)/Math.pow(10, 9));
            output.close();
        }


}


