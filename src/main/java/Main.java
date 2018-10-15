import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 03.10.2018.
 */
public class Main {
    static Map<String, City> city;

    @SneakyThrows
    public static void main(String[] args) throws IOException {

        System.out.println("Зачекайте трішки. Ми завантажуєм дані\n...");
        fillCities();

        startCommunication();

    }

    public static void startCommunication() throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        WeatherForecaster wf = new WeatherForecaster();

        String name;
        String question = "For which city/village you want to know weather forecast?";
        System.out.println(question);
        while (!(name = buf.readLine().trim().toLowerCase()).equals("")) {

            if (!city.containsKey(name)) {
                System.out.println("We didn't find your city. Check the input.\n" + question);
                continue;
            }
            City c = city.get(name);
            if (c.getCoordinates() != null || c.findCoordinates()) {
                System.out.println(wf.forecast(c));
                System.out.println("\n" + question);
            } else {
                System.out.println("sorry we didn`t support this city\n");
            }
        }
        System.out.println("Good bye");

    }

    private static void fillCities() throws IOException {
        String url = "https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B8_(%D0%B7%D0%B0_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D1%96%D1%82%D0%BE%D0%BC)";
        Document doc = Jsoup.connect(url).get();
        Elements cities = doc.select("table tr");

        city = new HashMap<>(); // You can use List`s or other java Collections
        for (Element cityData : cities) {
            City myCity = City.parse(cityData);
            if (myCity != null) {
                city.put(myCity.getName().trim().toLowerCase(), myCity);
            }
        }
    }
}
