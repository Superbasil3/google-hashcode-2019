package google.hash_code_2019.file_parser;

import google.hash_code_2019.model.Slide;
import google.hash_code_2019.model.Transitions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class WriteFile {

  public static void writeFileToPath(Map<String, Object> resultObject) throws IOException {
    try {
      for (Map.Entry entry : resultObject.entrySet()) {
        Path outputFolder = Paths.get("target/output");
        Files.createDirectories(outputFolder);

        String outputFileName = outputFolder.resolve(entry.getKey().toString().replace("txt", "out")).toString();
        PrintWriter fileResult = new PrintWriter(new FileWriter(outputFileName));

        writeAnswerToFile(fileResult, (Transitions) entry.getValue());
        fileResult.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void writeAnswerToFile(PrintWriter fileResult, Transitions transition) {
    fileResult.println(transition.transitions.size());
    for (Slide slide : transition.transitions) {
      if(slide.photo1 != null && slide.photo2 != null){
        fileResult.println(slide.photo1.idPhoto + " " + slide.photo2.idPhoto);
      } else {
        fileResult.println(slide.photo1.idPhoto);
      }
    }
  }
}
