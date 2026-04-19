package ru.vtv.hw.practical.echoserver;

import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.*;

@TestInstance(PER_CLASS)
public class EchoServerTest {
    private static EchoServer server;
    private static ExecutorService mockThreadPool;
    private Thread serverThread;

    private static final int VALID_PORT = 8080;
    private static final String VALID_HOST = "localhost";

    @SneakyThrows
    @BeforeAll
    void setUp() {
        mockThreadPool = mock(ExecutorService.class);
        server = EchoServer.create(VALID_PORT, mockThreadPool);
    }

    @SneakyThrows
    @Test
    void testStart_WithValidPort() {
        serverThread = new Thread(() -> server.start());
        serverThread.start();
        Thread.sleep(200);

        assertTrue(serverThread.isAlive(), "Сервер должен быть запущен");

        server.stop();

        var startTime = System.currentTimeMillis();
        while (serverThread.isAlive() && (System.currentTimeMillis() - startTime < 3000)) {
            Thread.sleep(50);
        }

        assertFalse(serverThread.isAlive(), "Поток сервера должен завершиться после stop()");
        System.out.printf("Сервер корректно запущен и остановлен в отдельном потоке%n");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 65536})
    void testStart_WithInvalidPortThrowsException(int invalidPort) {
        var exception = assertThrows(EchoServerException.class,
                () -> EchoServer.create(invalidPort).start(),
                "Запуск сервера на неверном порту должен вызвать EchoServerException"
        );

        var expectedMsg = format("[SERVER] Invalid port number: %d", invalidPort);
        assertEquals(expectedMsg, exception.getMessage(),
                format("Неверный текст исключения: '%s'%n", exception.getMessage())
        );

        System.out.println("При запуске с некорректным портом выбрасывается корректное исключение.");
    }

    @SneakyThrows
    @Test
    void testStart_IOExceptionOnServerSocketCreation_ThrowsEchoServerException() {
        var server = TestableEchoServer.builder().port(VALID_PORT).build();

        var exception = assertThrows(EchoServerException.class, server::start,
                "Запуск сервера должен выбросить EchoServerException при ошибке создания ServerSocket"
        );

        var expectedMsg = format("[SERVER] Didn't manage to start server on port %d!%n", VALID_PORT);
        assertEquals(expectedMsg, exception.getMessage(),
                String.format("Неверный текст исключения: '%s'%n", exception.getMessage())
        );

        System.out.printf("Тест пройден: при ошибке создания ServerSocket выбрасывается EchoServerException с корректным сообщением для порта %d.%n", VALID_PORT);
    }

    @Test
    void testStopClosesResources() {
        Mockito.verify(mockThreadPool, Mockito.atLeastOnce()).shutdown();
        System.out.printf("Пул потоков корректно закрыт%n");
    }

    @Test
    void testHandleClient_WithClosedSocketThrowsException() throws Exception {
        var mockSocket = mock(Socket.class);
        when(mockSocket.isClosed()).thenReturn(true);
        when(mockSocket.getInputStream()).thenThrow(new IOException("socket closed"));

        var method = EchoServer.class.getDeclaredMethod("handleClient", Socket.class);
        method.setAccessible(true);

        var output = tapSystemErr(() -> assertDoesNotThrow(
                () -> method.invoke(server, mockSocket),
                "Обработка закрытого сокета не должна приводить к необработанному исключению"
        ));

        assertTrue(
                output.contains("[SERVER] Ошибка ввода‑вывода с клиентом: socket closed"),
                format(
                        "Ожидаемое сообщение не найдено в выводе консоли.%nФактический вывод:%n%s%nОжидалось: '%s'%n",
                        output,
                        "[SERVER] Ошибка ввода‑вывода с клиентом: socket closed"
                )
        );

        System.out.println("Исключение корректно перехвачено и обработано в handleClient()");
    }

    @SneakyThrows
    @Test
    void testHandleClient_SocketException() {
        var mockClientSocket = mock(Socket.class);

        var mockAddress = InetAddress.getByName(VALID_HOST);
        when(mockClientSocket.getInetAddress()).thenReturn(mockAddress);

        var mockInput = mock(InputStream.class);
        var mockOutput = mock(OutputStream.class);
        when(mockClientSocket.getInputStream()).thenReturn(mockInput);
        when(mockClientSocket.getOutputStream()).thenReturn(mockOutput);

        var mockReader = mock(BufferedReader.class);
        var mockWriter = mock(PrintWriter.class);
        when(mockReader.readLine()).thenThrow(new SocketException("Connection reset"));

        var createBufferedReaderMethod = EchoServer.class.getDeclaredMethod("createBufferedReader", InputStream.class);
        createBufferedReaderMethod.setAccessible(true);

        var createPrintWriterMethod = EchoServer.class.getDeclaredMethod("createPrintWriter", OutputStream.class);
        createPrintWriterMethod.setAccessible(true);

        var spyServer = spy(server);
        doReturn(mockReader).when(spyServer).createBufferedReader(any());
        doReturn(mockWriter).when(spyServer).createPrintWriter(any());

        var handleClientMethod = EchoServer.class.getDeclaredMethod("handleClient", Socket.class);
        handleClientMethod.setAccessible(true);

        var output = tapSystemOut(() -> handleClientMethod.invoke(spyServer, mockClientSocket));

        assertTrue(output.contains("[SERVER] Клиент отключился: " + mockAddress),
                "Должно быть сообщение о отключении клиента");
    }

    @SneakyThrows
    @Test
    void testHandleClient_IOExceptionInFinally() {
        var mockClientSocket = mock(Socket.class);

        var mockInput = mock(InputStream.class);
        var mockOutput = mock(OutputStream.class);
        when(mockClientSocket.getInputStream()).thenReturn(mockInput);
        when(mockClientSocket.getOutputStream()).thenReturn(mockOutput);

        var mockReader = mock(BufferedReader.class);
        var mockWriter = mock(PrintWriter.class);
        when(mockReader.readLine()).thenReturn("test message", null);

        doThrow(new IOException("Failed to close socket")).when(mockClientSocket).close();

        var spyServer = spy(server);
        doReturn(mockReader).when(spyServer).createBufferedReader(any());
        doReturn(mockWriter).when(spyServer).createPrintWriter(any());

        var method = EchoServer.class.getDeclaredMethod("handleClient", Socket.class);
        method.setAccessible(true);

        var outText = tapSystemOut(() -> method.invoke(spyServer, mockClientSocket));
        var errText = tapSystemErr(() -> method.invoke(spyServer, mockClientSocket));

        assertTrue(outText.contains("[SERVER] Получено: test message"),
                "Должно быть сообщение о полученном сообщении");
        assertTrue(outText.contains("[SERVER] Отправлено эхо: test message"),
                "Должно быть сообщение об отправленном эхо");

        assertTrue(errText.contains("[SERVER] Ошибка при закрытии клиентского сокета: Failed to close socket"),
                "Должна быть ошибка закрытия сокета в System.err");
    }

    @SuperBuilder
    private static class TestableEchoServer extends EchoServer {
        @Override
        protected ServerSocket createServerSocket(int port) throws IOException {
            throw new IOException("Failed to bind");
        }
    }
}
