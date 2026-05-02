package ru.vtv.hw.practical.csvstudents.importdata;

import java.sql.SQLException;

public class DataImportException extends SQLException {
    private DataImportException(String message) {
        super(message);
    }

    public static  DataImportException connectionException(Throwable cause) {
        return new DataImportException("Ошибка при установке соединения с БД: " + cause.getMessage());
    }

    public static  DataImportException rollBackTransactionException(Throwable cause) {
        return new DataImportException("Ошибка при откате транзакции: " + cause.getMessage());
    }

    public static  DataImportException databaseException(Throwable cause) {
        return new DataImportException("Ошибка при работе с БД: " + cause.getMessage());
    }

    public static  DataImportException csvReadException(Throwable cause) {
        return new DataImportException("Ошибка при чтении CSV‑файла: " + cause.getMessage());
    }

    public static  DataImportException createTableException(Throwable cause) {
        return new DataImportException("Ошибка при создании таблицы: " + cause.getMessage());
    }

    public static  DataImportException storeDataException(Throwable cause) {
        return new DataImportException("Ошибка при импорте данных в БД: " + cause.getMessage());
    }

    public static  DataImportException selectDataException(Throwable cause) {
        return new DataImportException("Ошибка при получении данных из БД: " + cause.getMessage());
    }
}
