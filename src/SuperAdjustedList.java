import java.util.*;
import java.util.stream.Collectors;

public class SuperAdjustedList {
    int[][] adjacencyMatrix;
    private List<Set<VertexWithMarker>> M2; //TODO
    private List<Set<Integer>> Ls;

    private LinkedList[] B;

    private FourTimesLinkedList N;

    public SuperAdjustedList(List<Set<Integer>> adjacencyList) {
        fillAdjacencyMatrix(adjacencyList);
        //fillM2(adjacencyList);
        fillB(adjacencyList);
        N = new FourTimesLinkedList(adjacencyList);
    }

    //TODO M2 is needed to supplement the graph, so I do not know if this is necessary
    private void fillM2(List<Set<Integer>> adjacencyList) {
        int N = adjacencyList.size();
        M2 = new ArrayList<>(N);

        VertexWithMarker fakeFirst = new VertexWithMarker(0, 1);
        VertexWithMarker fakeLast = new VertexWithMarker(N, -1);


        for (int i = 0; i < N; i++) {
            HashSet current = new HashSet<>(N + 2);
            current.add(0);

            if (adjacencyList.get(i).size() == 0) {
                continue;
            }

            for (int j = 0; j < Ls.get(i).size(); j++) {
//                int v = Ls.get(i).get(j); //TODO
//
//                int iIdx = -1;
//
//                if (j > 0) {
//                    iIdx = Ls[i].get(j - 1).getIdx();
//                }
//
//                int jIdx = v.getIdx();
//
//                int kIdx = N;
//
//                if (j != Ls[i].size() - 1) {
//                    kIdx = Ls[i].get(j + 1).getIdx();
//                }
//
//                if (iIdx + 1 < jIdx && jIdx < kIdx - 1) {
//                    M2[i].add(new VertexWithMarker(jIdx - 1, -1));
//                    M2[i].add(new VertexWithMarker(jIdx + 1, 1));
//                } else if (iIdx + 1 < jIdx && jIdx == kIdx - 1) {
//                    M2[i].add(new VertexWithMarker(jIdx - 1, -1));
//                } else if (iIdx + 1 == jIdx && jIdx < kIdx - 1) {
//                    M2[i].add(new VertexWithMarker(jIdx + 1, 1));
//                }
            }
            current.add(fakeLast);
            M2.set(i, current);
        }
    }

    public List<Integer> getNeighborsFromComplementGraph(int v) {
        List<VertexWithMarker> list = (List<VertexWithMarker>) M2.get(v);
        List<Integer> neighbors = new ArrayList<>(adjacencyMatrix.length);
        for (int i = 0; i < list.size() - 1; i++) {
            VertexWithMarker vm1 = list.get(i);
            VertexWithMarker vm2 = list.get(i + 1);
            if (vm1.marker == 1 && vm2.marker == -1) {
                for (int j = vm1.v; j < vm2.v + 1; j++) {
                    neighbors.add(j);
                }
            } else {
                neighbors.add(i);

            }
        }
        neighbors = neighbors.stream().distinct().collect(Collectors.toList());

        neighbors.remove(0);
        neighbors.remove(neighbors.size() - 1);

        return neighbors;
    }

    private void fillAdjacencyMatrix(List<Set<Integer>> adjacencyList) {
        int N = adjacencyList.size();
        adjacencyMatrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            int[] M = adjacencyMatrix[i];
            for (int v : adjacencyList.get(i)) {
                M[v] = v;
            }
        }
    }

    private void fillB(List<Set<Integer>> adjacencyList) {
        int N = adjacencyList.size();
        B = new LinkedList[N];

        for (int i = 0; i < N; i++) {
            B[i] = new LinkedList<>(adjacencyList.get(i));
        }
    }

    //TODO
    public boolean isAdjusted(int v1, int v2) {
        return adjacencyMatrix[v1][v2] != 0;
    }

    private class VertexWithMarker {
        int v;
        int marker; // 1, -1

        VertexWithMarker(int v, int marker) {
            this.v = v;
            this.marker = marker;
        }

        @Override
        public String toString() {
            return "(" + v + ", " + marker + ")";
        }
    }
}
