package ru.vtv.hw.practical.cafe;

public class CafeCheck {

    public static void main(String[] args) throws InterruptedException {
        var cafe = new Cafe();

        var chef = new Thread(new Chef(cafe), "Повар");
        var customer1 = new Thread(CafeCustomer.createNewCustomer(cafe, "1"));
        var customer2 = new Thread(CafeCustomer.createNewCustomer(cafe, "2"));
        var customer3 = new Thread(CafeCustomer.createNewCustomer(cafe, "3"));

        chef.start();
        customer1.start();
        customer2.start();
        customer3.start();

        chef.join();

        customer1.join();
        customer2.join();
        customer3.join();

        System.out.println("Ресторан закрывается — все блюда розданы");
    }
}
