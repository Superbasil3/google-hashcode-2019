package google.hash_code_2019.file_parser;

import google.hash_code_2019.Simulation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

      } catch (IOException e) {
        e.printStackTrace();
      }


    }

    System.out.println("Total = " + NumberFormat.getInstance().format(total));
    return holder;
  }


}
