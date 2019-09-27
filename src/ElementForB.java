public class ElementForB {

    private ElementForB right;
    private ElementForB left;
    private ElementForB up;
    private ElementForB down;
    private int horizontalW;
    private int verticalW;



    public ElementForB(int horizontalW, int verticalW) {
        this.horizontalW = horizontalW;
        this.verticalW = verticalW;
    }

    public ElementForB getRight() {
        return right;
    }

    public void setRight(ElementForB right) {
        this.right = right;
    }

    public ElementForB getLeft() {
        return left;
    }

    public void setLeft(ElementForB left) {
        this.left = left;
    }

    public ElementForB getUp() {
        return up;
    }

    public void setUp(ElementForB up) {
        this.up = up;
    }

    public ElementForB getDown() {
        return down;
    }

    public void setDown(ElementForB down) {
        this.down = down;
    }

    public int getHorizontalW() {
        return horizontalW;
    }

    public void setHorizontalW(int horizontalW) {
        this.horizontalW = horizontalW;
    }

    public int getVerticalW() {
        return verticalW; }

    public void setVerticalW(int verticalW) {
        this.verticalW = verticalW;
    }

    private String bTOString(ElementForB b) {
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
                " {" + bTOString(left) + ", " + bTOString(right) + ", " + bTOString(up) + ", " + bTOString(down) + "}";
    }
}
