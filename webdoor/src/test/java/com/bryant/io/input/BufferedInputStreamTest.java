package com.bryant.io.input;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 输入流-字节流-缓冲输入流
 */
@Slf4j
public class BufferedInputStreamTest {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/bryantmo/Desktop/code/springcloud_test/webdoor/src/test/java/com/bryant/io/1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 2);
        // 设置一个内存
        byte[] bytes = new byte[3];
        int byteRead;
        while ((byteRead = bufferedInputStream.read()) != -1) {
            // 直接从磁盘读入数据到内存，非常慢
            int read = bufferedInputStream.read(bytes);
            log.info("read: data = {}, read = {}", new String(bytes), read);
        }
    }
}
