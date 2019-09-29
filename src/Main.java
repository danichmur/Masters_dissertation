import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {


    public static void main(String[] args) {
        TestGraph g = generateTestGraph2();

        SuperAdjustedList s = new SuperAdjustedList(g.getAdjustedList());
    }

    static TestGraph generateTestGraph() {

        int v1 = 0;
        int v2 = 1;
        int v3 = 2;
        int v4 = 3;
        int v5 = 4;
        int v6 = 6;
        int v7 = 7;
        int v8 = 8;

        int[] vertices = {v1, v2, v3, v4, v5, v6, v7, v8};

        TestGraph g = new TestGraph(vertices.length);


        /*     8            7
              / \          / \
            1 -- 2 ----- 5 -- 6
            \   /            /
             \ /            /
              3 ---------- 4
        */


        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v2, v5);
        g.addEdge(v3, v2);
        g.addEdge(v3, v4);
        g.addEdge(v5, v6);
        g.addEdge(v6, v4);
        g.addEdge(v7, v5);
        g.addEdge(v7, v6);

        g.addEdge(v8, v1);
        g.addEdge(v8, v2);

        return g;

    }

    static TestGraph generateTestGraph2() {
        int v1 = 0;
        int v2 = 1;
        int v3 = 2;
        int v4 = 3;

        int[] vertices = {v1, v2, v3, v4};

        TestGraph g = new TestGraph(vertices.length);

        /*
            1 -- 2 -- 4
            \   /
             \ /
              3
        */

        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v2, v3);
        g.addEdge(v2, v4);

        return g;
    }

    static class TestGraph {

        private List<Set<Integer>> adjacencyList;

        List<Set<Integer>> getAdjustedList() {
            return adjacencyList;
        }

        TestGraph(int verticesCount) {

            adjacencyList = new ArrayList();

            for (int i = 0; i < verticesCount; i++) {
                adjacencyList.add(new HashSet());
            }
        }

        void addEdge(int i, int j) {
            adjacencyList.get(i).add(j);
            adjacencyList.get(j).add(i);
        }
    }
}