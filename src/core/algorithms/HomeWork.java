package core.algorithms;

import java.util.Arrays;
import java.util.Random;

import static core.algorithms.InsertSort.insertionSort;
import static core.algorithms.ShellSort.shellSort;

public class HomeWork {
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        // 10, 100 и 200_000 в задании не было, просто стало интересно
        testSort(10);
        testSort(100);
        testSort(1000);
        testSort(10_000);
        testSort(100_000);
        testSort(200_000);
    }

    private static void testSort(int size) {
        System.out.printf("============ %d элементов ============\n", size);
        int[] testedArray = generate(size);

        /* Для каждой сортировки - копия изначального тестового массива, чтобы все сортировки сортировали \
            одинаковые исходные массивы, а не пересортировывали то, что уже отсортировано до них */
        int[] sortedArray = Arrays.copyOf(testedArray, size);
        long startTime = System.nanoTime();
        insertionSort(sortedArray);
        long manualTime = System.nanoTime() - startTime;
        System.out.printf("Сортировка вставками: \t\t%d ns\n", manualTime);

        sortedArray = Arrays.copyOf(testedArray, size);
        startTime = System.nanoTime();
        shellSort(sortedArray);
        manualTime = System.nanoTime() - startTime;
        System.out.printf("Сортировка Шелла:    \t\t%d ns\n", manualTime);

        sortedArray = Arrays.copyOf(testedArray, size);
        startTime = System.nanoTime();
        Arrays.sort(sortedArray);
        manualTime = System.nanoTime() - startTime;
        System.out.printf("Сортировка Arrays.sort(): \t%d ns\n\n", manualTime);
    }

    private static int[] generate(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = rnd.nextInt(4000);
        }
        return result;
    }
}

/*
============ 10 элементов ============
Сортировка вставками: 		245100 ns
Сортировка Шелла:    		242200 ns
Сортировка Arrays.sort(): 	1037300 ns

============ 100 элементов ============
Сортировка вставками: 		101700 ns
Сортировка Шелла:    		33600 ns
Сортировка Arrays.sort(): 	832500 ns

============ 1000 элементов ============
Сортировка вставками: 		1936700 ns
Сортировка Шелла:    		302700 ns
Сортировка Arrays.sort(): 	559300 ns

============ 10000 элементов ============
Сортировка вставками: 		23552400 ns
Сортировка Шелла:    		2068900 ns
Сортировка Arrays.sort(): 	2436800 ns

============ 100000 элементов ============
Сортировка вставками: 		2376570800 ns
Сортировка Шелла:    		9457400 ns
Сортировка Arrays.sort(): 	8096500 ns

============ 200000 элементов ============
Сортировка вставками: 		9523957000 ns
Сортировка Шелла:    		18965500 ns
Сортировка Arrays.sort(): 	10308700 ns
* */