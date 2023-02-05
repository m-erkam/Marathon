import java.util.Comparator;

public class Node implements Comparator<Node> {         // Helper node method to compare the nodes while putting them in priority queue
    public String name;
    public int cost;

    public Node(){

    }
    public Node(String name, int cost){
        this.name = name;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.cost < node2.cost) {
            return -1;
        }else if (node2.cost < node1.cost){
            return 1;
        }
        return 0;
    }


}
