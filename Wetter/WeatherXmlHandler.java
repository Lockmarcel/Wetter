package Wetter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Collection;

public class WeatherXmlHandler extends DefaultHandler {


    private Collection<Forecast> forecasts;
    private String from;
    private String to;
    private String symbolName;
    private String value;
    private String unit;

    public WeatherXmlHandler(Collection<Forecast> forecasts) {
        this.forecasts = forecasts;


    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        switch (qName) {
            case "time":
              from = attributes.getValue("from");
              to = attributes.getValue("to");
                break;
            case "symbol":
                symbolName = attributes.getValue("name");

                break;
            case "temperature":
              value = attributes.getValue("value");
              unit = attributes.getValue("unit");
                break;
        }
    }

    public void endElement (String uri, String localName, String qName) throws SAXException{

        switch (qName){
            case "time":
             Forecast forecast = new Forecast(from, to,symbolName,value,unit);
             forecasts.add(forecast);


        }
    }


}
