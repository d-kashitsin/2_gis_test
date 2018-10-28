package rest.model;

import java.util.HashMap;
import java.util.Map;

public class ReservDataView {
    public static Map<String, Object> toJson(final ReservData reservData) {
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", reservData.getId());
        result.put("place(s)", reservData.getPlaces());
        result.put("Room Name",reservData.getRoomName());
        result.put("Cinema Name",reservData.getCinemaTheatreName());
        result.put("movieName",reservData.getMovieName());
        return result;
    }
}
