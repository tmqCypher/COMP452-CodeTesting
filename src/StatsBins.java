public final class StatsBins {
    public static final int [] EDGES = {1, 2, 4, 6, 8, 10, 12, 14};

    public static int length() { return EDGES.length; }

    public static String getBinName(int index) {
        String binName = Integer.toString(EDGES[index]);
        if(index == EDGES.length-1) {
            // last bin
            binName += " or more";
        }
        else if(EDGES[index+1] - 1 > EDGES[index]) {
            // upper bound
            binName += "-" + (EDGES[index+1] - 1);
        }
        return binName;
    }
}
