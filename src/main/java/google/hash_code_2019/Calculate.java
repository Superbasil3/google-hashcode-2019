package google.hash_code_2019;

import google.hash_code_2019.file_parser.ReadFile;
import google.hash_code_2019.file_parser.WriteFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class Calculate {
    public static void main(String[] args) throws Exception {
        long timeStart = System.currentTimeMillis();
        Map<String, Object> stringObjectMap = ReadFile.getFileFromPath();
        stringObjectMap.values().forEach(simulation -> {
            try {
                ((Simulation) simulation).calculate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println((System.currentTimeMillis() - timeStart) / 1000 + " seconds in total");
    }
}
