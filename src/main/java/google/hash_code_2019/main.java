package main.java.fun.google.hash_code_2019;


import fun.google.hash_code_2018.file_parser.ReadFile;
import fun.google.hash_code_2018.file_parser.WriteFile;

import java.util.Map;

public class main {


    public static void main(String[] args) throws Exception {
        long timeStart = System.currentTimeMillis();
        Map<String, Maps> stringObjectMap = ReadFile.getFileFromPath();
        WriteFile.writeFileToPath(stringObjectMap);
        System.out.println((System.currentTimeMillis() - timeStart) / 1000 + " seconds in total");
    }
}
