package com.bryant.io.input;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 字符流-输入流-BufferedReader
 */
@Slf4j
public class FileReaderTest {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/bryantmo/Desktop/code/springcloud_test/webdoor/src/test/java/com/bryant/io/1.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            log.info("line : {}", line);
        }
    }
}
