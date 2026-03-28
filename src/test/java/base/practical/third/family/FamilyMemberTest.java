package base.practical.third.family;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class FamilyMemberTest {
    private final String JOHN = "John";
    private final String MARY = "Mary";
    private final String ALICE = "Alice";
    private final String BOB = "Bob";
    private final String CHARLIE = "Charlie";

    private final String UNKNOWN = "Unknown";

    private final String CHECK_PARENT_NAME_MSG = "У проверяемого ребёнка должен быть родитель с именем %s!";
    private final String CHECK_CHILDREN_NAME_MSG = "У проверяемого родителя должен быть ребёнок с именем %s!";
    private final String ONE_RELATIVE_FOUND_ERROR_MSG = "Должен быть найден только один подходящий родственник!";
    private final String FOUND_RELATIVE_NAME_NOT_VALID_MSG = "Имя найденного родственника не соответствует ожидаемому!";

    @Test
    public void testAddChild_CreatesNewChild() {
        var parent = new FamilyMember(JOHN);
        var originalChildrenCount = parent.getChildren().size();

        parent.addChild(ALICE);

        assertEquals(0, originalChildrenCount, "Количество детей до добавления должно быть равно 0!");
        assertEquals(1, parent.getChildren().size(), "Количество детей должно быть равно 1!");

        var existingChildNames = parent.getChildren().stream().map(FamilyMember::getName).collect(Collectors.toSet());
        assertTrue(existingChildNames.contains(ALICE), String.format(CHECK_CHILDREN_NAME_MSG, ALICE));
    }

    @Test
    public void testAddChild_ChildAlsoHasLinkToParent() {
        var parent = new FamilyMember(JOHN);

        parent.addChild(ALICE);

        var child = parent.getChildren().iterator().next();
        assertEquals(1, child.getParents().size(), "Количество родителей должно быть равно 1!");

        var existingParentNames = child.getParents().stream().map(FamilyMember::getName).collect(Collectors.toSet());
        assertTrue(existingParentNames.contains(JOHN), String.format(CHECK_PARENT_NAME_MSG, JOHN));
    }

    @Test
    public void testAddChild_MultipleChildren() {
        var parent = new FamilyMember(JOHN);

        parent.addChild(ALICE);
        parent.addChild(BOB);

        assertEquals(2, parent.getChildren().size());

        var existingChildNames = parent.getChildren().stream().map(FamilyMember::getName).collect(Collectors.toSet());
        assertTrue(existingChildNames.contains(ALICE), String.format(CHECK_CHILDREN_NAME_MSG, ALICE));
        assertTrue(existingChildNames.contains(BOB), String.format(CHECK_CHILDREN_NAME_MSG, BOB));
    }

    @Test
    public void testAddParent_CreatesNewParent() {
        var child = new FamilyMember(ALICE);
        var originalParentsCount = child.getParents().size();

        child.addParent(JOHN);

        assertEquals(0, originalParentsCount, "Количество родителей до добавления должно быть равно 0!");
        assertEquals(1, child.getParents().size(), "Количество родителей должно быть равно 1!");

        var existingParentNames = child.getParents().stream().map(FamilyMember::getName).collect(Collectors.toSet());
        assertTrue(existingParentNames.contains(JOHN), String.format(CHECK_PARENT_NAME_MSG, JOHN));
    }

    @Test
    public void testAddParent_ParentAlsoHasLinkToChild() {
        var child = new FamilyMember(ALICE);

        child.addParent(JOHN);

        var parent = child.getParents().iterator().next();
        assertEquals(1, parent.getChildren().size());

        var existingChildNames = parent.getChildren().stream().map(FamilyMember::getName).collect(Collectors.toSet());
        assertTrue(existingChildNames.contains(ALICE), String.format(CHECK_CHILDREN_NAME_MSG, ALICE));
    }

    @Test
    public void testAddParent_MultipleParents() {
        var child = new FamilyMember(ALICE);
        child.addParent(JOHN);
        child.addParent(MARY);

        assertEquals(2, child.getParents().size());
        var parentNames = child.getParents().stream().map(FamilyMember::getName).collect(Collectors.toSet());
        assertTrue(parentNames.contains(JOHN), String.format(CHECK_PARENT_NAME_MSG, JOHN));
        assertTrue(parentNames.contains(MARY), String.format(CHECK_PARENT_NAME_MSG, MARY));
    }

    @Test
    public void testFindRelatives_FindsDirectChild() {
        var john = new FamilyMember(JOHN);
        john.addChild(ALICE);

        var relatives = john.findRelatives(ALICE);
        assertEquals(1, relatives.size(), ONE_RELATIVE_FOUND_ERROR_MSG);
        assertEquals(ALICE, relatives.iterator().next().getName(), FOUND_RELATIVE_NAME_NOT_VALID_MSG);
    }

    @Test
    public void testFindRelatives_FindsGrandchild() {
        var john = new FamilyMember(JOHN);
        john.addChild(ALICE);

        var alice = john.getChildren().iterator().next();
        alice.addChild(CHARLIE);

        var relatives = john.findRelatives(CHARLIE);
        assertEquals(1, relatives.size(), ONE_RELATIVE_FOUND_ERROR_MSG);
        assertEquals(CHARLIE, relatives.iterator().next().getName(), FOUND_RELATIVE_NAME_NOT_VALID_MSG);
    }

    @Test
    public void testFindRelatives_FindsParent() {
        var alice = new FamilyMember(ALICE);
        alice.addParent(JOHN);

        var relatives = alice.findRelatives(JOHN);
        assertEquals(1, relatives.size(), ONE_RELATIVE_FOUND_ERROR_MSG);
        assertEquals(JOHN, relatives.iterator().next().getName(), FOUND_RELATIVE_NAME_NOT_VALID_MSG);
    }

    @Test
    public void testFindRelatives_FindsMultipleRelativesWithSameName() {
        var john = new FamilyMember(JOHN);
        john.addChild(ALICE);

        var alice = john.getChildren().iterator().next();
        alice.addChild(JOHN);

        var relatives = john.findRelatives(JOHN);
        // Сам John и его внук с таким же именем
        assertEquals(2, relatives.size(), "Должно было быть найдено два родственника с таким именем!");
    }

    @Test
    public void testFindRelatives_NoRelativesFound() {
        var john = new FamilyMember(JOHN);
        var relatives = john.findRelatives(UNKNOWN);
        assertTrue(relatives.isEmpty(), "Не должно было быть найдено никаких родственников с именем 'Unknown'!");
    }

    @SneakyThrows
    @Test
    public void testPrintTree_ParentAndTwoKids() {
        var john = new FamilyMember(JOHN);
        john.addChild(ALICE);
        john.addChild(BOB);

        var output = tapSystemOut(john::printTree);
        log.info(output);

        assertTrue(output.contains("Генеалогическое дерево для: " + JOHN));
        assertTrue(output.contains("    " + ALICE));
        assertTrue(output.contains("    " + BOB));
    }

    @SneakyThrows
    @Test
    public void testPrintTree_MultipleGenerations() {
        var john = new FamilyMember(JOHN);
        john.addChild(ALICE);

        var alice = john.getChildren().iterator().next();
        alice.addChild(CHARLIE);

        var output = tapSystemOut(john::printTree);
        log.info(output);

        assertTrue(output.contains(JOHN));
        assertTrue(output.contains("    " + ALICE));
        assertTrue(output.contains("        " + CHARLIE));
    }

    @SneakyThrows
    @Test
    public void testPrintTree_AlreadyPrinted() {
        var john = new FamilyMember(JOHN);
        var alice = new FamilyMember(ALICE);

        var childrenField = FamilyMember.class.getDeclaredField("children");

        childrenField.setAccessible(true);

        @SuppressWarnings("unchecked")
        var johnChildren = (Set<FamilyMember>) childrenField.get(john);
        @SuppressWarnings("unchecked")
        var aliceChildren = (Set<FamilyMember>) childrenField.get(alice);

        johnChildren.add(alice);
        aliceChildren.add(john);

        var output = tapSystemOut(john::printTree);
        log.info(output);

        assertTrue(output.contains("(уже выведен ранее)"));
    }

    @Test
    public void testComplexFamilyStructure() {
        FamilyMember alice = new FamilyMember(ALICE);

        alice.addParent(JOHN);
        alice.addParent(MARY);

        alice.addChild(BOB);
        alice.addChild(CHARLIE);

        var john = alice.getParents().stream()
                .filter(parent -> parent.getName().equals(JOHN)).findFirst()
                .orElse(new FamilyMember(UNKNOWN));

        assertEquals(2, alice.getParents().size());
        assertEquals(2, alice.getChildren().size());

        var relatives = john.findRelatives(ALICE);
        assertEquals(1, relatives.size());

        john.printTree();
    }
}
