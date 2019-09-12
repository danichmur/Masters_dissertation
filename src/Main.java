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

        Vertex[] vertices = {v1, v2, v3, v4, v5, v6, v7};

        TestGraph g = new TestGraph(vertices);


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
        TestGraph g = generateTestGraph();
        TestGraphElement[] adjustedList = g.getAdjustedList();

        SuperAdjustedList s = new SuperAdjustedList(adjustedList);

        int N = adjustedList.length;

        DoublyLinkedList<Vertex>[] B = new DoublyLinkedList[N];


        //TODO sort lists in A

        //TODO find a better way
        for (int i = 0; i < N; i++) {
            B[i] = new DoublyLinkedList<>();
        }

        for (int i = 0; i < N; i++) {
            List N_ai = adjustedList[i].getNeighbors();

            int degV = N_ai.size();

            for (int k = 0; k < degV; k++) {
                Vertex J = (Vertex) N_ai.get(k);
                DoublyLinkedList<Vertex> N_bi = B[J.getIdx()];
                N_bi.addLast(adjustedList[i].getVertex());
            }

        }

        for (int i = 0; i < B.length; i++) {
            System.out.println(i + " " + B[i]);
        }
    }
}