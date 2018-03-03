package fun.google.hash_code_2018.model;

import fun.google.hash_code_2018.Simulation;

public class EmptyRide implements Ride {
    private final String rideId;
    protected final Point start;
    protected final Point finish;
    private final int duration;
    private final int latestFinish;

    public EmptyRide(Simulation simulation, Point start, Point finish) {
        this.rideId = null;
        this.start = start;
        this.finish = finish;
        this.duration = this.start.distanceTo(this.finish);
        this.latestFinish = simulation.maps.getSteps();
    }

    public String getRideId() {
        return rideId;
    }

    public Point getStart() {
        return start;
    }

    public Point getFinish() {
        return finish;
    }

    public int getEarliestStart() {
        return -1;
    }

    public int getLatestFinish() {
        return latestFinish;
    }

    public int getDuration() {
        return duration;
    }

    public int getSize() {
        return 0;
    }

    public int getScore() {
        return 0;
    }

}
