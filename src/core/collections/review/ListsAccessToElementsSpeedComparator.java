package core.collections.review;

import java.util.*;

public class ListsAccessToElementsSpeedComparator {
    private static final Random RND = new Random();

    private static ArrayList<Integer> getArrayListOfRandomMillionIntegers() {
        ArrayList<Integer> resultList = new ArrayList<>();

        for (int size = 0; size < 1_000_000; size++) {
            resultList.add(RND.nextInt(1000));
        }

        return resultList;
    }

    private static LinkedList<Integer> getLinkedListOfRandomMillionIntegers() {
        LinkedList<Integer> resultList = new LinkedList<>();

        for (int size = 0; size < 1_000_000; size++) {
            resultList.add(RND.nextInt(1000));
        }

        return resultList;
    }

    public static void compareArrayListAndLinkedList() {
        ArrayList<Integer> arrayList = getArrayListOfRandomMillionIntegers();
        LinkedList<Integer> linkedList = getLinkedListOfRandomMillionIntegers();

        TreeSet<Long> accessTimeForArrayList = new TreeSet<>();
        TreeSet<Long> accessTimeForLinkedList = new TreeSet<>();

        for (int count = 0; count < 1000; count++) {
            int index = RND.nextInt(1_000_000);
            accessTimeForArrayList.add(getTimeForAccessingRandomElementByIndex(arrayList, index));
            accessTimeForLinkedList.add(getTimeForAccessingRandomElementByIndex(linkedList, index));
        }

        System.out.printf("Minimal time for ArrayList:  %d ns\n", accessTimeForArrayList.first());
        System.out.printf("Maximum time for ArrayList:  %d ns\n", accessTimeForArrayList.last());
        System.out.printf("Minimal time for LinkedList: %d ns\n", accessTimeForLinkedList.first());
        System.out.printf("Maximum time for LinkedList: %d ns\n", accessTimeForLinkedList.last());
    }

    private static long getTimeForAccessingRandomElementByIndex(List list, int index) {
        long startTime = System.nanoTime();
        list.get(index);
        return System.nanoTime() - startTime;
    }
}