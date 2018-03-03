package fun.google.hash_code_2018.model;

import fun.google.hash_code_2018.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VehicleRides implements Ride {
    private final Simulation simulation;
    private List<Ride> rides = new ArrayList<>();
    private int duration = 0;
    private int score = 0;
    private int bonus = 0;
    private double ratio = 0;

    public VehicleRides(Simulation simulation, Ride ride) {
        this.simulation = simulation;
        addRide(simulation, ride);
    }

    public VehicleRides(Simulation simulation, Ride ride1, Ride ride2) {
        this.simulation = simulation;
        this.rides.add(ride1);
        addRide(simulation, ride2);
    }

    public void addRide(Simulation simulation, Ride ride) {
        Point startingPoint = this.rides.isEmpty() ? Point.ORIGIN : getLastRide().getFinish();
        this.rides.add(new EmptyRide(simulation, startingPoint, ride.getStart()));
        this.rides.add(ride);
        calculateDurationAndScore();
        calculateRatio();
    }

    @Override
    public String getRideId() {
        return this.rides.stream().map(Ride::getRideId).filter(Objects::nonNull).collect(Collectors.joining(" "));
    }

    @Override
    public Point getStart() {
        return getFirstRide().getStart();
    }

    @Override
    public Point getFinish() {
        return getLastRide().getFinish();
    }

    @Override
    public int getEarliestStart() {
        return this.getFirstRide().getEarliestStart();
    }

    @Override
    public int getLatestFinish() {
        return simulation.maps.getSteps();
    }

    @Override
    public int getDuration() {
        return duration;
    }

    public int getScore() {
        return score + bonus;
    }

    @Override
    public int getSize() {
        return this.rides.stream().mapToInt(Ride::getSize).sum();
    }

    public double getRatio() {
        return ratio;
    }

    public double getInvertRatio() {
        return 1 / getRatio();
    }

    public List<Ride> getRides() {
        return rides;
    }

    public Stream<Ride> getRidesStream() {
        return rides.stream();
    }

    private Ride getFirstRide() {
        return this.rides.get(0);
    }

    private Ride getLastRide() {
        return this.rides.get(rides.size() - 1);
    }

    // Working method that calculate the score
    private void calculateDurationAndScore() {
        this.score = this.rides.stream().mapToInt(Ride::getScore).sum();
        this.bonus = 0;
        this.duration = 0;
        for (Ride currentRide : this.rides) {
            if (duration < currentRide.getEarliestStart()) {
                bonus += simulation.maps.getBonus();
            }
            duration = Math.max(this.duration, currentRide.getEarliestStart());
            duration += currentRide.getDuration();
            if (duration > currentRide.getLatestFinish()) {
                this.score = 0;
            }
        }
        if (duration > this.simulation.maps.getSteps()) {
            this.score = 0;
        }
    }

    private void calculateRatio() {
        //double waitRatio = this.duration / (double) (getLastRide().getEarliestStart() - getFirstRide().getDuration());
        double waitRatio = 0;
        if (simulation.maps.getBonus() > 500) {
            double scoreRatio = (1 / (double) this.duration) * this.bonus / 1000;
            this.ratio = waitRatio > 0 ? scoreRatio * waitRatio : scoreRatio;
        } else {
            double scoreRatio = getScore() / (double) this.duration;
            this.ratio = waitRatio > 0 ? scoreRatio * waitRatio : scoreRatio;
        }
    }

    public String getStringToFile() {
        return getSize() + " " + getRideId();
    }

    @Override
    public String toString() {
        return "VehicleRides{rides=" + rides.size() + ", duration=" + duration + ", score=" + score + ", bonus=" + bonus + '}';
    }
}
