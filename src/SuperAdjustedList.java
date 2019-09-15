import java.util.*;
import java.util.stream.Collectors;

public class SuperAdjustedList {
    private Vertex[][] Ms;
    private List<VertexWithMarker>[] M2; //TODO
    private List<Vertex>[] Ls;
    private DoublyLinkedList[] B;
    private final int SORTED_SIZE = 20;

    public SuperAdjustedList(GraphElement[] adjustedList) {
        //bucketSort(adjustedList, SORTED_SIZE);
        fillML(adjustedList);
        fillB(adjustedList);
        fillM2(adjustedList);
    }

    private void fillM2(GraphElement[] adjustedList) {
        int N = Ls.length;
        M2 = new List[N];

        //TODO find a better way
        for (int i = 0; i < N; i++) {
            M2[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            if (Ls[i].size() == 0) {
                continue;
            }
            List<Vertex> currentL = new ArrayList<>(Ls[i]);
            Vertex fakeFirst = new Vertex(0, "", null);
            //TODO: +2?
            Vertex fakeLast = new Vertex(Ls[i].get(Ls[i].size() - 1).getIdx() + 2, "", null);

            currentL.add(0, fakeFirst);
            currentL.add(fakeLast);

            for (int j = 1; j + 1 < currentL.size(); j++){
                Vertex v = currentL.get(j);

                int iIdx = currentL.get(j - 1).getIdx();
                int jIdx = v.getIdx();
                int kIdx = currentL.get(j + 1).getIdx();

                if (iIdx + 1 < jIdx && jIdx < kIdx - 1) {
                    M2[i].add(new VertexWithMarker(adjustedList[jIdx - 1].getVertex(), -1));
                    M2[i].add(new VertexWithMarker(adjustedList[jIdx + 1].getVertex(), 1));
                } else if (iIdx + 1 < jIdx && jIdx == kIdx - 1) {
                    M2[i].add(new VertexWithMarker(adjustedList[jIdx - 1].getVertex(), -1));
                } else if (iIdx + 1 == jIdx && jIdx < kIdx - 1) {
                    M2[i].add(new VertexWithMarker(adjustedList[jIdx + 1].getVertex(), 1));
                }
//                else if (iIdx + 1 == jIdx && jIdx == kIdx - 1) {
//                    //TODO ?
//                }
            }

            M2[i].add(0, new VertexWithMarker(fakeFirst, 1));
            M2[i].add(new VertexWithMarker(fakeLast, -1));
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

        B = new DoublyLinkedList[N];

        //TODO find a better way
        for (int i = 0; i < N; i++) {
            B[i] = new DoublyLinkedList<>();
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
        List<Integer> neighbors =  new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            VertexWithMarker vm1 = list.get(i);
            VertexWithMarker vm2 = list.get(i + 1);
            if (vm1.marker == 1 && vm2.marker == -1) {
                for (int j = vm1.v.getIdx(); j < vm2.v.getIdx() + 1; j ++) {
                    neighbors.add(j);
                }
            }
        }
        //neighbors.remove(0);
        neighbors.remove(neighbors.size() - 1);
        neighbors = neighbors.stream().distinct().collect(Collectors.toList());
        return neighbors;
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
        System.out.println("B");
        for (int i = 0; i < B.length; i++) {
            System.out.println(i + " " + B[i]);
        }
        System.out.println("M2");
        for (int i = 0; i < M2.length; i++) {
            System.out.println(i + " " + M2[i]);
        }
    }

    private void bucketSort(GraphElement[] intArr, int noOfBuckets){
        List<GraphElement>[] buckets = new List[noOfBuckets];

        for(int i = 0; i < noOfBuckets; i++){
            buckets[i] = new LinkedList<>();
        }

        for(GraphElement num : intArr){
            buckets[hash(num.getVertex().getIdx())].add(num);
        }

        for(List<GraphElement> bucket : buckets){
            Collections.sort(bucket);
        }

        int i = 0;

        for(List<GraphElement> bucket : buckets){
            for(GraphElement num : bucket){
                intArr[i++] = num;
            }
        }
    }

    // TODO
    // A very simple hash function
    private int hash(int num) {
        return num;
    }

    private class VertexWithMarker {
        Vertex v;
        int marker; // 1, -1

        VertexWithMarker(Vertex v, int marker) {
            this.v = v;
            this.marker = marker;
        }

        @Override
        public String toString() {
            return "(" + v.getIdx() + ", " + marker + ")";
        }
    }
}
