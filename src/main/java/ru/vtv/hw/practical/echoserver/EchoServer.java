package ru.vtv.hw.practical.echoserver;

import lombok.Builder;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.vtv.hw.practical.echoserver.EchoServerException.invalidPort;
import static ru.vtv.hw.practical.echoserver.EchoServerException.notStarted;

@Slf4j
@SuperBuilder
public class EchoServer {
    private final int port;
    private volatile boolean isRunning;
    private ServerSocket serverSocket;

    @Builder.Default
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public static EchoServer create(int port, ExecutorService threadPool) {
        if (port < 0 || port > 65535) {
            throw invalidPort(port);
        }
        return EchoServer.builder().port(port).threadPool(threadPool).build();
    }

    public static EchoServer create(int port) {
        return create(port, Executors.newCachedThreadPool());
    }

    public void start() {
        try {
            serverSocket = createServerSocket(port);
            log.debug("[SERVER] Сервер запущен на порту {}. Ожидание подключения...", port);
            isRunning = true;

            while (isRunning && !Thread.currentThread().isInterrupted()) {
                try {
                    var clientSocket = serverSocket.accept();
                    System.out.println("[SERVER] Новое подключение от: " + clientSocket.getInetAddress());

                    threadPool.submit(() -> handleClient(clientSocket));
                } catch (SocketException e) {
                    if (!isRunning) break;
                    if (!serverSocket.isClosed()) {
                        System.err.printf("[SERVER] Ошибка при принятии подключения: %s%n", e.getMessage());
                    }
                } catch (IOException e) {
                    if (!serverSocket.isClosed()) {
                        System.err.printf("[SERVER] IO ошибка при принятии подключения: %s%n", e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw notStarted(port);
        } finally {
            shutdown();
        }
    }

    public void stop() {
        isRunning = false;
        shutdown();
    }

    protected ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }

    private void handleClient(Socket clientSocket) {
        try (var inputStream = clientSocket.getInputStream();
             var outputStream = clientSocket.getOutputStream();
             var reader = createBufferedReader(inputStream);
             var writer = createPrintWriter(outputStream)) {

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.printf("[SERVER] Получено: %s%n", message);
                writer.println(message);
                System.out.printf("[SERVER] Отправлено эхо: %s%n", message);
            }
        } catch (SocketException e) {
            System.out.printf("[SERVER] Клиент отключился: %s%n", clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.printf("[SERVER] Ошибка ввода‑вывода с клиентом: %s%n", e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("[SERVER] Ошибка при закрытии клиентского сокета: " + e.getMessage());
            }
        }
    }

    private void shutdown() {
        if (!isNull(serverSocket) && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                log.warn("Ошибка при закрытии ServerSocket при остановке сервера: {}", e.getMessage());
            }
        }

        if (!threadPool.isShutdown()) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(5, SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    protected BufferedReader createBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    protected PrintWriter createPrintWriter(OutputStream outputStream) {
        return new PrintWriter(outputStream, true);
    }
}
