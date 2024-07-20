package core.streamapi;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class LongestSegment {
    public static void main(String[] args) throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(LongestSegment.class.getClassLoader().getResource("segments.txt").getFile()))) {
            double maxSegmentLength = reader.lines().map(s -> {
                List<Integer> coordinates = Arrays.stream(s.split("[^0-9\\-]+"))
                        .filter(part -> part.matches("^-?[0-9]+$"))
                        .map(Integer::parseInt)
                        .toList();
                return Math.sqrt(Math.pow(coordinates.get(2) - coordinates.get(0), 2) + Math.pow(coordinates.get(3) - coordinates.get(1), 2));
            }).max(Double::compareTo).orElse(0.00);
            System.out.printf("Max segment length = %f", maxSegmentLength); // (x:2,y:3)-(x:5,y:7) -> 5.00
        } catch (NullPointerException e) {
            System.out.println("File not found in resources!");
            throw e;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}