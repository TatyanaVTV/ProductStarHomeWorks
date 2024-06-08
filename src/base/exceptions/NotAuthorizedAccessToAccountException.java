package base.exceptions;

public class NotAuthorizedAccessToAccountException extends RuntimeException {
    public NotAuthorizedAccessToAccountException() {
        super("An attempt to withdraw money from someone else's account.");
    }
}
