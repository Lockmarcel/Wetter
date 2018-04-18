package Wetter;
public class Startup {

    public static void main(String[] args) {


        WeatherService weatherService = new WeatherService();
        WeatherTool weatherTool = new WeatherTool(weatherService);
        weatherTool.startRepl();

    }
}
