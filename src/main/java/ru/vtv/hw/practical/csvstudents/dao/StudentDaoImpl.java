package ru.vtv.hw.practical.csvstudents.dao;

import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.csvstudents.dao.domain.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.vtv.hw.practical.csvstudents.DataBaseCredentials.DB_URL;

@Slf4j
public class StudentDaoImpl implements StudentDao {

    @Override
    public List<Student> findAll() {
        var sql = "SELECT * FROM students";
        return executeQuery(sql, null);
    }

    @Override
    public List<Student> findByTestPreparationCourse(TestPreparationCourse courseStatus) {
        var sql = "SELECT * FROM students WHERE test_preparation_course = ?";
        return executeQuery(sql, ps -> ps.setString(1, courseStatus.name().toLowerCase()));
    }

    @Override
    public List<Student> findByMathScoreGreaterThan(int minScore) {
        var sql = "SELECT * FROM students WHERE math_score > ?";
        return executeQuery(sql, ps -> ps.setInt(1, minScore));
    }

    @Override
    public List<Student> findByGenderAndRace(Gender gender, Ethnicity raceEthnicity) {
        var sql = "SELECT * FROM students WHERE gender = ? AND race_ethnicity = ?";
        return executeQuery(sql, ps -> {
            ps.setString(1, gender.name().toLowerCase());
            ps.setString(2, raceEthnicity.getValue());
        });
    }

    private List<Student> executeQuery(String sql, ParameterSetter parameterSetter) {
        var students = new ArrayList<Student>();

        try (var conn = DriverManager.getConnection(DB_URL);
             var ps = conn.prepareStatement(sql)) {

            if (parameterSetter != null) {
                parameterSetter.setParameters(ps);
            }

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRowToStudent(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Error executing query: {}", sql, e);
            throw new RuntimeException("Database error", e);
        }

        return students;
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        var recGender = rs.getString("gender");
        var recRaceEthnicity = rs.getString("race_ethnicity");
        var recEducationLevel = rs.getString("parental_level_of_education");
        var recLunch = rs.getString("lunch");
        var recPreparationCourse = rs.getString("test_preparation_course");

        return Student.builder()
                .id(rs.getInt("id"))
                .gender(findEnumByValue(Gender.class, recGender))
                .raceEthnicity(findEnumByValue(Ethnicity.class, recRaceEthnicity))
                .parentalLevelOfEducation(findEnumByValue(EducationLevel.class, recEducationLevel))
                .lunch(findEnumByValue(Lunch.class, recLunch))
                .testPreparationCourse(findEnumByValue(TestPreparationCourse.class, recPreparationCourse))
                .mathScore(rs.getInt("math_score"))
                .readingScore(rs.getInt("reading_score"))
                .writingScore(rs.getInt("writing_score"))
                .build();
    }

    private <T extends Enum<T>> T findEnumByValue(Class<T> enumClass, String valueFromDb) {
        if (valueFromDb == null) return null;

        for (T constant : enumClass.getEnumConstants()) {
            try {
                var getValueMethod = enumClass.getMethod("getValue");
                var enumValue = (String) getValueMethod.invoke(constant);
                if (enumValue.equalsIgnoreCase(valueFromDb)) {
                    return constant;
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                if (constant.name().equals(valueFromDb.toUpperCase())) {
                    return constant;
                }
            }
        }

        throw new RuntimeException("No enum constant " + valueFromDb.toUpperCase() + " for " + enumClass.getName());
    }

    @FunctionalInterface
    private interface ParameterSetter {
        void setParameters(PreparedStatement ps) throws SQLException;
    }
}
