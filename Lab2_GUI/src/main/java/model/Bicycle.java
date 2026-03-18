package model;

public class Bicycle {

    private int id;
    private int startTime;
    private int endTime;
    private int totalHours;
    private int amount;

    // Constructor WITHOUT id (for save)
    public Bicycle(int startTime, int endTime, int totalHours, int amount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalHours = totalHours;
        this.amount = amount;
    }

    // Constructor WITH id (for update/delete)
    public Bicycle(int id, int startTime, int endTime, int totalHours, int amount) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalHours = totalHours;
        this.amount = amount;
    }

    // Getters
    public int getId() { return id; }
    public int getStartTime() { return startTime; }
    public int getEndTime() { return endTime; }
    public int getTotalHours() { return totalHours; }
    public int getAmount() { return amount; }
}