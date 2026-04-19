package ru.vtv.hw.practical.cafe;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import static java.time.Duration.ofSeconds;
import static java.util.Objects.isNull;

@Slf4j
@Builder
public class CafeCustomer implements Runnable {
    private final Cafe cafe;
    private final String name;

    private static final int MAX_ATTEMPTS = 10;
    private static final int ATTEMPT_INTERVAL_SEC = 2;

    public static CafeCustomer createNewCustomer(Cafe cafe, String name) {
        return CafeCustomer.builder()
                .cafe(cafe)
                .name(name)
                .build();
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        log.info("Посетитель {} зашел в кафе", name);

        var attempts = 0;
        while (!cafe.isFinished() && attempts < MAX_ATTEMPTS) {
            attempts++;
            var takenDish = cafe.takeOrder();
            if (isNull(takenDish)) {
                break; // Все блюда приготовлены
            }

            try {
                Thread.sleep(ofSeconds(ATTEMPT_INTERVAL_SEC));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        log.info("Посетитель {} покинул кафе", name);
    }
}
