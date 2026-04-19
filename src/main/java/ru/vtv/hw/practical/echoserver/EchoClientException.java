package ru.vtv.hw.practical.echoserver;

import static java.lang.String.format;

public class EchoClientException extends RuntimeException {
    private EchoClientException(String message) {
        super(message);
    }

    private EchoClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EchoClientException connectionRefused(String host, int port) {
        return new EchoClientException(
                format("[CLIENT] Didn't manage to connect with server %s:%d. Check if server is running", host, port)
        );
    }

    public static EchoClientException connectionTimeout(String host, int port) {
        return new EchoClientException(format("[CLIENT] Connection timeout: %s:%d", host, port));
    }

    public static EchoClientException unknownHost(String host) {
        return new EchoClientException(format("[CLIENT] Unknown host: %s", host));
    }

    public static EchoClientException clientIOException(Throwable cause) {
        return new EchoClientException(format("[CLIENT] Input/Output exception: %s", cause.getMessage()), cause);
    }
}
