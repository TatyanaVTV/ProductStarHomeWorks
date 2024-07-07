package base.types;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Courses {
    public static void main(String[] args) {
        var availableCourses = new Course[]{
                new Course("Programming Basics", 5, false, 0.00),
                new Course("Java", 144, true, 130000.00),
                new Course("Kotlin", 90, false, 90000.00),
        };

        System.out.println("Available Courses: " + Arrays.toString(availableCourses));

        Course[] futureCourses = {
                new Course("JavaScript", 44, true, 35000.00),
                new Course("Agile Basics", 8, false, 5000.00),
                new Course("Python", 100, true, 100000.00),
        };

        System.out.println("\nFuture Courses: ");
        Arrays.asList(futureCourses).forEach(System.out::println);

        Course[][] educationPlan = {
                { new Course("Programming Basics", 5, false, 0.00),
                        new Course("Java", 144, true, 130000.00),
                        new Course("Agile Basics", 8, false, 5000.00) } ,
                {
                        new Course("JavaScript", 20, true, 35000.00),
                        new Course("Java 2", 90, false, 90000.00),
                        new Course("Kotlin", 47, true, 100000.00) }
        };

        System.out.println("\nEducation plan: ");
        AtomicInteger year = new AtomicInteger(1);
        Arrays.asList(educationPlan).forEach(courseArray -> {
            System.out.println(year.getAndIncrement() + " year: ");
            Arrays.asList(courseArray).forEach(System.out::println);
        });

    }
}

class Course {
    private String title;
    private int hours;
    private boolean hasExam;
    private double cost;

    public Course(String title, int hours, boolean hasExam, double cost) {
        this.title = title;
        this.hours = hours;
        this.hasExam = hasExam;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", hours=" + hours +
                ", hasExam=" + hasExam +
                ", cost=" + cost +
                '}';
    }
}
