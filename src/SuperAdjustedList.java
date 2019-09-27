import java.util.*;
import java.util.stream.Collectors;

public class SuperAdjustedList {
    private Vertex[][] Ms;
    private List<VertexWithMarker>[] M2; //TODO
    private List<Vertex>[] Ls;
    private DoublyLinkedList[] B;
    private final int SORTED_SIZE = 20;

    public SuperAdjustedList(GraphElement[] adjustedList) {
        fillML(adjustedList);
        //bucketSort(Ls, SORTED_SIZE);

        fillDoublyLinkedListB(adjustedList);
        fillM2(adjustedList);
        fillB();
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

                if(j > 0){
                    iIdx = Ls[i].get(j - 1).getIdx();
                }

                int jIdx = v.getIdx();

                int kIdx = N ;

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

    private void fillDoublyLinkedListB(GraphElement[] adjustedList) {
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
        List<Integer> neighbors = new ArrayList<>(Ms.length);
        for (int i = 0; i < list.size() - 1; i++) {
            VertexWithMarker vm1 = list.get(i);
            VertexWithMarker vm2 = list.get(i + 1);
            if (vm1.marker == 1 && vm2.marker == -1) {
                for (int j = vm1.v; j < vm2.v + 1; j++) {
                    neighbors.add(j);
                }
            }else{
                neighbors.add(i);

            }
        }
        neighbors = neighbors.stream().distinct().collect(Collectors.toList());

        neighbors.remove(0);
        neighbors.remove(neighbors.size() - 1);

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

    private void bucketSort(GraphElement[] intArr, int noOfBuckets) {
        List<GraphElement>[] buckets = new List[noOfBuckets];

        for (int i = 0; i < noOfBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }

        for (GraphElement num : intArr) {
            buckets[hash(num.getVertex().getIdx())].add(num);
        }

        for (List<GraphElement> bucket : buckets) {
            Collections.sort(bucket);
        }

        int i = 0;

        for (List<GraphElement> bucket : buckets) {
            for (GraphElement num : bucket) {
                intArr[i++] = num;
            }
        }
    }


    private void fillB(){

        final int N = Ls.length;

        ElementForB[][] B = new ElementForB[N][N];
        HeaderN[] nHorizontal = new HeaderN[N];
        HeaderN[] nVertical = new HeaderN[N];

        for (int i = 0; i  < N; i++) {
            List<Vertex> lsi = Ls[i];

            nHorizontal[i] = new HeaderN(N);
            nVertical[i] = new HeaderN(N);

            ElementForB bPrevious = null;

            for (Vertex v : lsi) {

                int j = v.getIdx();

                ElementForB bHorizontal = new ElementForB(i, j);

                if (i > j) {
                    bPrevious = bHorizontal;
                    //The matrix is symmetrical, so there is no need to check fill i > j cases
                    continue;
                }

                ElementForB bVertical = new ElementForB(j, i);

                if (bPrevious == null) {

                    //TODO
                    //link first with start

                } else {
                    ElementForB bPreviousReverse = B[bPrevious.getVerticalW()][bPrevious.getHorizontalW()];

                    bHorizontal.setLeft(bPrevious);
                    bPrevious.setRight(bHorizontal);
//                    bVertical.setLeft(bPreviousReverse);
//                    bPreviousReverse.setRight(bVertical);


                    bVertical.setUp(bPreviousReverse);
                    bPreviousReverse.setDown(bVertical);
//                    bHorizontal.setUp(bPrevious);
//                    bPrevious.setDown(bHorizontal);

                }
                
                B[i][j] = bHorizontal;
                B[j][i] = bVertical;

                System.out.println("{left, right, up, down}");
                for(int i1 = 0; i1  < Ls.length; i1++){
                    for(int j1 = 0; j1  < Ls.length; j1++){
                        System.out.printf("%-50s ", B[i1][j1]);
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();

                bPrevious = bHorizontal;
            }
        }

        System.out.println("{left, right, up, down}");
        for(int i = 0; i  < Ls.length; i++){
            for(int j = 0; j  < Ls.length; j++){
                System.out.printf("%-50s ", B[i][j]);
            }
            System.out.println();
        }
    }



    // TODO
    // A very simple hash function
    private int hash(int num) {
        return num;
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
