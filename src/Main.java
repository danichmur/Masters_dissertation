import java.lang.reflect.Array;
import java.util.*;

public class Main {

    // TEST data
    private static  int[] intervals = {0, 2};

    static Vertex createVertex(int idx) {
        return new Vertex(idx, "KHD", intervals);
    }

    static TestGraph generateTestGraph() {

        Vertex v1 = createVertex(0);
        Vertex v2 = createVertex(1);
        Vertex v3 = createVertex(2);
        Vertex v4 = createVertex(3);
        Vertex v5 = createVertex(4);
        Vertex v6 = createVertex(5);
        Vertex v7 = createVertex(6);
        Vertex v8 = createVertex(7);
        Vertex v9 = createVertex(8);
        Vertex v10 = createVertex(9);
        Vertex v11 = createVertex(10);

        Vertex[] vertices = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11};

        TestGraph g = new TestGraph(vertices);


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

    public static void main(String[] args) {
        TestGraph g = generateTestGraph();

        SuperAdjustedList s = new SuperAdjustedList(g.getAdjustedList());

        s.printlnArrays();
        System.out.println(s.getNeighborsFromComplementGraph(createVertex(0)));

    }
}