class TestGraph {

    TestGraphElement[] getAdjustedList() {
        return adjustedList;
    }

    private TestGraphElement[] adjustedList;

    TestGraph(Vertex[] vertices) {

        adjustedList = new TestGraphElement[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            adjustedList[i] = new TestGraphElement(vertices[i]);
        }
    }


    void addEdge(Vertex src, Vertex dest) {
        adjustedList[src.getIdx()].addNeighbour(dest);
    }
}
