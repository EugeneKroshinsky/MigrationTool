package innowise.internship.utils;

import innowise.internship.dto.FileInfo;
import innowise.internship.services.MigrationFileClasspathReader;
import innowise.internship.services.MigrationFileReader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SqlFileUtilTest {
    private static final String FILE_V_1_FIRST_QUERY = """
            CREATE TABLE TEST (
                ID INT PRIMARY KEY,
                NAME VARCHAR(255)
            );
            """;
    private static final String FILE_V_1_SECOND_QUERY = """
            INSERT INTO TEST (ID, NAME) VALUES (1, 'test 1');
            """;
    private static final String FILE_V_2 = "INSERT INTO TEST (ID, NAME) VALUES (3, 'test 3');";
    private final SqlFileUtil sqlFileUtil = new SqlFileUtil();
    private final Properties properties = PropertiesUtils.getProperties("application.properties");
    private final MigrationFileReader fileReader = new MigrationFileClasspathReader(properties);
    private final List<FileInfo> files = fileReader.getMigrationFiles();

    @Test
    void readLinesV1Test() {
        List<String> lines = sqlFileUtil.readLines(files.get(0).getPath());
        String query = FILE_V_1_FIRST_QUERY + FILE_V_1_SECOND_QUERY;
        List<String> expectedLines = Arrays.stream(query.split("\n")).toList();
        assertEquals(expectedLines, lines);
    }

    @Test
    void readLinesV2Test() {
        List<String> lines = sqlFileUtil.readLines(files.get(1).getPath());
        assertEquals(List.of(FILE_V_2), lines);
    }

    @Test
    void readV1Test() {
        String fileStr = sqlFileUtil.read(files.get(0).getPath());
        String query = (FILE_V_1_FIRST_QUERY + FILE_V_1_SECOND_QUERY)
                .replace("\n", "");
        assertEquals(query, fileStr);
    }

    @Test
    void readV2Test() {
        String fileStr = sqlFileUtil.read(files.get(1).getPath());
        String query = FILE_V_2;
        assertEquals(query, fileStr);
    }

    @Test
    void getSeparateQueriesV1Test() {
        List<String> queries = sqlFileUtil.getSeparateQueries(files.get(0).getPath());
        String firstQuery = FILE_V_1_FIRST_QUERY.replace("\n", "").replace(";", "");
        String secondQuery = FILE_V_1_SECOND_QUERY.replace("\n", "").replace(";", "");
        List<String> expectedQueries = List.of(firstQuery, secondQuery);
        assertEquals(expectedQueries, queries);
    }
    @Test
    void getSeparateQueriesV2Test() {
        List<String> queries = sqlFileUtil.getSeparateQueries(files.get(1).getPath());
        List<String> expectedQueries = List.of(FILE_V_2.replace(";", ""));
        assertEquals(expectedQueries, queries);
    }
}