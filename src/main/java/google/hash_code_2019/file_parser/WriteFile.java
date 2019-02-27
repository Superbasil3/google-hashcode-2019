package main.java.fun.google.hash_code_2019.file_parser;

import fun.google.hash_code_2018.model.Maps;
import fun.google.hash_code_2018.model.VehicleRides;

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
                Path outputFolder = Paths.get("target\\output");
                Files.createDirectories(outputFolder);

                String outputFileName = outputFolder.resolve(entry.getKey().toString().replace("in", "out")).toString();
                PrintWriter fileResult = new PrintWriter(new FileWriter(outputFileName));

                writeAnswerToFile(fileResult,entry.getValue());
                fileResult.close();
            }
        } catch (Exception e) {

        }
    }

    private static void writeAnswerToFile(PrintWriter fileResult, Object value) {
        //for(VehicleRides vehicleRides : value.getVehicleRides()){
            fileResult.println("");
        //}
    }
}
