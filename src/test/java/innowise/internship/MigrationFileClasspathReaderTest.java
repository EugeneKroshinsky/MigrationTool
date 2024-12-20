    package innowise.internship;

    import innowise.internship.dto.FileInfo;
    import innowise.internship.services.migrations.MigrationFileClasspathReader;
    import innowise.internship.services.migrations.MigrationFileReader;
    import org.junit.jupiter.api.Test;
    import org.mockito.Mockito;

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
            List<String> expectedFiles = List.of("V_1__test_1.sql", "V_3__test_3.sql");
            return expectedFiles;
        }
    }