package ru.vtv.hw.practical.niofile;

import lombok.AllArgsConstructor;
import lombok.Data;
import static java.lang.String.format;

@Data
@AllArgsConstructor
public class User {
    private String name;
    private String city;

    @Override
    public String toString() {
        return format("Имя: %s, Город: %s", name, city);
    }
}
