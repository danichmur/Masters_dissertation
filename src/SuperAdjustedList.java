import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SuperAdjustedList {
    private Vertex[][] Ms;
    private List<Vertex>[] Ls;
    private DoublyLinkedList[] B;
    private final int SORTED_SIZE = 20;

    public SuperAdjustedList(GraphElement[] adjustedList) {
        GraphElement[] sortedList =  bucketSort(adjustedList, SORTED_SIZE);
        fillML(adjustedList);
        fillB(adjustedList);
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
    }

    private GraphElement[] bucketSort(GraphElement[] intArr, int noOfBuckets){
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

        return intArr;
    }

    // TODO
    // A very simple hash function
    private int hash(int num) {
        return num / SORTED_SIZE;
    }
}
