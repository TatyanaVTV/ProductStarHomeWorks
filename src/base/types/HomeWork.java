package base.types;

import java.util.Arrays;

public class HomeWork {

    public static void main(String[] args) {
        Friend[] friends = {
                new Friend("Вася", 18, true, 4),
                new Friend("Катя", 19, false, 3),
                new Friend("Дима", 20, true, 2),
                new Friend("Маша", 20, false, 1),
                new Friend("Саша", 20, true, 0),
                new Friend("Коля", 20, false, 1)
        };
        //System.out.println("My friends: " + Arrays.toString(friends));

        Arrays.stream(friends).forEach(System.out::println);
    }
}

class Friend {
    private String name;
    private int age;
    private boolean isFriendFromSchool;
    private float hoursSpentTogetherLastWeek;

    public Friend(String name, int age, boolean isFriendFromSchool, float hoursSpentTogetherLastWeek) {
        this.name = name;
        this.age = age;
        this.isFriendFromSchool = isFriendFromSchool;
        this.hoursSpentTogetherLastWeek = hoursSpentTogetherLastWeek;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isFriendFromSchool=" + isFriendFromSchool +
                ", hoursSpentTogetherLastWeek=" + hoursSpentTogetherLastWeek +
                '}';
    }
}
