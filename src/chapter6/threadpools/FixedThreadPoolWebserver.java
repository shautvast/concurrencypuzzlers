package chapter6.threadpools;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * taken from listing 6.4. Added an actual response that the server will always give, proving that the thread is being reused.
 */
public class FixedThreadPoolWebserver {

    private static final Executor threadpool = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws IOException {
        threadpool.execute(() -> Thread.currentThread().setName("Hello Visitor"));

        ServerSocket serverSocket = new ServerSocket(8080);
        for (; ; ) {
            Socket clientSocket = serverSocket.accept();
            threadpool.execute(() -> handleRequest(clientSocket));
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(createHttpReponse());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createHttpReponse() {
        return String.format("HTTP/1.0 200%nContent-type: text/plain%nContent-length: 14%n%n%s%n",Thread.currentThread().getName());
    }
}
