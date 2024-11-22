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
            List<String> expectedFiles = getExpectedFiles();
            List<FileInfo> files = reader.getMigrationFiles();
            assertEquals(files.size(), expectedFiles.size());
        }
        @Test
        void getMigrationFilesValueTest() {
            when(properties.getProperty("filepath")).thenReturn("db/migration");
            List<String> expectedFiles = getExpectedFiles();
            List<String> files = reader.getMigrationFiles().stream().map(FileInfo::getFilename).toList();
            assertEquals(expectedFiles, files);
        }
        private List<String> getExpectedFiles() {
            List<String> expectedFiles = List.of("V_3__test_3.sql", "V_1__test_1.sql");
            return expectedFiles;
        }
    }