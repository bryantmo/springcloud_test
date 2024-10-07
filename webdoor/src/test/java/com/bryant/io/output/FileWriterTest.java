package com.bryant.io.output;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class FileWriterTest {
    public static void main(String[] args) throws IOException {
        String data = "hello world";
        File file = new File("/Users/bryantmo/Desktop/code/springcloud_test/webdoor/src/test/java/com/bryant/io/2.txt");
        FileWriter fileWriter = new FileWriter(file);
        try {
            fileWriter.write(data);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
