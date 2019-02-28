package google.hash_code_2019;

import google.hash_code_2019.file_parser.ReadFile;
import google.hash_code_2019.file_parser.WriteFile;

import java.util.Map;

public class main {


  public static void main(String[] args) throws Exception {
    long timeStart = System.currentTimeMillis();
    Map<String, Object> stringObjectMap = ReadFile.getFileFromPath();
    WriteFile.writeFileToPath(stringObjectMap);
    System.out.println((System.currentTimeMillis() - timeStart) / 1000 + " seconds in total");
  }
}
