package innowise.internship.factories;

import innowise.internship.dto.FileInfo;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileInfoFactoryTest {

    @Test
    void createFileInfoCorrectTest() {
        Path path = Paths.get("V_1.0__create_table.sql");
        FileInfo fileInfo = FileInfoFactory.createFileInfo(path);
        assertEquals("1.0", fileInfo.getVersion());
        assertEquals("create_table", fileInfo.getDescription());
        assertEquals("sql", fileInfo.getType());
        assertEquals("V_1.0__create_table.sql", fileInfo.getFilename());
        assertEquals("V", fileInfo.getActionType());
        assertTrue(fileInfo.isCorrect());
    }
    @Test
    void createFileInfoIncorrectTest() {
        Path path = Paths.get("wrong value");
        FileInfo fileInfo = FileInfoFactory.createFileInfo(path);
        assertEquals("wrong value", fileInfo.getFilename());
        assertFalse(FileInfoFactory.createFileInfo(path).isCorrect());
        assertNull(fileInfo.getVersion());
        assertNull(fileInfo.getDescription());
        assertNull(fileInfo.getType());
        assertNull(fileInfo.getActionType());
    }
}