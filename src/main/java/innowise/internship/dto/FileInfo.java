package innowise.internship.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Path;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class FileInfo {
    private Path path;
    private String filename;
    private String version;
    private String description;
    private String type;
    private String actionType;
    private boolean isCorrect;
}
