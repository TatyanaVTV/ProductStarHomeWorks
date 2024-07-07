package core.algorithms;

public class InsertSort {
    public static void insertionSort(int[] originalArray) {
        for (int i = 1; i< originalArray.length; i++) {
            int indexOfLastSorted = i - 1;
            while (indexOfLastSorted > -1 && originalArray[indexOfLastSorted] > originalArray[indexOfLastSorted+1]) {
                int temp = originalArray[indexOfLastSorted];
                originalArray[indexOfLastSorted] = originalArray[indexOfLastSorted+1];
                originalArray[indexOfLastSorted+1] = temp;
                indexOfLastSorted--;
            }
        }
    }
}
