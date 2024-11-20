    package innowise.internship;

    import org.junit.jupiter.api.BeforeAll;
    import org.junit.jupiter.api.Test;

    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.List;

    import static org.junit.jupiter.api.Assertions.*;

    class MigrationFileClasspathReaderTest {
        private MigrationFileReader reader = new MigrationFileClasspathReader("db/migration");

        @Test
        void getMigrationFilesSizeTest() {
            List<String> expectedFiles = getExpectedFiles();
            List<String> files = reader.getMigrationFiles();
            assertEquals(files.size(), expectedFiles.size());
        }
        @Test
        void getMigrationFilesValueTest() {
            List<String> expectedFiles = getExpectedFiles();
            List<String> files = reader.getMigrationFiles();
            assertEquals(expectedFiles, files);
        }
        private List<String> getExpectedFiles() {
            Path expectedPath1 = Paths.get("C:", "github", "MigrationTool", "build", "resources", "test", "db", "migration", "V_1__test_1.sql");
            Path expectedPath2 = Paths.get("C:", "github", "MigrationTool", "build", "resources", "test", "db", "migration", "subdirectory", "V_3__test_3.sql");
            List<String> expectedFiles = List.of(expectedPath1.toString(), expectedPath2.toString());
            return expectedFiles;
        }
    }