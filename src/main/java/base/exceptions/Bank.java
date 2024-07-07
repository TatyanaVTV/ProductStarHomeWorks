package base.exceptions;

public class Bank {
    public static void main(String[] args) {
        Account account = new Account("Masha", 10000.00);

        tryToWithdraw(account, "Masha", 2000.00);
        tryToWithdraw(account, "Masha", 10000.00);
        tryToWithdraw(account, "Dima", 1000.00);
    }

    private static void tryToWithdraw(Account account, String ownerToVerify, double amount) {
        try {
            account.withdrawMoneyFromAccount(ownerToVerify, amount);
        } catch (NotEnoughMoneyException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
