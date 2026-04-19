package ru.vtv.hw.practical.cafe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.time.Duration.ofSeconds;
import static ru.vtv.hw.practical.cafe.MenuItem.getRandomMenuItem;

@Slf4j
@RequiredArgsConstructor
public class Chef implements Runnable {
    private final Cafe cafe;

    @Override
    public void run() {
        log.info("Повар начал работу");
        while (!cafe.isFinished()) {
            var menuItem = getRandomMenuItem();

            log.info("Повар начал готовить {}, ожидаемое время готовки: {}с",
                    menuItem.getRusName(), menuItem.getSecondsToCook());

            try {
                Thread.sleep(ofSeconds(menuItem.getSecondsToCook()));
                cafe.addOrder(menuItem);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        log.info("Повар закончил работу — приготовлено 10 блюд");
    }
}
