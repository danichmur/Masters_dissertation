import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SuperAdjustedList {
    private List<List<Vertex>> Ms;
    private List<List<Vertex>> Ls;

    public SuperAdjustedList(LinkedList[] adjustedList) {
        int N = adjustedList.length;
        //M and L for each vertex
        Ms = new ArrayList<>(N);
        Ls = new ArrayList<>(N);

        //TODO find a better way
        for (int i = 0; i < N; i++) {
            Ls.add(new ArrayList<>());
            Ms.add(new ArrayList<>());
            List<Vertex> M = Ms.get(i);
            for (int j = 0; j < N; j++) {
                M.add(null);
            }
        }

        // all vertices
        for (int i = 0; i < N; i++) {
            Ls.set(i, adjustedList[i]);
            List<Vertex> M = Ms.get(i);
            // all vertices[i] edges
            for (Vertex v : Ls.get(i)) {
                M.set(v.getIdx(), v);
            }
        }
    }

    public boolean isAdjusted(Vertex v1, Vertex v2) {
        return Ms.get(v1.getIdx()).get(v2.getIdx()) != null;
    }

    //TODO remove
    void printlnArrays() {
        System.out.println("Ms");
        for (int i = 0; i < Ms.size(); i++){
            System.out.println(i + " " + Ls.get(i));
        }
        System.out.println("Ls");
        for (int i = 0; i < Ls.size(); i++){
            System.out.println(i + " " + Ls.get(i));
        }
    }
}
