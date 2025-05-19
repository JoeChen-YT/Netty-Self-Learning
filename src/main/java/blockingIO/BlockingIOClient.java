package blockingIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class BlockingIOClient {
    public static void main(String[] args) throws IOException {
        final SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("Waiting");
    }
}
