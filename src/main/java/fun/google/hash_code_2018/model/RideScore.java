package fun.google.hash_code_2018.model;

public class RideScore {
    private BookedRide ride;
    private int vehicleId;
    private int score;

    public BookedRide getRide() {
        return ride;
    }

    public void setRide(BookedRide ride) {
        this.ride = ride;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
