import java.util.*;

public class FourTimesLinkedList {

    private Header[] nRow;
    private Header[] nColumn;
    final private List<Set<Integer>> adjacencyList;
    private int[][] adjacencyMatrix;
    private final int VERTEX_DEGREE = 2; //19;
    private final int CLIQUE_SIZE = 3; //8;

    public FourTimesLinkedList(List<Set<Integer>> adjacencyList) {
        buildList(adjacencyList);
        this.adjacencyList = adjacencyList;
        fillAdjacencyMatrix(adjacencyList);
    }


    private void removeInnerEdges(int v, Set<Integer> clique) {
        //System.out.println("removeInnerEdges for " + v + ", with c = " + clique);
        Node n = nColumn[v].node;

        //TODO maybe we need to update link in the nColumn[v]
        while (n != null) {
            if (clique.contains(n.rowW)) {
                if (n.left != null && n.right != null) {
                    n.left.right = n.right;
                    n.right.left = n.left;
                } else if (n.left != null) {
                    nRow[n.rowW].node = null;
                } else if (n.right != null) {
                    n.right.left = null;
                    nRow[n.rowW].node = n.right;
                } else {
                    //no more vertices in this row
                    nRow[n.rowW].node = null;
                }
            }
            n = n.down;
        }
    }

    private boolean isAdjusted(int v1, int v2) {
        return adjacencyMatrix[v1][v2] != 0;
    }

    public List<Set<Integer>> findCliquesWithStructure() {
        List<Set<Integer>> cliques = new ArrayList<>();

        for (int i = 0; i < adjacencyList.size(); i++) {
            if (adjacencyList.get(i).size() >= VERTEX_DEGREE) {
                Set<Integer> startClique = findStartClique(i);
                if (startClique.size() >= CLIQUE_SIZE) {
                    cliques.add(expandClique(startClique));
                }
            }
        }

        return cliques;
    }

    private Set<Integer> getNeighbors(int v) {
        Set<Integer> neighbors = new HashSet<>();
        Node n = nRow[v].node;

        while (n != null) {
            neighbors.add(n.columnW);
            n = n.right;
        }

        return neighbors;
    }

    private Set<Integer> findStartClique(int v) {
        Set<Integer> clique = new HashSet<>();
        clique.add(v);

        //TODO find a better way
        for(int i : getNeighbors(v)) {
            boolean f = true;
            for (int c : clique) {
                if (!isAdjusted(c, i)) {
                    f = false;
                    break;
                }
            }
            if (f) {
                clique.add(i);
                if (clique.size() >= CLIQUE_SIZE) {
                    return clique;
                }
            }
        }

        return clique;
    }

    private Set<Integer> expandClique(Set<Integer> clique) {
        Map<Integer, Integer> candidates = new HashMap<>();

        for (int c : clique) {
            removeInnerEdges(c, clique);
        }

        for (int c : clique) {
            Set<Integer> neighbors = getNeighbors(c);
            for (int n : neighbors) {
                Integer occurrences = candidates.get(n);
                candidates.put(n, occurrences == null ? 1 : occurrences + 1);
            }
        }

        return expandCliqueRecursive(clique, candidates, clique.iterator().next());

    }

    private Set<Integer> expandCliqueRecursive(Set<Integer> clique, Map<Integer, Integer> candidates, int t) {
        int cliqueSize = clique.size();

        for (int y : getNeighbors(t)) {
            if (candidates.get(y) >= cliqueSize) {
                clique.add(y);

                //TODO find a better way
                for (int c : clique) {
                    removeInnerEdges(c, clique);
                }

                for (int n : getNeighbors(y)) {
                    Integer occurrences = candidates.get(n);
                    candidates.put(n, occurrences == null ? 1 : occurrences + 1);
                }
                return expandCliqueRecursive(clique, candidates, y);
            }
        }

        return clique;
    }

    //UTILS

    private Node getOrCreateElementForB(Node[][] B, int i, int j) {
        Node b;
        if (B[i][j] == null) {
            b = new Node(i, j);
        } else {
            b = B[i][j];
        }
        return b;
    }

    private void buildList(final List<Set<Integer>> adjacencyList) {
        final int N = adjacencyList.size();

        Node[][] B = new Node[N][N];
        nRow = new Header[N];
        nColumn = new Header[N];

        for (int i = 0; i < N; i++) {
            //TODO The neighbors MUST BE SORTED
            Set<Integer> neighbors = adjacencyList.get(i);

            Node bPreviousHorizontal = null;
            Node bPreviousVertical = null;

            for (int j : neighbors) {

                Node bHorizontal = getOrCreateElementForB(B, j, i);
                Node bVertical = getOrCreateElementForB(B, i, j);

                if (!(bPreviousVertical == null || bPreviousHorizontal == null)) {
                    bHorizontal.setLeft(bPreviousHorizontal);
                    bPreviousHorizontal.setRight(bHorizontal);

                    bVertical.setUp(bPreviousVertical);
                    bPreviousVertical.setDown(bVertical);
                }

                B[j][i] = bHorizontal;
                bPreviousHorizontal = bHorizontal;

                B[i][j] = bVertical;
                bPreviousVertical = bVertical;
            }
        }

        //link first with start
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (B[j][i] != null) {
                    // since B is symmetric
                    nRow[i] = new Header(B[j][i]);
                    nColumn[i] = new Header(B[i][j]);
                    break;
                }
            }
        }
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

    //TODO remove
    public void printB() {
        final int N = nRow.length;
        System.out.println("{left, right, up, down}");
        System.out.println("horizontal");
        for (int i = 0; i < N; i++) {
            if (nRow[i].node != null) {
                System.out.printf("%-50s ", nRow[i].node);
                Node right = nRow[i].node.right;
                while (right != null) {
                    System.out.printf("%-50s ", right);
                    right = right.right;
                }
                System.out.println();
            } else {
                System.out.println("null");
            }
        }
        System.out.println();
        System.out.println("vertical");
        for (int i = 0; i < N; i++) {
            if (nColumn[i].node != null) {
                System.out.printf("%-50s ", nColumn[i].node == null ? "null" : nColumn[i]);
                Node down = nColumn[i].node.down;
                while (down != null) {
                    System.out.printf("%-50s ", down);
                    down = down.down;
                }
                System.out.println();
            } else {
                System.out.println("null");
            }
        }
    }
    private class Header {
        private Node node;

        Header(Node node) {
            this.node = node;
        }

        @Override
        public String toString() {
            return node.toString();
        }
    }

    private class Node {

        private Node right;
        private Node left;
        private Node up;
        private Node down;
        private int rowW;
        private int columnW;

        Node(int columnW, int rowW) {
            this.rowW = rowW;
            this.columnW = columnW;
        }

        void setUp(Node up) {
            this.up = up;
        }

        void setRight(Node right) {
            this.right = right;
        }

        void setLeft(Node left) {
            this.left = left;
        }

        void setDown(Node down) {
            this.down = down;
        }


        private String bTOString(Node b) {
            String s;
            if (b == null) {
                s = "null";
            } else {
                s = "row: " + (b.rowW + 1) + " col: " + (b.columnW + 1);
            }
            return s;
        }

        @Override
        public String toString() {
            return bTOString(this) +
                    " {" + bTOString(left) +
                    ", " + bTOString(right) + ", " + bTOString(up) +
                    ", " + bTOString(down) + "} ";
        }
    }
}
