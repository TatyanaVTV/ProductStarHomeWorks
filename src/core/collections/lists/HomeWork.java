package core.collections.lists;

import java.util.*;

public class HomeWork {
    private static final Random RND = new Random();

    public static void main(String[] args) {
        ArrayList<Integer> originalList = generateListOfIntegers(4 + RND.nextInt(3));
        int k = 2;
        System.out.printf("Average values of subLists with k = %d\n", k);
        System.out.println(getRollingAverage(originalList, k));

        System.out.println("\nAverage values of subLists for example from task ([1, 2, 3, 5], k = 2):");
        System.out.println(getRollingAverage(new ArrayList<>(Arrays.asList(1, 2, 3, 5)), k));
    }

    private static ArrayList<Integer> generateListOfIntegers(int size) {
        ArrayList<Integer> newList = new ArrayList<>();
        for (int count = 0; count < size; count++) {
            newList.add(RND.nextInt( 10));
        }
        System.out.println("Generated list: " + newList);
        return newList;
    }

    /** Сигнатура метода отличается от той, что в задании, для корректного вывода средних значений. */
    private static List<Double> getRollingAverage(ArrayList<Integer> arr, int k) {
        List<Double> resultList = new LinkedList<>();
        LinkedList<Integer> subList = new LinkedList<>();
        int sum = 0;

        for (int i = 0; i < k; i++) {
            subList.add(arr.get(i));
            sum += arr.get(i);
        }
        System.out.println(subList);
        resultList.add(sum / (double) k);

        for (int i = k; i < arr.size(); i++) {
            subList.add(arr.get(i));
            sum = sum + arr.get(i) - subList.getFirst();
            subList.remove();
            System.out.println(subList);
            resultList.add(sum / (double) k);
        }

        return resultList;
    }
}
