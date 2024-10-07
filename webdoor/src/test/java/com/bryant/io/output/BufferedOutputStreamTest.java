package com.bryant.io.output;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * BufferedOutputStream通过内部维护一个缓冲区来减少对底层文件系统的调用次数，从而提高写入效率。
 */
@Slf4j
public class BufferedOutputStreamTest {

    public static void main(String[] args) throws IOException {
        // 设置一个内存数据
        String data = "Hello world....";
        // 输出流写入字符到磁盘
        File file = new File("/Users/bryantmo/Desktop/code/springcloud_test/webdoor/src/test/java/com/bryant/io/1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 2);
        bufferedOutputStream.write(data.getBytes());
        // flush()方法用于强制将缓冲区中的数据写入到底层的文件输出流中。即使缓冲区未满，调用flush()也会立即执行写入操作。
        bufferedOutputStream.flush();
    }
}
