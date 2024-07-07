package base.firstprogramm;

class ExitInitializedException extends Exception {
    public ExitInitializedException() {
        super("Введена команда для выхода из приложения!");
    }
}
