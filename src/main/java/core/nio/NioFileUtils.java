package core.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioFileUtils {
    private static final String FILE_NAME = "nio_test.txt";

    public static void main(String[] args) {
        readFromConsoleAndSaveToFile();
        readFromFileAndPrintToConsole();
    }

    private static void readFromConsoleAndSaveToFile() {
        Path filePath = Paths.get(FILE_NAME);
        try(FileChannel fileChannel = FileChannel.open(filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            // creating buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                // reading data from console
                String input = System.console().readLine();
                if (input.isEmpty()) {
                    break;
                }
                buffer.clear();
                buffer.put(input.getBytes());
                buffer.put(System.lineSeparator().getBytes());
                buffer.flip();

                // if buffer has smth -> write to file
                while (buffer.hasRemaining()) {
                    fileChannel.write(buffer);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFromFileAndPrintToConsole() {
        Path filePath = Paths.get(FILE_NAME);

        try(FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            // creating buffer
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());

            // read from file to buffer
            fileChannel.read(buffer);

            // prepare buffer for reading
            buffer.flip();

            System.out.println(new String(buffer.array()));
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
