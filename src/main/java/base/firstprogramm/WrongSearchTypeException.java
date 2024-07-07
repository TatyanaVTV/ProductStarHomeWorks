package base.firstprogramm;

class WrongSearchTypeException extends Exception {
    public WrongSearchTypeException() {
        super("Неверное значение типа поиска, либо неизвестная команда! Повторите ввод: ");
    }
}
