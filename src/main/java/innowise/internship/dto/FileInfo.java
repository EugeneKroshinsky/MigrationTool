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
public class FileInfo implements Comparable<FileInfo> {
    private Path path;
    private String filename;
    private String version;
    private String description;
    private String type;
    private String actionType;
    private boolean isCorrect;
    @Override
    public int compareTo(FileInfo other) {
        if (this.version == null && other.version == null) {
            return 0;
        }
        if (this.version == null) {
            return -1;
        }
        if (other.version == null) {
            return 1;
        }
        return this.version.compareTo(other.version);
    }
}
