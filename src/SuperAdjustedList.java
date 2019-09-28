import java.util.*;
import java.util.stream.Collectors;

public class SuperAdjustedList {
    private Vertex[][] Ms;
    private List<VertexWithMarker>[] M2; //TODO
    private List<Vertex>[] Ls;

    private LinkedList[] B;
    private FourTimesLinkedList N;

    public SuperAdjustedList(GraphElement[] adjustedList) {
        fillML(adjustedList);
        fillM2(adjustedList);
        fillB(adjustedList);
        N = new FourTimesLinkedList(Ls);
    }

    private void fillM2(GraphElement[] adjustedList) {
        int N = Ls.length;
        M2 = new List[N];

        VertexWithMarker fakeFirst = new VertexWithMarker(0, 1);
        VertexWithMarker fakeLast = new VertexWithMarker(N, -1);


        for (int i = 0; i < N; i++) {
            //TODO review
            M2[i] = new ArrayList<>(N + 2);
            if (Ls[i].size() == 0) {
                continue;
            }

            for (int j = 0; j < Ls[i].size(); j++) {
                Vertex v = Ls[i].get(j); //TODO

                int iIdx = -1;

                if (j > 0) {
                    iIdx = Ls[i].get(j - 1).getIdx();
                }

                int jIdx = v.getIdx();

                int kIdx = N;

                if (j != Ls[i].size() - 1) {
                    kIdx = Ls[i].get(j + 1).getIdx();
                }

                if (iIdx + 1 < jIdx && jIdx < kIdx - 1) {
                    M2[i].add(new VertexWithMarker(jIdx - 1, -1));
                    M2[i].add(new VertexWithMarker(jIdx + 1, 1));
                } else if (iIdx + 1 < jIdx && jIdx == kIdx - 1) {
                    M2[i].add(new VertexWithMarker(jIdx - 1, -1));
                } else if (iIdx + 1 == jIdx && jIdx < kIdx - 1) {
                    M2[i].add(new VertexWithMarker(jIdx + 1, 1));
                }
            }

            M2[i].add(0, fakeFirst);
            M2[i].add(fakeLast);
        }
    }

    private void fillML(GraphElement[] adjustedList) {
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

    private void fillB(GraphElement[] adjustedList) {
        int N = adjustedList.length;

        B = new LinkedList[N];

        //TODO find a better way
        for (int i = 0; i < N; i++) {
            B[i] = new LinkedList();
        }

        for (GraphElement e : adjustedList) {
            List<Vertex> N_ai = e.getNeighbors();
            for (Vertex j : N_ai) {
                B[j.getIdx()].addLast(e.getVertex());
            }

        }
    }

    public boolean isAdjusted(Vertex v1, Vertex v2) {
        return Ms[v1.getIdx()][v2.getIdx()] != null;
    }

    public List<Integer> getNeighborsFromComplementGraph(Vertex v) {
        List<VertexWithMarker> list = M2[v.getIdx()];
        List<Integer> neighbors = new ArrayList<>(Ms.length);
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
