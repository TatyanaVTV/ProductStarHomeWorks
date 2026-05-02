package ru.vtv.hw.practical.csvstudents;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImportDataCheck {

    public static void main(String[] args) {
        var service = new StudentImportServiceImpl();
        try {
            service.importDataFromCsv();
        } catch (DataImportException e) {
            log.error("Ошибка импорта: {}", e.getMessage());
        }
    }
}
