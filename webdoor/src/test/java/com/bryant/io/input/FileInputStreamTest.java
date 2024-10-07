package com.bryant.io.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 字节流-输入流-FileInputStream
 *
 */
public class FileInputStreamTest {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/bryantmo/Desktop/code/springcloud_test/webdoor/src/test/java/com/bryant/io/1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int read = 0;
        // 直接从磁盘读入到内存
        while ((read = fileInputStream.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, read));
            // 可能再输出到磁盘持久化
        }
    }
}
