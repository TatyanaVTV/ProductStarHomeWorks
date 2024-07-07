package core.collections.review;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class DuplicatesRemover {
    public static final ArrayList<String> TEST_ARRAY_LIST_OF_STRINGS =
            new ArrayList<>(List.of("One", "Two", "One", "Three", "Four", "Two", "Five", "Two", "Four", "Five", "Six", "Seven"));

    public static ArrayList<String> removeDuplicates(ArrayList<String> originalList) {
        // Linked-вариант выбран для сохранения порядка значений
        LinkedHashSet<String> setOfStrings = new LinkedHashSet<>(originalList);
        originalList.clear();
        originalList.addAll(setOfStrings);
        return originalList;
    }
}
