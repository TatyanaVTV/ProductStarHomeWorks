package ru.vtv.hw.practical.echoserver;

import static java.lang.String.format;

public class EchoServerException  extends RuntimeException {
    private EchoServerException(String message) {
        super(message);
    }

    public static EchoServerException notStarted(int port) {
        return new EchoServerException(format("[SERVER] Didn't manage to start server on port %d!%n", port));
    }

    public static EchoServerException invalidPort(int port) {
        return new EchoServerException(format("[SERVER] Invalid port number: %d", port));
    }
}
