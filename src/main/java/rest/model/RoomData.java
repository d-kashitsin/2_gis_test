package rest.model;


import java.util.Map;

public class RoomData {
    private final int roomID;
    private final String roomName;
    private final String CinemaName;
    public final Map<Integer, String> seats;

    public RoomData(int roomID, String roomName, String cinemaName, Map<Integer, String> seats) {
        this.roomID = roomID;
        this.roomName = roomName;
        CinemaName = cinemaName;
        this.seats = seats;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getCinemaName() {
        return CinemaName;
    }

    public Map<Integer, String> getSeats() {
        return seats;
    }

    @Override
    public String toString() {

        return "Room Name: " + roomName +
                "Cinema Name: " + CinemaName +
                "Seats: " + seats;
    }


}
