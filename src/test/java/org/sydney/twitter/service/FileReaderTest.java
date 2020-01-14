package org.sydney.twitter.service;


import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class FileReaderTest {


    @Test
    public void shouldReadFileUsingName() {
        Stream<String> lines = FileReader.readFile("user.txt");
        assertTrue(lines.findAny().isPresent());
    }

    @Test
    public void shouldThrowExceptionWhenReadFileUsingIncorrectName() {

        String fileName = "users.txt";
        try {
            Stream<String> lines = FileReader.readFile(fileName);
            fail("should throw exception with incorrect file name.");
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), String.format(FileReader.FILE_NOT_FOUND, fileName));
        }

    }
}