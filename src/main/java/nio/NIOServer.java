package nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //1. Create the ServerSocketChannel -> The Socket that the application is listening for connection
        final ServerSocketChannel ssc = ServerSocketChannel.open();
        //2. Bind the ssc into a port
        ssc.bind(new InetSocketAddress(8080));
        //3. Configure the ssc as non-blocking
        ssc.configureBlocking(false);
        //4. Busy waiting for the connection from client side:
        final List<SocketChannel> socketChannelList = new ArrayList<>();
        final ByteBuffer buffer = ByteBuffer.allocate(10);
        while(true) {
            // Since we configure the ssc as non-blocking, it won't be blocked by accept method
            // And it will return null if there is no connection.
            final SocketChannel sc = ssc.accept();
            if (sc != null) {
                log.info("Connection created with socket as {}", sc);
                // Configure the server socket as non-blocking
                sc.configureBlocking(false);
                socketChannelList.add(sc);
            }
            for(final SocketChannel socketChannel : socketChannelList) {
                int len = socketChannel.read(buffer);
                if (len > 0) {
                    buffer.flip();
                    log.info("The message from client is {}", StandardCharsets.UTF_8.decode(buffer));
                    buffer.clear();
                    log.info("Finish Reading message from {}", socketChannel);
                }
            }
        }
    }
}
