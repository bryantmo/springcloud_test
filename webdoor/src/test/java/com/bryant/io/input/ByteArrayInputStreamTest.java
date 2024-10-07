package com.bryant.io.input;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ByteArrayInputStream 是 Java 中的一个类，它是 InputStream 的子类，用于从字节数组中读取数据。
 * 这个类允许你将一个字节数组视为一个输入流，从而可以使用标准的 I/O 流操作来处理它。
 */
@Slf4j
public class ByteArrayInputStreamTest {
    public static void main(String[] args) throws IOException {
        // 被读取的数据，内存
        byte[] bytes = "Hello world".getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes, 0, 3);
        while (byteArrayInputStream.read() != -1) {
            byteArrayInputStream.read(bytes);
            log.info("byteArrayInputStream#read: data = {}", new String(bytes));
        }

    }
}
