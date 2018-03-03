package fun.google.hash_code_2018.file_parser;

import fun.google.hash_code_2018.Simulation;
import fun.google.hash_code_2018.model.Maps;
import fun.google.hash_code_2018.model.Point;
import fun.google.hash_code_2018.model.BookedRide;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFile {

    public static int bonus = 0;

    public static Map<String, Maps> getFileFromPath() throws IOException, URISyntaxException {
        Map<String, Maps> holder = new HashMap<>();

        List<Path> paths = Files.find(Paths.get(ClassLoader.getSystemResource("inputs").toURI()), 5, (path, attr) -> path.toString().toLowerCase().endsWith(".in"))
                .sorted((path2, path1) -> path1.getFileName().toString().compareTo(path2.getFileName().toString()))
                .collect(Collectors.toList());
        int total = 0;
        for (Path file : paths) {
            String filename = file.getFileName().toString();
            final Simulation simulation = new Simulation();

            try (Stream<String> stream = Files.lines(Paths.get(file.toString()))) {
                AtomicInteger nextRideId = new AtomicInteger();
                stream.forEach((String line) -> {
                    String[] lineParsed = line.split(" ");
                    if (!simulation.isInitialized()) {
                        simulation.setMaps(new Maps(lineParsed[0], lineParsed[1], lineParsed[2], lineParsed[4], lineParsed[5]));
                    } else {
                        String rideId = String.valueOf(nextRideId.getAndIncrement());
                        simulation.addRide(new BookedRide(simulation, rideId,
                                new Point(Integer.parseInt(lineParsed[0]), Integer.parseInt(lineParsed[1])),
                                new Point(Integer.parseInt(lineParsed[2]), Integer.parseInt(lineParsed[3])),
                                Integer.parseInt(lineParsed[4]),
                                Integer.parseInt(lineParsed[5])));
                    }
                });

                holder.put(filename, simulation.maps);
            } catch (IOException e) {
                e.printStackTrace();
            }

            bonus = simulation.maps.getBonus();
            int simulateTotal = simulation.simulate();
            System.out.println("Total for " + file.getFileName().toString() + " = " + simulateTotal + " (" + NumberFormat.getInstance().format(simulateTotal) + ")");

            total += simulateTotal;
        }
        System.out.println("Total = " + NumberFormat.getInstance().format(total));
        return holder;
    }


}
