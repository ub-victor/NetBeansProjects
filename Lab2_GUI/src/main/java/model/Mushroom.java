package model;

public class Mushroom {

    private int id;
    private String name;
    private boolean hasGills;
    private boolean forest;
    private boolean hasRing;
    private boolean convex;

    public Mushroom(String name, boolean hasGills, boolean forest, boolean hasRing, boolean convex) {
        this.name = name;
        this.hasGills = hasGills;
        this.forest = forest;
        this.hasRing = hasRing;
        this.convex = convex;
    }

    public String getName() { return name; }
    public boolean isHasGills() { return hasGills; }
    public boolean isForest() { return forest; }
    public boolean isHasRing() { return hasRing; }
    public boolean isConvex() { return convex; }
}