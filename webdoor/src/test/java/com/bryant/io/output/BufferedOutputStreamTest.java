package com.bryant.io.output;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 输出流-字节流-BufferedOutputStream
 * BufferedOutputStream通过内部维护一个缓冲区来减少对底层文件系统的调用次数，从而提高写入效率。
 */
@Slf4j
public class BufferedOutputStreamTest {
    public static void main(String[] args) throws IOException {
        // 设置一个内存数据
        String data = "Hello world...." + System.currentTimeMillis();
        // 输出流写入字符到磁盘
        File file = new File("/Users/bryantmo/Desktop/code/springcloud_test/webdoor/src/test/java/com/bryant/io/1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        // 这里我们测试了，缓冲区大小为2和222的预设值时，分别对应了底层不同的写入磁盘逻辑
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 2);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 222);
        bufferedOutputStream.write(data.getBytes());
        // flush()方法用于强制将缓冲区中的数据写入到底层的文件输出流中。即使缓冲区未满，调用flush()也会立即执行写入操作。
        bufferedOutputStream.flush();
    }
}
