package innowise.internship.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;

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

    public FileInfo(Path path) {
        this.path = path;
        this.filename = path.getFileName().toString();
        this.isCorrect = checkVersion(filename);
        if (isCorrect) {
            this.version = filename.toString().split("__")[0].split("_")[1];
            this.description = filename.toString().split("__")[1].split(".sql")[0];
            this.type = filename.toString().split("__")[1].split("\\.")[1];
            this.actionType = filename.toString().split("__")[0].split("_")[0];
        }
    }

    private boolean checkVersion(String file) {
        String regex = "^V_\\d+(\\.\\d+)*__[a-zA-Z0-9_]+\\.sql$";
        return file.matches(regex);
    }

}
