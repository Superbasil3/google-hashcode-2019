package fun.google.hash_code_2018.model;

import fun.google.hash_code_2018.Simulation;

public class BookedRide implements Ride {
    private final String rideId;
    protected final Point start;
    protected final Point finish;
    private final int earliestStart;
    private final int latestFinish;
    private final int duration;
    private final int score;

    public BookedRide(Simulation simulation, String rideId, Point start, Point finish, int earliestStart, int latestFinish) {
        this.rideId = rideId;
        this.start = start;
        this.finish = finish;
        this.earliestStart = earliestStart;
        this.latestFinish = latestFinish;
        this.duration = this.start.distanceTo(this.finish);
        this.score = duration;
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
        return earliestStart;
    }

    public int getLatestFinish() {
        return latestFinish;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int getScore() {
        return score;
    }

}
