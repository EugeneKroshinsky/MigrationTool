    package innowise.internship;

    import innowise.internship.dto.FileInfo;
    import innowise.internship.services.MigrationFileClasspathReader;
    import innowise.internship.services.MigrationFileReader;
    import org.junit.jupiter.api.Test;
    import org.mockito.Mockito;

    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.List;
    import java.util.Properties;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.when;

    class MigrationFileClasspathReaderTest {
        private static Properties properties = Mockito.mock(Properties.class);
        private MigrationFileReader reader = new MigrationFileClasspathReader(properties);

        @Test
        void getMigrationFilesSizeTest() {
            when(properties.getProperty("filepath")).thenReturn("db/migration");
            List<FileInfo> expectedFiles = getExpectedFiles();
            List<FileInfo> files = reader.getMigrationFiles();
            assertEquals(files.size(), expectedFiles.size());
        }
        @Test
        void getMigrationFilesValueTest() {
            when(properties.getProperty("filepath")).thenReturn("db/migration");
            List<FileInfo> expectedFiles = getExpectedFiles();
            List<FileInfo> files = reader.getMigrationFiles();
            assertEquals(expectedFiles, files);
        }
        private List<FileInfo> getExpectedFiles() {
            Path expectedPath1 = Paths.get("C:", "github", "MigrationTool", "build", "resources", "test", "db", "migration", "V_1__test_1.sql");
            Path expectedPath2 = Paths.get("C:", "github", "MigrationTool", "build", "resources", "test", "db", "migration", "subdirectory", "V_3__test_3.sql");
            List<FileInfo> expectedFiles = List.of(new FileInfo(expectedPath2), new FileInfo(expectedPath1));
            return expectedFiles;
        }
    }