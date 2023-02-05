import java.util.*;

public class Graph {
    public int vertexNo;        // I use a graph to store the adjacents
    HashMap<String, ArrayList<Map.Entry<String, Integer>>> adjacencyList;

    public Graph(int vertexNo){
        this.vertexNo = vertexNo;
    }

    public int shortestPath(String start, String endNode){      // Dijkstra algorithm with priority queue implementation
        Map<String, Integer>distances = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(vertexNo, new Node());
        HashSet<String> visited = new HashSet<>();
        for (String node : adjacencyList.keySet()){     // First, it arranges all distances to infinity
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.offer(new Node(start, 0));        // It puts the starting node to the queue

        while (!queue.isEmpty()){           // In while loop, it take the node with minimum distance to the current node and process it

            Node node = queue.poll();
            if (visited.contains(node.name)){
                continue;
            }
            if (node.name.equals(endNode)){
                return distances.get(endNode);
            }
            visited.add(node.name);

            process(node, queue, visited, distances);

        }

        return distances.get(endNode);


    }

    public void process(Node node, PriorityQueue<Node> queue, HashSet<String> visited, Map<String, Integer> distances){
        for (Map.Entry<String, Integer> neighbor : adjacencyList.get(node.name)){       // If new distance is less than old distance, it updates it
            if (!visited.contains(neighbor.getKey())){
                int edge = neighbor.getValue();
                int newDistance = distances.get(node.name) + edge;
                if (newDistance < distances.get(neighbor.getKey())){
                    distances.put(neighbor.getKey(), newDistance);
                    queue.offer(new Node(neighbor.getKey(), newDistance));
                }
            }
        }
    }
    public Node shortestPathFlag(HashSet<String> flags, String start){      // While finding the shortest path between flags, it does similar things
        Map<String, Integer>distances = new HashMap<>();                    // It stops when it reaches a flag and takes it
        PriorityQueue<Node> queue = new PriorityQueue<>(vertexNo, new Node());
        HashSet<String> visited = new HashSet<>();

        Node end = new Node();
        flags.remove(start);
        for (String node : adjacencyList.keySet()){
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        queue.offer(new Node(start, 0));
        while (!queue.isEmpty()){

            Node node = queue.poll();

            if (visited.contains(node.name)){           // It checks whether it is in the visited or flag list
                continue;
            }
            if (flags.contains(node.name)){
                end =node;
                return end;
            }
            visited.add(node.name);
            process(node, queue, visited, distances);

        }
        return end;
    }

    public int findFlag(HashSet<String> flags, String start, int distance){
        if (flags.size() == 1){
            return distance;
        }

        Node node = shortestPathFlag(flags, start);         // When it reach e flag, it put a way between the source and the reached flag

        if (node.name == null){     // If it cant find a node, it means there is no way between them
            return -1;
        }

        int min = node.cost;
        adjacencyList.get(start).add(new AbstractMap.SimpleEntry<>(node.name, 0));
        adjacencyList.get(node.name).add(new AbstractMap.SimpleEntry<>(start, 0));
        distance += min;
        return findFlag(flags, node.name, distance);
    }

}
