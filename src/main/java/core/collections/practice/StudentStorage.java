package core.collections.practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentStorage {
    private Map<Long, Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();
    private Long currentId = 0L;

    /**
     * Создание данных о студенте
     * @param student данные студента
     * @return сгенерированный уникальный идентификатор студента
     * */
    public Long createStudent(Student student) {
        Long nextId = getNextId();
        studentStorageMap.put(nextId, student);
        studentSurnameStorage.studentCreated(nextId, student.getSurname());
        return nextId;
    }

    /**
     * Обновление данных о студенте
     * @param id идентификатор студента
     * @param student данные студента
     * @return true, если данные были обновлены, false, если студент не был найден
     * */
    public boolean updateStudent(Long id, Student student) {
        if (!studentStorageMap.containsKey(id)) {
            return false;
        } else {
            String newSurname = student.getSurname();
            String oldSurname = studentStorageMap.get(id).getSurname();
            studentStorageMap.put(id, student);
            studentSurnameStorage.studentUpdated(id, oldSurname, newSurname);
            return true;
        }
    }

    /**
     * Удаляет данные о студенте
     * @param id идентификатор студента
     * @return true, если студент был удален, false, если студент не был найден по идентификатору
     * */
    public boolean deleteStudent(Long id) {
        Student removed = studentStorageMap.remove(id);
        if (removed != null) {
            studentSurnameStorage.studentDeleted(id, removed.getSurname());
        }
        return removed != null;
    }

    public void search(String surnames) {
        Set<Long> students = studentSurnameStorage.getStudentBySurnamesLessOrEqualThan(surnames);
        for (Long studentId : students) {
            Student student = studentStorageMap.get(studentId);
            System.out.println("[" + studentId + "] " + student);
        }
    }

    public Long getNextId() {
        currentId = currentId + 1;
        return currentId;
    }

    public void printAll() {
        System.out.println(studentStorageMap);
    }

    public void printMap(Map<String, Long> data) {
        data.entrySet().stream().forEach(e -> {
            System.out.printf("%s - %d\n", e.getKey(), e.getValue());
        });
    }

    public Map<String, Long> getCountByCourse() {
        return studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCourse(), // ключ
                        student -> 1L, // значение
                        (count1, count2) -> count1 + count2 // ф-ция определяющая решение коллизий
                ));
    }

    public Map<String, Long> getCountByCity() {
        return studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCity(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
    }
}
