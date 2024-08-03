package core.memory;

public class ForStackOverFlow {
    public static void main(String[] args) {
        doSmthRecursively();
    }

    private static void doSmthRecursively() {
        doSmthRecursively();
    }
}