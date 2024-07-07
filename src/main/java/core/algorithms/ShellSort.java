package core.algorithms;

public class ShellSort {
    public static void shellSort(int[] originalArray) {
        for (int step = originalArray.length / 2; step > 0 ; step = step >> 1) {
            for (int i = step; i < originalArray.length; i++) {
                int checkedPosition = i;
                while (checkedPosition >= step && originalArray[checkedPosition - step] > originalArray[checkedPosition]) {
                    int temp = originalArray[checkedPosition];
                    originalArray[checkedPosition] = originalArray[checkedPosition - step];
                    originalArray[checkedPosition - step] = temp;
                    checkedPosition -= step;
                }
            }
        }
    }
}
