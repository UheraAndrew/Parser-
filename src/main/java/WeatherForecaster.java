import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor on 04.10.2018.
 */
public class WeatherForecaster {
    private static final String KEY = "fb15a529342f413aabe53922180910";
    private static final String URL = "http://api.apixu.com/v1/forecast.json?";
    private int daysForForecast = 7;

    public String forecast(City city) {

        Coordinates coordinates = city.getCoordinates();

        Map<String, Object> map = new HashMap<>();
        map.put("key", KEY);
        map.put("q", coordinates.lat + " " + coordinates.lon);
        map.put("days", this.daysForForecast);

        JSONObject jsonObj;
        try {
            jsonObj = Unirest.get(URL).queryString(map).asJson().getBody().getObject();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
        JSONArray out_dict = jsonObj.getJSONObject("forecast").getJSONArray("forecastday");
        JSONArray reversed = new JSONArray();
        for (int i = out_dict.length() - 1; i > -1; i--) {
            reversed.put(out_dict.get(i));
        }
        return reversed.toString(5);
    }

}
