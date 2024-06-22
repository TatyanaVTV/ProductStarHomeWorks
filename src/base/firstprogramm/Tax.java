package base.firstprogramm;

public class Tax {

    private static Price[] prices;
    public static int i = 0;

    public static void main(String[] args) {
        prices =  new Price[args.length];

        for (String value : args) {
            verifyAndAddPrice(value);
        }

        printPricesWithTaxes();
    }

    private static void verifyAndAddPrice(String value) {
        if (value.matches("[0-9]+\\.?[0-9]+")) {
            prices[i++] = new Price(Double.parseDouble(value));
        }
    }

    private static void printPricesWithTaxes() {
        for (Price price : prices) {
            if (price == null) continue;
            if (price.getTaxPercent() > 0) {
                System.out.println(price);
            }
        }
    }
}

class Price {
    private double value;

    public Price(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double getTaxPercent() {
        if (value <= 100) {
            return 10;
        } else if (value <= 1000) {
            return 20;
        } else if (value <= 10000) {
            return 30;
        } else return 0.;
    }

    @Override
    public String toString() {
        return "Price{value=" + getValue() + ", tax=" + getTaxPercent() + "%}";
    }
}

