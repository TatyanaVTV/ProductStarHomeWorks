package core.generics;

import java.util.ArrayList;
import java.util.List;

public class Stable<T> {
    private List<T> animalsInStable = new ArrayList<>();

    public void addAnimal(T newAnimal) {
        animalsInStable.add(newAnimal);
    }

    public List<T> getAllAnimals() {
        return animalsInStable;
    }

    public void addAllAnimals(List<? extends T> animals) {
        animalsInStable.addAll(animals);
    }

    public static <T> void moveAllToAnotherStable(Stable<? extends T> stableProvider, Stable<? super T> stableConsumer) {
        stableConsumer.addAllAnimals(stableProvider.getAllAnimals());
    }
}
