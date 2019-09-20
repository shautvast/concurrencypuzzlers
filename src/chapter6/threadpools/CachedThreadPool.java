package chapter6.threadpools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    private static final Executor threadpool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        for (; ; ) {
            Socket clientSocket = serverSocket.accept();
            threadpool.execute(() -> handleRequest(clientSocket));
        }
    }

    private static void handleRequest(Socket clientSocket) {
        //
    }
}
