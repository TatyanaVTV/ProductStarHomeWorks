package core.annotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Напишите класс который бы на основе рефлексии создавал бы новые экземпляры DZ, обращался бы к их методам
 * getSumInteger и getSumFromList
 * и выводил результаты в консоль
 */

public class DZ {

    private int i;
    private String s;
    private List<Double> list = new ArrayList<>();

    private DZ() {
    }

    public DZ(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public DZ(int i, String s, List<Double> list) {
        this.i = i;
        this.s = s;
        this.list = list;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public List<Double> getList() {
        return list;
    }

    public void setList(List<Double> list) {
        this.list = list;
    }

    private int getSumInteger(DZ dz1, DZ dz2) {
        return dz1.getI() + dz2.getI();
    }

    private List<Double> getSumFromList(DZ dz1, DZ dz2) {
        return Stream.concat(dz1.getList().stream(), dz2.getList().stream()).collect(Collectors.toList());
    }

}
