import java.util.Comparator;
import java.util.List;

public class FourTimesLinkedList {

    private Node[] nHorizontal;
    private Node[] nVertical;

    //TODO List<Set<Integer>>
    public FourTimesLinkedList(List<Vertex>[] Ls) {
        buildList(Ls);
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


    private void buildList(List<Vertex>[] Ls) {
        final int N = Ls.length;

        Node[][] B = new Node[N][N];
        nHorizontal = new Node[N];
        nVertical = new Node[N];

        for (int i = 0; i < N; i++) {
            List<Vertex> lsi = Ls[i];
            //TODO lsi HAVE TO BE SORTED
            lsi.sort(Comparator.comparingInt(Vertex::getIdx));
            Node bPreviousHorizontal = null;
            Node bPreviousVertical = null;

            for (Vertex v : lsi) {
                int j = v.getIdx();

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
        UnitTest(B);

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

    //TODO remove
    private void UnitTest(Node[][] B) {
        //this is the right B for generateTestGraph2
        String rightBStr = "null1 2 {null, 1 3, null, 3 2}1 3 {1 2, null, null, 2 3}null2 1 {null, 2 3, null, 3 1}null2 3 {2 1, 2 4, 1 3, null}2 4 {2 3, null, null, null}3 1 {null, 3 2, 2 1, null}3 2 {3 1, null, 1 2, 4 2}nullnullnull4 2 {null, null, 3 2, null}nullnull";
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B.length; j++) {
                s.append(B[i][j]);
            }
        }
        System.out.println(rightBStr.equals(s.toString()));
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
