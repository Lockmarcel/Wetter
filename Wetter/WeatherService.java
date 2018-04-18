package Wetter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherService {

    private Map<String, List<Forecast>> forecasts = new HashMap<>();


    public List<Forecast> getWeatherForecastFor(String city) {

        if (forecasts.containsKey(city)) {

            System.out.println("Lokale Daten");
            return forecasts.get(city);
        } else {

            System.out.println("Daten vom Server");
            return talkToOpenWeatherMap(city);
        }
    }

    private List<Forecast> talkToOpenWeatherMap(String city) {
        try {

            String xml = getXmlFromOpenWeatherMap(city);
            parseXml(xml, city);
            return forecasts.get(city);

        } catch (IOException | SAXException ex) {

            ex.printStackTrace();
            return null;
        }
    }


    private String getXmlFromOpenWeatherMap(String city) throws IOException

    {
        String config = "e9244509f9da7fe16ad3579261c91b9b";
        String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&lang=de&mode=xml&APPID=" + config;
        try {

            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            try (InputStream in = connection.getInputStream()) {
                return exhaustInputStream(in);

            }
        } catch (MalformedURLException unrecoverable) {
            throw new AssertionError(unrecoverable);
        }

    }


    private String exhaustInputStream(InputStream in) throws IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            sb.append(inputLine);
            sb.append('\n');
        }
        return sb.toString();


    }

    private void parseXml(String xml, String city) throws IOException, SAXException {

        try {
            InputSource xmlInput = new InputSource(new StringReader(xml));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            ArrayList<Forecast> list = new ArrayList<>();
            forecasts.put(city, list);
            WeatherXmlHandler handler = new WeatherXmlHandler(list);
            saxParser.parse(xmlInput, handler);
        } catch (ParserConfigurationException unrecoverable) {
            throw new AssertionError(unrecoverable);

        }

    }

    public double averageTemperature(String day, String city) {

        List<Forecast> forecasts = getWeatherForecastFor(city);
        int n= 0;
        double sum=0;

        for (Forecast forecast : forecasts) {


            if (forecast.getDay().equals(day)) {
                double temp = forecast.getTemperature();
                sum += temp;
                ++n;


            }

        }
        return sum/n;

    }
}
