package rest.model;

import java.util.HashMap;
import java.util.Map;

public class RoomDataView {
    public static Map<String, Object> toJson(final RoomData roomData) {
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", roomData.getRoomID());
        result.put("Room Name", roomData.getRoomName());
        result.put("Cinema Name", roomData.getCinemaName());
        result.put("Seats", roomData.getSeats());
        return result;
    }
}
