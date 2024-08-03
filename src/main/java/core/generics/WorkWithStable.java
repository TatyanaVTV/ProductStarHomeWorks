package core.generics;

import java.util.List;

public class WorkWithStable {
    public static void main(String[] args) {
        Stable<BaseHorse> stable =  new Stable<>();

        stable.addAnimal(new AbyssinianHorse());
        stable.addAnimal(new ArabicHorse());
        stable.addAnimal(new ShireHorse());

        List<BaseHorse> differentHorses = List.of(new AbyssinianHorse(), new ArabicHorse(), new ShireHorse());
        stable.addAllAnimals(differentHorses);

        List<ArabicHorse> arabicHorses = List.of(new ArabicHorse(), new ArabicHorse(), new ArabicHorse());
        stable.addAllAnimals(arabicHorses);

        Stable<IAnimal> newStable = new Stable<>();
        Stable.moveAllToAnotherStable(stable, newStable);

        stable.getAllAnimals().forEach(System.out::println);
        System.out.println("=============================");
        newStable.getAllAnimals().forEach(System.out::println);
    }

}
