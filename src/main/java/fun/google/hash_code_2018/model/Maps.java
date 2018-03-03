package fun.google.hash_code_2018.model;

import java.util.ArrayList;
import java.util.List;

public class Maps {

    private final List<Ride> rides = new ArrayList<>();
    private final List<VehicleRides> vehicleRides;
    private final int rows;
    private final int columns;
    private final int vehicles;
    private final int bonus;
    private final int steps;

    public Maps(String rows, String columns, String vehicles, String bonus, String steps) {
        this.rows = Integer.parseInt(rows);
        this.columns = Integer.parseInt(columns);
        this.vehicles = Integer.parseInt(vehicles);
        this.bonus = Integer.parseInt(bonus);
        this.steps = Integer.parseInt(steps);
        this.vehicleRides = new ArrayList<>(this.vehicles);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getVehicles() {
        return vehicles;
    }

    public int getBonus() {
        return bonus;
    }

    public int getSteps() {
        return steps;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void addRide(BookedRide ride) {
        rides.add(ride);
    }

    public List<VehicleRides> getVehicleRides() {
        return vehicleRides;
    }

}
