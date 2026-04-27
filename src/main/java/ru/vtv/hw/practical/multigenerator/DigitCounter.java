package ru.vtv.hw.practical.multigenerator;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DigitCounter {
    private final AtomicInteger twoDigitCount = new AtomicInteger(0);
    private final AtomicInteger threeDigitCount = new AtomicInteger(0);
    private final AtomicInteger fourDigitCount = new AtomicInteger(0);

    public void processNumbers(List<Integer> numbers) throws InterruptedException {
        var twoDigitThread = new Thread(() -> {
            for (Integer number : numbers) {
                if (number >= 10 && number <= 99) {
                    twoDigitCount.incrementAndGet();
                }
            }
        });

        var threeDigitThread = new Thread(() -> {
            for (Integer number : numbers) {
                if (number >= 100 && number <= 999) {
                    threeDigitCount.incrementAndGet();
                }
            }
        });

        var fourDigitThread = new Thread(() -> {
            for (Integer number : numbers) {
                if (number >= 1000 && number <= 9999) {
                    fourDigitCount.incrementAndGet();
                }
            }
        });

        twoDigitThread.start();
        threeDigitThread.start();
        fourDigitThread.start();

        twoDigitThread.join();
        threeDigitThread.join();
        fourDigitThread.join();
    }

    public int getTwoDigitCount() {
        return twoDigitCount.get();
    }

    public int getThreeDigitCount() {
        return threeDigitCount.get();
    }

    public int getFourDigitCount() {
        return fourDigitCount.get();
    }
}
