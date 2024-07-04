package core.collections.review;

import java.util.HashMap;

import static core.collections.review.DuplicatesRemover.TEST_ARRAY_LIST_OF_STRINGS;
import static core.collections.review.DuplicatesRemover.removeDuplicates;
import static core.collections.review.HashMapSwitcher.*;
import static core.collections.review.ListsAccessToElementsSpeedComparator.*;

public class HomeWork {
    public static void main(String[] args) {
        System.out.println("======================= HashMap ======================");
        HashMap<Integer, String> originalHashMap = getTestHashMap();
        HashMap<String, Integer> switchedHaspMap = switchKeysAndValues(originalHashMap);
        printMaps(originalHashMap, switchedHaspMap);

        System.out.println("\n\n============== Getting elements by Index ==============");
        compareArrayListAndLinkedList();

        System.out.println("\n====================== ArrayList ======================");
        System.out.println("Original ArrayList of Strings: " + TEST_ARRAY_LIST_OF_STRINGS);
        System.out.println("Same ArrayList of Strings without duplicates: " + removeDuplicates(TEST_ARRAY_LIST_OF_STRINGS));
    }
}
