package com.bryant.io.output;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 字节流-输出流-ByteArrayOutputStream
 */
@Slf4j
public class ByteArrayOutputStreamTest {
    public static void main(String[] args) throws IOException {
        String newString = "Hello, World!";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 底层是for循环，写入ByteArrayOutputStream的内存数组
        baos.write(newString.getBytes());
        // 读出内存数组：底层会新建一个数组，通过数组拷贝，并返回
        byte[] bytes = baos.toByteArray();
        log.info("bytes: {}", bytes);
        log.info("string: {}", new String(bytes));
    }
}
