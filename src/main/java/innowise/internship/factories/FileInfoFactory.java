package innowise.internship.factories;

import innowise.internship.dto.FileInfo;

import java.nio.file.Path;

public class FileInfoFactory {
    public static FileInfo createFileInfo(Path path) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath(path);
        fileInfo.setFilename(path.getFileName().toString());
        boolean isCorrect = checkVersion(fileInfo.getFilename());
        fileInfo.setCorrect(isCorrect);
        if (isCorrect) {
            String[] nameParts = fileInfo.getFilename().split("__");
            String[] versionAndAction = nameParts[0].split("_");
            fileInfo.setVersion(versionAndAction[1]);
            fileInfo.setActionType(versionAndAction[0]);
            String[] descriptionAndType = nameParts[1].split("\\.");
            fileInfo.setDescription(descriptionAndType[0]);
            fileInfo.setType(descriptionAndType[1]);
        }
        return fileInfo;
    }
    private static boolean checkVersion(String file) {
        String regex = "^V_\\d+(\\.\\d+)*__[a-zA-Z0-9_]+\\.sql$";
        return file.matches(regex);
    }
}
