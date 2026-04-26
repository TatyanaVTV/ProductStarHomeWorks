package ru.vtv.hw.practical.echoserver;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static ru.vtv.hw.practical.echoserver.EchoClientException.*;

@RequiredArgsConstructor
public class EchoClient {
    private final String host;
    private final int port;

    public void sendMessage(String message) {
        try (var socket = createSocket(host, port);
             var outputStream = socket.getOutputStream();
             var inputStream = socket.getInputStream();
             var writer = new PrintWriter(outputStream, true);
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {

            writer.println(message);
            System.out.printf("[CLIENT] Отправлено на сервер: %s%n", message);

            var response = reader.readLine();
            System.out.printf("[CLIENT] Эхо-ответ от сервера: %s%n", response);
        } catch (UnknownHostException e) {
            System.err.printf("[CLIENT] Неизвестный хост: %s%n", host);
            throw unknownHost(host);
        } catch (ConnectException e) {
            System.err.printf(
                    "[CLIENT] Не удалось подключиться к серверу %s:%s (проверьте, запущен ли сервер)%n", host, port
            );
            throw connectionRefused(host, port);
        } catch (SocketTimeoutException e) {
            System.err.println("[CLIENT] Таймаут подключения к серверу");
            throw connectionTimeout(host, port);
        } catch (IOException e) {
            System.err.printf("[CLIENT] Ошибка ввода‑вывода: %s%n", e.getMessage());
            throw clientIOException(e);
        }
    }

    protected Socket createSocket(String host, int port) throws IOException {
        return new Socket(host, port);
    }
}
