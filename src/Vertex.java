import java.util.Arrays;
import java.util.Objects;

public class Vertex {

    private final int idx;

    private final String peptide;

    private final int[] intervals;

    public int getIdx() {
        return idx;
    }

    public String getPeptide() {
        return peptide;
    }

    public int[] getIntervals() {
        return intervals;
    }

    public Vertex(int idx, String peptide, int[] intervals) {
        this.idx = idx;
        this.peptide = peptide;
        this.intervals = intervals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return getIdx() == vertex.getIdx();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdx());
    }

    @Override
    public String toString() {
        return idx + ": (" + peptide + ", " + Arrays.toString(intervals) + ")";
    }
}
