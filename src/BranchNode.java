public class BranchNode implements Comparable<BranchNode> {

    int[] x;
    long cost;
    Vertex v;
    int base;

    public BranchNode(int[] x, long cost, Vertex v) {
        this.x = copyX(x);
        this.cost = cost;
        this.v = v;
    }
    
    public BranchNode(int[] x, long cost, Vertex v, int base) {
        this(x, cost, v);
        this.base = base;
    }

    private int[] copyX(int[] x) {
        int[] n = new int[x.length];
        for (int i = 0; i < x.length; ++i) {
            n[i] = x[i];
        }
        return n;
    }

    @Override
    public int compareTo(BranchNode o) {
        if (this.cost > o.cost) {
            return -1;
        } else if (this.cost < o.cost) {
            return 1;
        } else {
            return 0;
        }
    }

    private String getXStr() {
        String str = "";
        for (int i = 0; i < x.length; ++i) {
            str += (x[i] + ", ");
        }
        return str;
    }

    @Override
    public String toString() {
        return "cost = " + cost + ", x = " + getXStr();
    }

}