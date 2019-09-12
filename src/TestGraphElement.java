import java.util.ArrayList;
import java.util.List;

//Just a pair
public class TestGraphElement {
    public Vertex getVertex() {
        return vertex;
    }

    private List<Vertex> neighbors;
    private Vertex vertex;

    public List<Vertex> getNeighbors() {
        return neighbors;
    }


    public TestGraphElement(Vertex vertex) {
        this.vertex = vertex;
        neighbors = new ArrayList();
    }

    public void addNeighbour(Vertex neighbour) {
        neighbors.add(neighbour);
    }
}
