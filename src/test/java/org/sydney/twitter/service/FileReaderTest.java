package org.sydney.twitter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class FileReaderTest {

    @InjectMocks
    private FileReader fileReader;

    @Test
    public void shouldReadFileUsingName() {
        Stream<String> lines = fileReader.readFile("user.txt");
        assertThat(lines)
                .isNotEmpty()
                .extracting(String::toString)
                .contains("Ward follows Alan");
    }

    @Test
    public void shouldThrowExceptionWhenReadFileUsingIncorrectName() {

        String fileName = "users.txt";
        try {
            fileReader.readFile(fileName);
            fail("should throw exception with incorrect file name.");
        } catch (RuntimeException ex) {
            assertThat(ex).hasMessage(String.format(FileReader.FILE_NOT_FOUND, fileName));
        }

    }
}