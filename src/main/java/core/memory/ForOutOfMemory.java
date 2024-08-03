package core.memory;

import java.util.ArrayList;

public class ForOutOfMemory {
    private static ArrayList<String> someStaticList = new ArrayList<>();

    public static void main(String[] args) {
        fillingStaticListInUnEndingCycle(); // -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/disk2/dumps
    }

    private static void fillingStaticListInUnEndingCycle() {
        while (true) {
            someStaticList.add("Smth");
        }
    }
}
