package org.sydney.twitter.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {

    public static final String FILE_NOT_FOUND = "Unable to read this file [%s]";

    public static Stream<String> readFile(String fileName) {
        try {
            Path path = Paths.get(FileReader.class.getClassLoader()
                    .getResource(fileName).toURI());
            return Files.lines(path);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(String.format(FILE_NOT_FOUND, fileName), e);
        } catch (Exception ex) {
            throw new RuntimeException(String.format(FILE_NOT_FOUND, fileName), ex);
        }
    }
}
