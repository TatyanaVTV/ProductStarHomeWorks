package ru.vtv.hw.practical.csvstudents;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DataBaseCredentials {
    public static final String DB_URL =
            "jdbc:h2:file:./students_db;FILE_LOCK=NO;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;USER=test;PASSWORD=test";
}
