package fun.google.hash_code_2018;

import fun.google.hash_code_2018.model.Maps;
import fun.google.hash_code_2018.model.Ride;
import fun.google.hash_code_2018.model.VehicleRides;
import fun.google.hash_code_2018.model.BookedRide;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Simulation {

    public Maps maps = null;

    public int simulate() {
        /*List<VehicleRides> rides = maps.getRides().parallelStream()
                .map(r1 -> new VehicleRides(this, r1))
                .filter(vr -> vr.getRatio() > 0)
                .flatMap(vr -> maps.getRides().stream().filter(r2 -> !vr.getRides().contains(r2)).map(r2 -> new VehicleRides(this, vr, r2)))
                .filter(vr -> vr.getRatio() > 0)
                .sorted(Comparator.comparingDouble(VehicleRides::getInvertRatio))
                .collect(Collectors.toList());*/

        List<VehicleRides> rides = maps.getRides().parallelStream()
                .flatMap(r1 -> maps.getRides().stream().filter(r2 -> r1 != r2).map(r2 -> new VehicleRides(this, r1, r2)))
                .filter(vr -> vr.getRatio() > 0)
                .sorted(Comparator.comparingDouble(VehicleRides::getInvertRatio))
                .collect(Collectors.toList());

        System.out.println(rides);

        return maps.getVehicleRides().stream().mapToInt(VehicleRides::getScore).sum();
    }

    public int simulate2() {
        // Stats
        int allRidesDuration = maps.getRides().stream().mapToInt(Ride::getDuration).sum();
        int totalAvailableDuration = maps.getVehicles() * maps.getSteps();
        double targetOccupancy = allRidesDuration / (double) totalAvailableDuration;
//        System.out.println("Rides (availbles vs total) = " + totalAvailableDuration + " vs " + allRidesDuration + " (" + targetOccupancy + ")");

        List<VehicleRides> startingRides = maps.getRides().parallelStream()
                .map(sr -> new VehicleRides(this, sr))
                .filter(sr -> sr.getRatio() > 0)
                .sorted(Comparator.comparingDouble(VehicleRides::getInvertRatio))
                .limit(maps.getVehicles())
                .collect(Collectors.toList());

        startingRides.stream().flatMap(VehicleRides::getRidesStream).forEach(maps.getRides()::remove);
        maps.getVehicleRides().addAll(startingRides);

        int maxIteration = 3;
        for (int iteration = 0; iteration < maxIteration; iteration ++) {
            final List<Ride> remainingRides = new ArrayList<>();
            final double startingBestRatio = (iteration == maxIteration - 1) ? 0 : 0.6d + 0.35d * ((maxIteration - iteration) / (double) maxIteration);
            maps.getRides().forEach(remainingRide -> {
                double bestRatio = startingBestRatio;
                VehicleRides bestRidesToAdd = null;
                for (VehicleRides vehicleRides : maps.getVehicleRides()) {
                    VehicleRides newVehicleRides = new VehicleRides(this, vehicleRides, remainingRide);
                    if (newVehicleRides.getRatio() <= 0) {
                        continue;
                    }

                    if (newVehicleRides.getRatio() > bestRatio) {
                        bestRatio = newVehicleRides.getRatio();
                        bestRidesToAdd = vehicleRides;
                    }
                }
                if (bestRidesToAdd != null) {
                    bestRidesToAdd.addRide(this, remainingRide);
                } else {
                    remainingRides.add(remainingRide);
                }
            });
            maps.getRides().clear();
            maps.getRides().addAll(remainingRides);
        }

        return maps.getVehicleRides().stream().mapToInt(VehicleRides::getScore).sum();
    }

    public boolean isInitialized() {
        return maps != null;
    }

    public void setMaps(Maps maps) {
        this.maps = maps;
    }

    public void addRide(BookedRide ride) {
        maps.addRide(ride);
    }
}
