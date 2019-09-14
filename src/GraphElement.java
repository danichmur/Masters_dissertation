import java.util.List;

public interface GraphElement extends Comparable<GraphElement> {
    List<Vertex> getNeighbors();
    Vertex getVertex();
}
