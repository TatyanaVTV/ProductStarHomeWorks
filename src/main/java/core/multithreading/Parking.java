package core.multithreading;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Parking {
    private static final boolean[] PARKING_PLACES = new boolean[5];
    private static final Semaphore SMP = new Semaphore(PARKING_PLACES.length, true);
    private static final Random RND = new Random();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i < 11; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(500); // эмуляция работы "парковщика"
        }
    }

    public static class Car implements Runnable {
        private final int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            int mockTime = 3 + RND.nextInt(6);
            System.out.printf("Car #%s has arrived at the Parking lot and is waiting for a parking space.%n", carNumber);
            try {
                SMP.acquire();
                int parkingSpaceNumber = 0;
                synchronized (PARKING_PLACES) {
                    for (int i = 0; i < PARKING_PLACES.length; i++) {
                        if (!PARKING_PLACES[i]) {
                            parkingSpaceNumber = i;
                            PARKING_PLACES[parkingSpaceNumber] = true;
                            break;
                        }
                    }
                }
                // Логирование за пределами синхронизации, т.к. в этот момент уже нет смысла держать занятым общий ресурс
                System.out.printf(">> Car #%s has occupied parking space #%s for %s sec.%n", carNumber, parkingSpaceNumber, mockTime);

                Thread.sleep(mockTime * 1000L); // время стоянки на парковке
                synchronized (PARKING_PLACES) {
                    PARKING_PLACES[parkingSpaceNumber] = false;
                }
                // Логирование за пределами синхронизации, т.к. в этот момент уже нет смысла держать занятым общий ресурс
                System.out.printf("<< Car #%s has left parking space #%s.%n", carNumber, parkingSpaceNumber);

                SMP.release();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
