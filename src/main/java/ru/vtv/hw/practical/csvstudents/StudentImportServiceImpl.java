package ru.vtv.hw.practical.csvstudents;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.sql.DriverManager.getConnection;
import static ru.vtv.hw.practical.csvstudents.DataImportException.*;

@Slf4j
public class StudentImportServiceImpl implements StudentImportService {
    private static final String DB_URL =
            "jdbc:h2:file:./students_db;FILE_LOCK=NO;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;USER=test;PASSWORD=test";
    private static final String CSV_FILE_PATH = "StudentsPerformance.csv";

    public void importDataFromCsv() throws DataImportException {
        createTableIfNotExists();

        try (var parser = CSVParser.parse(
                new FileReader(CSV_FILE_PATH, UTF_8),
                CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setIgnoreEmptyLines(true)
                        .get()
        )) {
            processWithTransaction(parser);
            log.info("Successfully imported students from csv file: {}", CSV_FILE_PATH);
        } catch (Exception e) {
            throw csvReadException(e);
        }
    }

    @SneakyThrows
    private void processWithTransaction(CSVParser parser) {
        try (var conn = getConnection(DB_URL)) {
            try {
                conn.setAutoCommit(false);

                for (var record : parser) {
                    insertStudentFromRecord(conn, record);
                }

                conn.commit();
                verifyImport(conn);
            } catch (SQLException e) {
                try {
                    conn.rollback();
                    log.warn("Transaction rolled back due to error");
                } catch (SQLException rollbackEx) {
                    log.error("Failed to rollback transaction", rollbackEx);
                    throw rollBackTransactionException(rollbackEx);
                }
                throw databaseException(e);
            }
        } catch (Exception e) {
            throw connectionException(e);
        }
    }

    private void createTableIfNotExists() throws DataImportException {
        var sql = """
                CREATE TABLE IF NOT EXISTS students (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    gender VARCHAR(20),
                    race_ethnicity VARCHAR(50),
                    parental_level_of_education VARCHAR(100),
                    lunch VARCHAR(20),
                    test_preparation_course VARCHAR(30),
                    math_score INT,
                    reading_score INT,
                    writing_score INT
                );
                """;

        try (var conn = getConnection(DB_URL);
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw createTableException(e);
        }
    }

    private void insertStudentFromRecord(Connection conn, CSVRecord record) throws DataImportException {
        var sql = "INSERT INTO students (gender, race_ethnicity, parental_level_of_education, lunch, test_preparation_course, math_score, reading_score, writing_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.get("gender"));
            pstmt.setString(2, record.get("race/ethnicity"));
            pstmt.setString(3, record.get("parental level of education"));
            pstmt.setString(4, record.get("lunch"));
            pstmt.setString(5, record.get("test preparation course"));
            pstmt.setInt(6, Integer.parseInt(record.get("math score")));
            pstmt.setInt(7, Integer.parseInt(record.get("reading score")));
            pstmt.setInt(8, Integer.parseInt(record.get("writing score")));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw storeDataException(e);
        }
    }

    private void verifyImport(Connection conn) throws DataImportException {
        var countSql = "SELECT COUNT(*) FROM students";
        try (var stmt = conn.createStatement();
             var rs = stmt.executeQuery(countSql)) {
            if (rs.next()) {
                int count = rs.getInt(1);
                log.info("Imported records count: {}", count);
            }
        } catch (SQLException e) {
            throw selectDataException(e);
        }
    }
}
