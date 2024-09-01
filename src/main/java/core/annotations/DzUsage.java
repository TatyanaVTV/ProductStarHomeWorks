package core.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DzUsage {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException
            , InvocationTargetException, InstantiationException, IllegalAccessException {
        Class clazz = Class.forName("core.annotations.DZ");

        DZ dz1 = (DZ) clazz.getDeclaredConstructor(int.class, String.class, List.class)
                .newInstance(1, "one", List.of(1.00, 11.00, 111.00));
        DZ dz2 = (DZ) clazz.getDeclaredConstructor(int.class, String.class, List.class)
                .newInstance(2, "two", List.of(2.00, 22.00, 222.00));

        System.out.printf("dz1: %d - %s - %s%n", dz1.getI(), dz1.getS(), dz1.getList());
        System.out.printf("dz2: %d - %s - %s%n", dz2.getI(), dz2.getS(), dz2.getList());

        Method sumMethod = clazz.getDeclaredMethod("getSumInteger", clazz, clazz);
        sumMethod.setAccessible(true);
        int sum = (int) sumMethod.invoke(dz1, dz1, dz2);
        System.out.printf("Sum of dz1 and dz2 = %d%n", sum);

        Method sumFromListMethod = clazz.getDeclaredMethod("getSumFromList", clazz, clazz);
        sumFromListMethod.setAccessible(true);
        List<Double> sumFromList = (List<Double>) sumFromListMethod.invoke(dz1, dz1, dz2);
        System.out.printf("Sum from List of dz1 and dz2 = %s", sumFromList);
    }
}
