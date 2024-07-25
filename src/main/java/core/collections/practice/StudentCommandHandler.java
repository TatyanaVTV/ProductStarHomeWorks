package core.collections.practice;

import java.util.Map;

public class StudentCommandHandler {
    private StudentStorage studentStorage = new StudentStorage();
    public void processCommand(Command command) throws WrongDataFormatException {
        Action action = command.getAction();

        try {
            switch (action) {
                case CREATE -> processCreateCommand(command);
                case UPDATE -> processUpdateCommand(command);
                case DELETE -> processDeleteCommand(command);
                case STATS_BY_COURSE -> processStatsByCourseCommand(command);
                case STATS_BY_CITY -> processStatsByCityCommand(command);
                case SEARCH -> processSearchCommand(command);
                default -> System.out.printf("Действие %s не поддерживается.\n", action);
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new WrongDataFormatException(command, getDataFormatForAction(action), e);
        } finally {
            System.out.println("Обработка команды. Действие: " + command.getAction().name()
                    + ", данные: " + command.getData());
        }
    }

    public String getDataFormatForAction(Action action) {
        return switch (action) {
            case CREATE -> "surname,name,course,city,age";
            case UPDATE -> "id,surname,name,course,city,age";
            case DELETE -> "id";
            case SEARCH -> "surname / surname,surname / <пусто>";
            default -> "<пусто>";
        };
    }

    private void processCreateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");

        Student student = new Student();
        student.setSurname(dataArray[0]);
        student.setName(dataArray[1]);
        student.setCourse(dataArray[2]);
        student.setCity(dataArray[3]);
        student.setAge(Integer.valueOf(dataArray[4]));

        studentStorage.createStudent(student);
        studentStorage.printAll();
    }

    private void processUpdateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");

        Long id = Long.valueOf(dataArray[0]);

        Student student = new Student();
        student.setSurname(dataArray[1]);
        student.setName(dataArray[2]);
        student.setCourse(dataArray[3]);
        student.setCity(dataArray[4]);
        student.setAge(Integer.valueOf(dataArray[5]));

        studentStorage.updateStudent(id, student);
        studentStorage.printAll();
    }

    private void processDeleteCommand(Command command) {
        String data = command.getData();
        Long id = Long.valueOf(data);
        studentStorage.deleteStudent(id);
        studentStorage.printAll();
    }

    private void processStatsByCourseCommand(Command command) {
        Map<String, Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);
    }

    private void processSearchCommand(Command command) {
        String surnames = command.getData();
        studentStorage.search(surnames);
    }

    private void processStatsByCityCommand(Command command) {
        Map<String, Long> data = studentStorage.getCountByCity();
        studentStorage.printMap(data);
    }
}
