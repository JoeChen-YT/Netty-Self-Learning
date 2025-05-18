package buffer;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class FileBufferExperiment {
    public static void main(String[] args) throws FileNotFoundException {
        // There are two ways to get FileChannel:
        // 1. FileInputStream/FileOutputStream 2. RandomAccessFile
        final FileChannel fileChannel = new FileInputStream("data.txt").getChannel();

        // 2. Allocate ByteBuffer to store the byte read from the FileChannel:
        final ByteBuffer buffer = ByteBuffer.allocate(10);

        // 3. Read bytes:
        while(true) {
            // flush the byte from channel to buffer
            try {
                if(fileChannel.read(buffer) < 0) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            log.info("Already read the data into buffer");

            // Switch the buffer to read mode:
            buffer.flip();
            // Read the buffer one by one:
            while(buffer.hasRemaining()) {
                final byte charInByte = buffer.get();
                log.info("output with byte as {}", (char) charInByte);
            }
            // Switch the butter to write mode:
            buffer.clear();
        }
    }
}
