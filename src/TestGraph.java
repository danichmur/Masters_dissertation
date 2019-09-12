import java.util.LinkedList;

public class TestGraph {

    public LinkedList[] getAdjustedList() {
        return adjustedList;
    }

    private LinkedList[] adjustedList;

    public TestGraph(int vertices) {
        adjustedList = new LinkedList[vertices];

        for (int i = 0; i < vertices; i++)
            adjustedList[i] = new LinkedList();
    }

    public void addEdge(Vertex src, Vertex dest) {
        adjustedList[src.getIdx()].add(dest);
    }
}
