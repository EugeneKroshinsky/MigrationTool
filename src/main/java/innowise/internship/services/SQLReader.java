package innowise.internship.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class SQLReader {
    public List<String> readLines(Path path) {
        List<String> lines = new ArrayList<>();
        try{
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    public String read(Path path) {
        List<String> lines = readLines(path);
        StringBuilder stringBuilder = new StringBuilder();
        lines.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
