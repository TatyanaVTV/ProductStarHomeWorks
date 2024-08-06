package core.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class FileLoader {
    private static final Scanner IN = new Scanner(System.in);

    private static final String FILE_NAME_REGEX = "[A-Za-z0-9А-Яа-яЁё]+\\.{1}txt";
    private static final String DIRECTORY_REGEX = "^[A-Za-z]:(\\\\[\\w\\s\\(\\)]+)+\\\\?$";

    public static void main(String[] args) {
        while (true) {
            printOptions();
            int command = readOption();

            switch (command) {
                case -1 -> {}
                case 1 -> writeToFile();
                case 2 -> readFromFile();
                case 0 -> {
                    IN.close();
                    return;
                }
                default -> System.out.println("Unknown command.\n");
            }
        }
    }

    private static void printOptions() {
        System.out.println("======================= File Loader ========================");
        System.out.println("1 - Write text to file (fileName, directory, textToWrite)");
        System.out.println("2 - Get file source (fileName, directory)");
        System.out.println("0 - Exit");
        System.out.println("============================================================");
        System.out.println("Choose option: ");
    }

    private static int readOption() {
        try {
            return Integer.parseInt(IN.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static void writeToFile() {
        System.out.println(">>> Writing text to file");

        String fileName = readFileName();
        String directory = readDirectoryPath();
        if (!directory.endsWith("\\")) {
            directory += "\\";
        }
        String filePath = directory + fileName;

        String fileSource = readFileSource();

        File folder = new File(directory);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.printf("Folder '%s' has been created!\n", directory);
            } else {
                System.out.printf("Folder '%s' hasn't been created! It already exists or cannot be created.", directory);
            }
        }
        File fileToSave = new File(filePath.replace("\\", "\\\\"));
        try(FileWriter fileWriter = new FileWriter(fileToSave)) {
            boolean created = fileToSave.createNewFile();
            if (created) {
                System.out.printf("File '%s' has been created.\n", filePath);
            }

            long start = System.nanoTime();
            fileWriter.write(fileSource, 0, fileSource.length());
            fileWriter.flush();
            System.out.printf("File source for '%s' has been saved.\n", filePath);
            System.out.printf("File size: %d b, saving time: %d ns\n", fileToSave.length(), System.nanoTime() - start);
        } catch (IOException io) {
            System.out.println(io.getLocalizedMessage());
        }
    }

    private static void readFromFile() {
        System.out.println(">>> Getting file source");

        String fileName = readFileName();
        String directory = readDirectoryPath();
        if (!directory.endsWith("\\")) {
            directory += "\\";
        }
        String filePath = directory + fileName;

        try {
            File file = new File(filePath.replace("\\", "\\\\"));
            if (file.exists()) {
                List<String> fileSource = Files.readAllLines(file.toPath(), Charset.defaultCharset());
                System.out.printf("----------------------- File source: -----------------------\n%s:\n", filePath);
                fileSource.forEach(System.out::println);
                System.out.println("------------------------------------------------------------");
            } else {
                System.out.printf("File '%s' not found!\n", filePath);
            }
        } catch (IOException io) {
            System.out.println(io.getLocalizedMessage());
        }
    }

    private static String readFileName() {
        return readData("file name", FILE_NAME_REGEX);
    }

    private static String readDirectoryPath() {
        return readData("directory path", DIRECTORY_REGEX);
    }

    private static String readFileSource() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        System.out.println("Type file source (line 'eof' for stop typing):");
        String readedString;
        while(!(readedString = IN.nextLine()).equals("eof")) {
            joiner.add(readedString);
        }
        return joiner.toString();
    }

    private static String readData(String dataType, String pattern) {
        System.out.printf("Type %s: ", dataType);
        String readedString = IN.nextLine();
        while(!readedString.matches(pattern)) {
            System.out.printf("Wrong %s format. \nType %s: ", dataType, dataType);
            readedString = IN.nextLine();
        }
        return readedString;
    }
}