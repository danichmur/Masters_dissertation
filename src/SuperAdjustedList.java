import java.util.Arrays;
import java.util.List;

public class SuperAdjustedList {
    private Vertex[][] Ms;
    private List<Vertex>[] Ls;

    public SuperAdjustedList(TestGraphElement[] adjustedList) {
        int N = adjustedList.length;
        //M and L for each vertex
        Ms = new Vertex[N][N];
        Ls = new List[N];

        // all vertices
        for (int i = 0; i < N; i++) {
            Ls[i] = adjustedList[i].getNeighbors();
            Vertex[] M = Ms[i];
            // all vertices[i] edges
            for (Vertex v : Ls[i]) {
                M[v.getIdx()] = v;
            }
        }
    }

    public boolean isAdjusted(Vertex v1, Vertex v2) {
        return Ms[v1.getIdx()][v2.getIdx()] != null;
    }

    //TODO remove
    void printlnArrays() {
        System.out.println("Ms");
        for (int i = 0; i < Ms.length; i++) {
            System.out.println(i + " " + Arrays.toString(Ms[i]));
        }
        System.out.println("Ls");
        for (int i = 0; i < Ls.length; i++) {
            System.out.println(i + " " + Ls[i]);
        }
    }
}
