package Wetter;

import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.currentTimeMillis;

public class WeatherTool {

    private WeatherService _service;
    private Scanner _scanner;

    public WeatherTool(WeatherService service) {

        _service = service;
        _scanner = new Scanner(System.in);


    }

    private String printSimpleDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd");
        long currentTime = currentTimeMillis()+ 1000*60*60*24;

        return formatter.format(currentTime);


    }

    // Repl Read evaluate print loop

    public void startRepl() {

        System.out.println("@morgen für morgen und @quit zum beenden oder der Stadtname");
        System.out.print(">  ");
        String line = _scanner.nextLine();

        while (!line.equals("@quit")) {

            if (line.equals("@morgen")) {


                double temp = _service.averageTemperature(printSimpleDateFormat(), "Hamburg");
                int blub =((int) temp);

                System.out.println("In Hamburg ist es morgen dem "+ printSimpleDateFormat()+ " genau "+ blub + " Grad in Hamburg");
            } else {
                List<Forecast> forecasts = _service.getWeatherForecastFor(line);

                System.out.println(forecasts.get(0));

            }
            System.out.print(">  ");
            line = _scanner.nextLine();




        }


    }

    }