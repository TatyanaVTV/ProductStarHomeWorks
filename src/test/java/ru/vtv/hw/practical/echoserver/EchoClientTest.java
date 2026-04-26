package ru.vtv.hw.practical.echoserver;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class EchoClientTest {
    private static EchoServer server;
    private Thread serverThread;

    private EchoClient client;

    private static final int VALID_PORT = 8080;
    private static final int VALID_NOT_USED_PORT = 9999;

    private static final String VALID_HOST = "localhost";
    private static final String INVALID_HOST = "invalid.host.name";

    @BeforeAll
    void setUpServer() {
        server = EchoServer.create(VALID_PORT);
        serverThread = new Thread(() -> server.start());
        serverThread.start();
    }

    @AfterAll
    void tearDownServer() {
        server.stop();
    }

    @BeforeEach
    void setUp() {
        client = new EchoClient(VALID_HOST, VALID_PORT);
    }

    @Test
    void testSendMessage_ToRunningServer() {
        assertDoesNotThrow(
                () -> client.sendMessage("Test message"),
                "Отправка сообщения работающему серверу не должна вызывать исключений"
        );
        System.out.printf("Сообщение успешно отправлено и получено%n");
    }

    @Test
    void testSendMessage_SocketTimeoutException() {
        var client = new EchoClient(VALID_HOST, VALID_PORT) {
            @Override
            protected Socket createSocket(String host, int port) throws IOException {
                throw new SocketTimeoutException("Connection timed out");
            }
        };

        var exception = assertThrows(EchoClientException.class, () -> client.sendMessage("test message"),
                "Метод должен выбрасывать EchoClientException при таймауте");

        var expectedMsg = format("[CLIENT] Connection timeout: %s:%d", VALID_HOST, VALID_PORT);
        assertEquals(expectedMsg, exception.getMessage(),
                format("Сообщение исключения должно содержать '%s'", expectedMsg)
        );
    }

    @Test
    void testSendMessage_IOException() {
        var client = new EchoClient(VALID_HOST, VALID_PORT) {
            @Override
            protected Socket createSocket(String host, int port) throws IOException {
                throw new IOException("I/O error occurred");
            }
        };

        var exception = assertThrows(EchoClientException.class, () -> client.sendMessage("test message"),
                "Метод должен выбрасывать EchoClientException при IO ошибке"
        );

        assertTrue(exception.getMessage().contains("[CLIENT] Input/Output exception"),
                "Сообщение исключения должно содержать информацию об ошибке ввода‑вывода"
        );
    }

    @Test
    void testConnectToNonExistentServerThrowsException() {
        var clientToInvalidHost = new EchoClient(VALID_HOST, VALID_NOT_USED_PORT); // порт, где нет сервера

        var exception = assertThrows(EchoClientException.class,
                () -> clientToInvalidHost.sendMessage("Test"),
                "Попытка подключения к несуществующему серверу должна вызвать ConnectException"
        );

        var expectedMsg = format("[CLIENT] Didn't manage to connect with server %s:%d. Check if server is running",
                VALID_HOST, VALID_NOT_USED_PORT);
        assertEquals(expectedMsg, exception.getMessage(),
                format("Неверный текст исключения: '%s'", exception.getMessage()));
    }

    @Test
    void testSendToInvalidHostThrowsException() {
        var clientToInvalidHost = new EchoClient(INVALID_HOST, VALID_PORT);
        var expectedMsg = format("[CLIENT] Unknown host: %s", INVALID_HOST);

        var exception = assertThrows(EchoClientException.class,
                () -> clientToInvalidHost.sendMessage("Test"),
                "Попытка подключения к неверному хосту должна вызвать исключение EchoClientException"
        );
        assertEquals(expectedMsg,
                exception.getMessage(),
                format("Неверный текст исключения: '%s'", exception.getMessage())
        );
    }

    @Test
    void testEmptyMessage() {
        assertDoesNotThrow(
                () -> client.sendMessage(""),
                "Отправка пустого сообщения не должна вызывать исключений"
        );
        System.out.printf("Пустое сообщение обработано корректно%n");
    }
}
