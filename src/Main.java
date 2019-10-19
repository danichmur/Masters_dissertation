import java.util.*;

public class Main {

    private static Set<Set<Integer>> findCliques(List<Set<Integer>> adjacencyList) {
        Set<Integer> p = new HashSet<>();
        for (int i = 0; i < adjacencyList.size(); i++) {
            p.add(i);
        }
        return findCliques(new HashSet<>(), p, new HashSet<>(), adjacencyList);
    }
    private static boolean Flag = false;

    private static Set<Set<Integer>> findCliques(Set<Integer> clique, Set<Integer> p, Set<Integer> x, List<Set<Integer>> adjacencyList) {
        Set<Set<Integer>> result = new HashSet<>();
        if (p.isEmpty() && x.isEmpty()) {
            result.add(clique);
            if(clique.size() <= 13) Flag = true;
        }
        Set<Integer> intersectionPX = new HashSet<>(p);
        intersectionPX.addAll(x);
        int u = -1;  // u is the pivot
        int max = 0; // max is degree of pivot
        for (Integer c : intersectionPX) {
            int m = 0;
            for (Integer v : adjacencyList.get(c)) {
                if (intersectionPX.contains(v)) m++;
            }
            if (m > max){
                max = m;
                u = c;
            }
        }
        Set<Integer> differenceP = new HashSet<>(p);

        if (u != -1){
            differenceP.removeAll(adjacencyList.get(u));
        }

        for (Integer v : differenceP) {
            Set<Integer> newClique = new HashSet<>(clique);
            newClique.add(v);
            Set<Integer> pIntersection = new HashSet<>(p);
            Set<Integer> xIntersection = new HashSet<>(x);
            pIntersection.retainAll(adjacencyList.get(v));
            xIntersection.retainAll(adjacencyList.get(v));
            //if (Flag) continue;
            result.addAll(findCliques(newClique, pIntersection, xIntersection, adjacencyList));
            p.remove(v);
            x.add(v);
        }
        return result;
    }

    public static void main(String[] args) {
        TestGraph g = generateTestGraph2();
        FourTimesLinkedList l = new FourTimesLinkedList(g.getAdjustedList());

        System.out.println(findCliques(g.getAdjustedList()));
        System.out.println(l.findCliquesWithStructure());
    }

    static TestGraph generateTestGraph3() {
        int v1 = 0;
        int v2 = 1;
        int v3 = 2;
        int v4 = 3;

        int[] vertices = {v1, v2, v3, v4};

        TestGraph g = new TestGraph(vertices.length);


        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v1, v4);

        g.addEdge(v2, v3);
        g.addEdge(v2, v4);

        g.addEdge(v3, v4);

        return g;
    }

    static TestGraph generateTestGraph2() {
        int v1 = 0;
        int v2 = 1;
        int v3 = 2;
        int v4 = 3;
        int v5 = 4;
        int v6 = 5;

        int[] vertices = {v1, v2, v3, v4, v5, v6};

        TestGraph g = new TestGraph(vertices.length);

        /*
            1 -- 2
            \ 5 /
             \ /
              3 --- 4
               \   /
                 6
        */

        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v1, v5);

        g.addEdge(v2, v3);
        g.addEdge(v2, v5);

        g.addEdge(v3, v4);
        g.addEdge(v3, v5);
        g.addEdge(v3, v6);

        g.addEdge(v4, v6);

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