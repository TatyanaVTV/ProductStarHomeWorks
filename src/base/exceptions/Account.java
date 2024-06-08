package base.exceptions;

public class Account {
    private final String ownerName;
    private double balance;

    public Account(String ownerName, double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public void withdrawMoneyFromAccount(String nameToVerify, double sumToWithdraw) throws NotEnoughMoneyException {
        if (nameToVerify.equals(this.ownerName)) {
            if (balance >= sumToWithdraw) {
                balance -= sumToWithdraw;
                System.out.printf("Withdrawing money from account. Owner: %s, sum: -%f, new balance: %f%n", ownerName, sumToWithdraw, balance);
            } else {
                throw new NotEnoughMoneyException();
            }
        } else {
            throw new NotAuthorizedAccessToAccountException();
        }
    }
}
