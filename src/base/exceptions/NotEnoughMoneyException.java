package base.exceptions;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {
        super("There is not enough money in the account!");
    }
}
