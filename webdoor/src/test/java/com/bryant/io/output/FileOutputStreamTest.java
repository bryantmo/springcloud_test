package com.bryant.io.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节流-输入流-FileInputStream
 *
 */
public class FileOutputStreamTest {
    public static void main(String[] args) throws IOException {
        String data = "字节流-输入流-FileInputStream";
        // 文件标识符
        File file = new File("/Users/bryantmo/Desktop/code/springcloud_test/webdoor/src/test/java/com/bryant/io/1.txt");
        FileOutputStream fileInputStream = new FileOutputStream(file);
        // 直接从内存写入磁盘
        fileInputStream.write(data.getBytes());
        fileInputStream.flush();
    }
}
