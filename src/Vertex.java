import java.util.ArrayList;
import java.util.Arrays;

public class Vertex {

    private int idx;

    private String peptide;

    private int[] intervals;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getPeptide() {
        return peptide;
    }

    public void setPeptide(String peptide) {
        this.peptide = peptide;
    }

    public int[] getIntervals() {
        return intervals;
    }

    public void setIntervals(int[] intervals) {
        this.intervals = intervals;
    }

    public Vertex(int idx, String peptide, int[] intervals) {
        this.idx = idx;
        this.peptide = peptide;
        this.intervals = intervals;
    }

    @Override
    public String toString() {
        return idx + ": (" + peptide + ", " + Arrays.toString(intervals) + ")";
    }
}
