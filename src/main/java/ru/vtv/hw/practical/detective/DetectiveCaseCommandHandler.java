package ru.vtv.hw.practical.detective;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class DetectiveCaseCommandHandler {
    private static final DetectiveCase DETECTIVE_CASE = new DetectiveCase();

    public void processCommand(Command command) throws EvidenceException {
        var action = command.getAction();
        try {
            switch (action) {
                case ADD_EVIDENCE -> processAddEvidenceCommand(command);
                case CHECK_EVIDENCE -> processCheckEvidenceCommand(command);
                case REMOVE_EVIDENCE -> processRemoveEvidenceCommand(command);
                case COMPARE_EVIDENCE -> processCompareEvidenceCommand(command);
                case SHOW_EVIDENCES -> processShowEvidencesCommand();
                default -> System.out.printf("Действие %s не поддерживается.\n", action);
            }
        } finally {
            log.debug("Обработка команды. Действие: {}, данные: {}", command.getAction().name(), command.getData());
        }
    }

    private void processAddEvidenceCommand(Command command) {
        var evidenceName = command.getData();
        DETECTIVE_CASE.addEvidence(evidenceName);
        System.out.printf("Улика \"%s\" добавлена.%n", evidenceName);
    }

    private void processCheckEvidenceCommand(Command command) {
        var evidenceName = command.getData();
        var isFound = DETECTIVE_CASE.checkEvidence(evidenceName);
        System.out.printf("Улика \"%s\"%sнайдена.%n", evidenceName, isFound ? " " : " не ");
    }

    private void processRemoveEvidenceCommand(Command command) {
        var evidenceName = command.getData();
        DETECTIVE_CASE.removeEvidence(evidenceName);
        System.out.printf("Улика \"%s\" удалена.%n", evidenceName);
    }

    private void processCompareEvidenceCommand(Command command) {
        var evidencesArray = command.getData().split(",");
        System.out.printf("Улики для поиска - %d шт.%n", evidencesArray.length);

        var foundEvidences = DETECTIVE_CASE.checkEvidences(Set.of(evidencesArray));
        System.out.println("Совпадения с базой данных: ");
        foundEvidences.forEach(foundEvidence -> System.out.printf("- %s%n", foundEvidence));

        if (foundEvidences.isEmpty()) System.out.println("- Отсутствуют");
    }

    private void processShowEvidencesCommand() {
        System.out.println("Найденные улики: ");
        var foundEvidences = DETECTIVE_CASE.getEvidences();

        foundEvidences.forEach(evidence -> System.out.println("- " + evidence));

        if (foundEvidences.isEmpty()) System.out.println("- На данный момент никаких улик не найдено");
    }
}
