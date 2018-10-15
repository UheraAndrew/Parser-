/**
 * Created by Victor on 03.10.2018.
 */

import jdk.nashorn.internal.objects.annotations.Constructor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Executors;


@Getter
@Setter
@ToString
public class City {
    private String name;//fp
    private String url;//fp
    private String administrativeArea;//fp
    private int numberOfCitizens;//fp
    private String yearOfFound;//fp
    private Coordinates coordinates; // Set this
    private double area;//fp

    private static final int INFO_SIZE = 6;


    public static City parse(Element city) {
        Elements info = city.select("td");
        if (info.size() == INFO_SIZE) {
            City myCity = new City();
            Element anchor = info.get(1).select("a").get(0);

            myCity.setName(anchor.attr("title"));
            myCity.setUrl(String.format("https://uk.wikipedia.org%s", anchor.attr("href")));

            anchor = info.get(2).select("a").get(0);

            myCity.setAdministrativeArea(anchor.attr("title"));

            anchor = info.get(3);


            StringBuffer buf = new StringBuffer(anchor.text());
            for (int i = 0; i < buf.length(); i++) {
                if (buf.charAt(i) == ' ' || buf.charAt(i) == ' ') {
                    buf.delete(i, i + 1);
                    i--;
                }
            }
            try {
                myCity.setNumberOfCitizens(Integer.parseInt(buf.toString()));
            } catch (NumberFormatException e) {
                myCity.setNumberOfCitizens(Integer.parseInt(buf.toString().split("!")[0]));
            }

            anchor = info.get(4).select("a").get(0);
            myCity.setYearOfFound(anchor.attr("title"));

            anchor = info.get(5);
            myCity.setArea(Double.parseDouble(anchor.text()));

            return myCity;
        }
        return null;
    }

    public Boolean findCoordinates() throws IOException {
        Document doc = Jsoup.connect(this.url).get();
        Elements coordinats = doc.select(".geo");
        if (coordinats.size() == 0) {
            return Boolean.FALSE;
        }
        String[] coordinates = coordinats.get(0).text().split(";");
        Coordinates temp = new Coordinates(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1].trim()));
        this.setCoordinates(temp);
        return Boolean.TRUE;
    }
}