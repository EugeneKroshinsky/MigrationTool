package innowise.internship.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class SQLReader {
    public List<String> readSQLFile(Path path) {
        List<String> lines = new ArrayList<>();
        try{
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
