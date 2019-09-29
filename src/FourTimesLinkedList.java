import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FourTimesLinkedList {

    private Node[] nHorizontal;
    private Node[] nVertical;

    public FourTimesLinkedList(List<Set<Integer>> adjacencyList) {
        buildList(adjacencyList);
    }

    private Node getOrCreateElementForB(Node[][] B, int i, int j) {
        Node b;
        if (B[i][j] == null) {
            b = new Node(i, j);
        } else {
            b = B[i][j];
        }
        return b;
    }


    private void buildList(List<Set<Integer>>  adjacencyList) {
        final int N = adjacencyList.size();

        Node[][] B = new Node[N][N];
        nHorizontal = new Node[N];
        nVertical = new Node[N];

        for (int i = 0; i < N; i++) {
            //TODO The neighbors MUST BE SORTED
            Set<Integer> neighbors = adjacencyList.get(i);

            Node bPreviousHorizontal = null;
            Node bPreviousVertical = null;

            for (int j : neighbors) {

                Node bHorizontal = getOrCreateElementForB(B, i, j);
                Node bVertical = getOrCreateElementForB(B, j, i);

                if (bPreviousVertical == null || bPreviousHorizontal == null) {
                    //link first with start
                    nHorizontal[i] = bHorizontal;
                    nVertical[i] = bVertical;
                } else {
                    bHorizontal.setLeft(bPreviousHorizontal);
                    bPreviousHorizontal.setRight(bHorizontal);

                    bVertical.setUp(bPreviousVertical);
                    bPreviousVertical.setDown(bVertical);
                }

                B[i][j] = bHorizontal;
                bPreviousHorizontal = bHorizontal;

                B[j][i] = bVertical;
                bPreviousVertical = bVertical;
            }
        }
    }

    public void removeIncomingEdges(int v) {
        Node n = nVertical[v];

        while (n != null) {
            if (n.left != null && n.right != null) {
                n.left.right = n.right;
                n.right.left = n.left;
            } else if (n.left != null) {
                n.left.right = null;
            } else {
                n.right.left = null;
            }
            n = n.down;
        }
    }


    //TODO remove
    public void printB() {
        final int N = nHorizontal.length;
        System.out.println("{left, right, up, down}");
        System.out.println("horizontal");
        for (int i = 0; i < N; i++) {
            System.out.printf("%-50s ", nHorizontal[i]);
            Node right = nHorizontal[i].getRight();
            while (right != null) {
                System.out.printf("%-50s ", right);
                right = right.getRight();
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("vertical");
        for (int i = 0; i < N; i++) {
            System.out.printf("%-50s ", nVertical[i]);
            Node down = nVertical[i].getDown();
            while (down != null) {
                System.out.printf("%-50s ", down);
                down = down.getDown();
            }
            System.out.println();
        }
    }

    private class Node {

        private Node right;
        private Node left;
        private Node up;
        private Node down;
        private int horizontalW;
        private int verticalW;

        Node(int horizontalW, int verticalW) {
            this.horizontalW = horizontalW;
            this.verticalW = verticalW;
        }

        void setUp(Node up) {
            this.up = up;
        }

        void setRight(Node right) {
            this.right = right;
        }

        Node getRight() {
            return right;
        }

        void setLeft(Node left) {
            this.left = left;
        }

        void setDown(Node down) {
            this.down = down;
        }

        Node getDown() {
            return down;
        }

        int getHorizontalW() {
            return horizontalW;
        }

        int getVerticalW() {
            return verticalW; }

        private String bTOString(Node b) {
            String s;
            if (b == null) {
                s = "null";
            } else {
                s = (b.getHorizontalW() + 1) + " " + (b.getVerticalW() + 1);
            }
            return s;
        }

        @Override
        public String toString() {
            return bTOString(this) +
                    " {" + bTOString(left) +
                    ", " + bTOString(right) + ", " + bTOString(up) +
                    ", " + bTOString(down) + "}";
        }
    }
}
