package com.bryant.io.output;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 字符流-输出流-BufferedWriter
 */
@Slf4j
public class BufferedWriterTest {
    public static void main(String[] args) throws IOException {
        String data = "hello world";
        File file = new File("/Users/bryantmo/Desktop/code/springcloud_test/webdoor/src/test/java/com/bryant/io/2.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
