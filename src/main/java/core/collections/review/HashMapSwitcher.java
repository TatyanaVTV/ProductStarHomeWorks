package core.collections.review;

import java.util.HashMap;
import java.util.Map;

public class HashMapSwitcher {

    private static final HashMap<Integer, String> TEST_HASH_MAP = new HashMap<>();

    public static HashMap<Integer, String> getTestHashMap() {
        if (TEST_HASH_MAP.isEmpty()) {
            TEST_HASH_MAP.put(1, "One");
            TEST_HASH_MAP.put(2, "Two");
            TEST_HASH_MAP.put(3, "Three");
            TEST_HASH_MAP.put(4, "Four");
            TEST_HASH_MAP.put(5, "Five");
        }
        return TEST_HASH_MAP;
    }

    public static HashMap<String, Integer> switchKeysAndValues(HashMap<Integer, String> originalHashMap) {
        HashMap<String, Integer> newHaspMap = new HashMap<>();

        for (Map.Entry<Integer, String> entry : originalHashMap.entrySet()) {
            newHaspMap.put(entry.getValue(), entry.getKey());
        }

        return newHaspMap;
    }

    public static void printMaps(HashMap<Integer, String> original, HashMap<String, Integer> switched) {
        System.out.println("Original HashMap: ");
        original.forEach((key, value) -> System.out.printf(" [%d -> %s] ", key, value));
        System.out.println("\nSwitched HashMap: ");
        switched.forEach((key, value) -> System.out.printf(" [%s -> %d] ", key, value));
    }
}
