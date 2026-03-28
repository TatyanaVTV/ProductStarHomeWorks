package base.practical.third.family;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor

public class FamilyMember {
    @Getter private final String name;
    private final Set<FamilyMember> parents = new HashSet<>();
    private final Set<FamilyMember> children = new HashSet<>();

    public Set<FamilyMember> getParents() {
        return Set.copyOf(parents);
    }

    public Set<FamilyMember> getChildren() {
        return Set.copyOf(children);
    }

    public void addChild(String name) {
        var child = new FamilyMember(name);
        children.add(child);
        child.parents.add(this);
    }

    public void addParent(String name) {
        var parent = new FamilyMember(name);
        parents.add(parent);
        parent.children.add(this);
    }

    /**
     * По заданию нет однозначности: надо ли искать всех родственников родственника с таким именем (как бы их не звали),
     * либо надо у текущего найти всех родственников с заданным именем.
     * */
    public Set<FamilyMember> findRelatives(String relativeName) {
        var relatives = new HashSet<FamilyMember>();
        findRelativesRecursive(this, relativeName, relatives, new HashSet<>());
        return relatives;
    }

    public void printTree() {
        System.out.println("Генеалогическое дерево для: " + this.name);
        printTreeRecursive(this, 0, new HashSet<>());
    }

    private void findRelativesRecursive(FamilyMember current, String targetName,
                                        Set<FamilyMember> result, Set<FamilyMember> visited) {
        if (visited.contains(current)) {
            return;
        }
        visited.add(current);

        // если имя текущего проверяемого родственника совпадает с искомым - добавляем в результаты поиска
        if (current.name.equals(targetName)) {
            result.add(current);
        }

        // Рекурсивно ищем среди родителей
        for (FamilyMember parent : current.parents) {
            findRelativesRecursive(parent, targetName, result, visited);
        }

        // Рекурсивно ищем среди детей
        for (FamilyMember child : current.children) {
            findRelativesRecursive(child, targetName, result, visited);
        }
    }

    private void printTreeRecursive(FamilyMember member, int depth, Set<FamilyMember> visited) {
        if (visited.contains(member)) {
            var indent = " ".repeat(depth * 4); // отступы при выводе
            System.out.println(indent + member.name + " (уже выведен ранее)");
            return;
        }
        visited.add(member);

        var indent = " ".repeat(depth * 4); // отступы при выводе
        System.out.println(indent + member.name);

        // Выводим детей с увеличенной глубиной
        for (FamilyMember child : member.children) {
            printTreeRecursive(child, depth + 1, visited);
        }
    }
}
