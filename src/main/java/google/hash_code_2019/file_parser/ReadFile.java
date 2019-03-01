package google.hash_code_2019.file_parser;

import google.hash_code_2019.Simulation;
import google.hash_code_2019.model.Photo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFile {


  public static Map<String, Object> getFileFromPath() throws IOException, URISyntaxException {
    Map<String, Object> holder = new HashMap<>();

    List<Path> paths = Files.find(Paths.get(ClassLoader.getSystemResource(".").toURI()), 5, (path, attr) -> path.toString().toLowerCase().endsWith(".txt"))
        .sorted((path2, path1) -> path1.getFileName().toString().compareTo(path2.getFileName().toString()))
        .collect(Collectors.toList());
    int total = 0;

    for (Path file : paths) {
      String filename = file.getFileName().toString();
      final Simulation simulation = new Simulation();

      try (Stream<String> stream = Files.lines(Paths.get(file.toString()))) {
        AtomicInteger photoId = new AtomicInteger();
        stream.forEach((String line) -> {
          String[] lineParsed = line.split(" ");
          if(lineParsed.length == 1){
            System.out.println("Number of pictures : " + lineParsed[0]);
          }else {
            simulation.addPhoto(new Photo(photoId.getAndIncrement(),lineParsed));
          }
        });
        System.out.println(simulation);
      } catch (IOException e) {
        e.printStackTrace();
      }

      //simulation.simulate();
      //simulation.fakeSimulate();

      simulation.prepareAndStat();
      total = simulation.fakeSimulate();
      holder.put(filename,simulation.transitions);
      System.out.println("File= " + filename + " score : " +total);
      WriteFile.writeFileToPath(holder);
    }
    System.out.println("Total = " + NumberFormat.getInstance().format(total));
    return holder;
  }

}
