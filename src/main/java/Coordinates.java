import lombok.ToString;
import org.jsoup.select.Elements;

import javax.xml.bind.Element;


//TODO Create class that represents coordinates
@ToString
public class Coordinates {
    double lat;
    double lon;

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }


}
