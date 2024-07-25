package core.collections.practice;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentSurnameStorage {
    private TreeMap<String, Set<Long>> surnamesTreeMap = new TreeMap<>();

    public void studentCreated(Long id, String surname) {
        Set<Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }

    public void studentDeleted(Long id, String surname) {
        surnamesTreeMap.get(surname).remove(id);
    }

    public void studentUpdated(Long id, String oldSurname, String newSurname) {
        studentDeleted(id, oldSurname);
        studentCreated(id, newSurname);
    }

    /**
     * Данный метод возвращает уникальные идентификаторы студентов, чьи фамилии меньше или равны переданной
     * @return set
     */
    public Set<Long> getStudentBySurnamesLessOrEqualThan(String surname) {
        String[] parsedData = surname.split(",");
        String surnameFrom;
        String surnameTo;

        switch (parsedData.length) {
            case 1 -> {
                if (parsedData[0].isEmpty()) {
                    surnameFrom = surnamesTreeMap.firstKey();
                    surnameTo = surnamesTreeMap.lastKey();
                } else {
                    surnameFrom = parsedData[0];
                    surnameTo = parsedData[0];
                }
            }
            case 2 -> {
                surnameFrom = parsedData[0];
                surnameTo = parsedData[1];
            }
            default -> {
                throw new IllegalArgumentException("Too many arguments");
            }
        }

        return surnamesTreeMap.subMap(surnameFrom, true, surnameTo, true)
                .values()
                .stream()
                .flatMap(longs -> longs.stream()) // "распаковывает" коллекцию
                .collect(Collectors.toSet());
    }
}
