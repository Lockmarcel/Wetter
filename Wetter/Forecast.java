package Wetter;

public class Forecast {


    private String from;
    private String to;
    private String symbolName;
    private String value;
    private String unit;

    public Forecast(String from, String to, String symbolName, String value, String unit) {
        this.from = from;
        this.to = to;
        this.symbolName = symbolName;
        this.value = value;
        this.unit = unit;
    }


    @Override
    public String toString() {
        return from + " ... " + to + "\n" + value + " " + unit + " (" + symbolName + " )\n";
    }

    public double getTemperature() {

        return Double.valueOf(value);
    }

    public String getDay() {

        return from.substring(0, 10);
    }
}
