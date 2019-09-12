import java.util.*;

public class Main {

    // TEST data
    private static  int[] intervals = {0, 2};

    static Vertex createVertex(int idx) {
        return new Vertex(idx, "KHD", intervals);
    }

    static TestGraph generatTestGraph() {
        TestGraph g = new TestGraph(7);
        Vertex v1 = createVertex(0);
        Vertex v2 = createVertex(1);
        Vertex v3 = createVertex(2);
        Vertex v4 = createVertex(3);
        Vertex v5 = createVertex(4);
        Vertex v6 = createVertex(5);
        Vertex v7 = createVertex(6);

        /*                  7
                           / \
            1 -- 2 ----- 5 -- 6
            \   /            /
             \ /            /
              3 ---------- 4
        */

        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v2, v1);
        g.addEdge(v2, v3);
        g.addEdge(v2, v5);
        g.addEdge(v3, v1);
        g.addEdge(v3, v2);
        g.addEdge(v3, v4);
        g.addEdge(v4, v3);
        g.addEdge(v4, v6);
        g.addEdge(v5, v2);
        g.addEdge(v5, v6);
        g.addEdge(v6, v4);
        g.addEdge(v6, v5);
        g.addEdge(v7, v6);
        g.addEdge(v7, v7);

        return g;
    }

    public static void main(String[] args) {
        TestGraph g = generatTestGraph();
        LinkedList[] adjustedList = g.getAdjustedList();
        SuperAdjustedList s = new SuperAdjustedList(adjustedList);
        s.printlnArrays();


        //System.out.println(s.isAdjusted(v1, v2));
    }
}