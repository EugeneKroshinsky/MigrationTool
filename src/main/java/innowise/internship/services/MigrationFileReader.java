package innowise.internship.services;

import innowise.internship.dto.FileInfo;

import java.util.List;

public interface MigrationFileReader {
    List<FileInfo> getMigrationFiles();
}

