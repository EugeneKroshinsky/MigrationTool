package innowise.internship.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class SqlFileUtil {
    public List<String> readLines(Path path) {
        List<String> lines = new ArrayList<>();
        try{
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            log.error("Failed to read file", e);
        }
        return lines;
    }

    public String read(Path path) {
        List<String> lines = readLines(path);
        StringBuilder stringBuilder = new StringBuilder();
        lines.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
    public List<String> getSeparateQueries(Path path) {
        return Arrays.stream(read(path).split(";")).toList();
    }
}
