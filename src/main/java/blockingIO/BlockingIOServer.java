package blockingIO;

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
public class BlockingIOServer {
    public static void main(String[] args) throws IOException {
        //1. Create the ServerSocketChannel -> The Socket that the application is listening for connection
        final ServerSocketChannel ssc = ServerSocketChannel.open();
        //2. Bind the ssc into a port
        ssc.bind(new InetSocketAddress(8080));
        //3. Busy waiting for the connection from client side:
        final List<SocketChannel> socketChannelList = new ArrayList<>();
        final ByteBuffer buffer = ByteBuffer.allocate(10);
        while(true) {
            log.info("Waiting for the connection");
            final SocketChannel sc = ssc.accept(); // Blocking the thread
            log.info("Connection created with socket as {}", sc);
            socketChannelList.add(sc);
            for(final SocketChannel socketChannel : socketChannelList) {
                log.info("Reading message from {}", socketChannel);
                socketChannel.read(buffer);
                buffer.flip();
                log.info("The message from client is {}", StandardCharsets.UTF_8.decode(buffer));
                buffer.clear();
                log.info("Finish Reading message from {}", socketChannel);
            }
        }
    }
}
