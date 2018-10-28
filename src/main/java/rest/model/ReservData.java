package rest.model;

import java.util.*;

public class ReservData {
    private final int id;
    private final List<Integer> places;
    private final String roomName;
    private final String cinemaTheatreName;
    private final String movieName;



    public int getId() {
        return id;
    }

    public List<Integer> getPlaces() {
        return places;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getCinemaTheatreName() {
        return cinemaTheatreName;
    }

    public String getMovieName() {
        return movieName;
    }


    public ReservData(int id, List<Integer> places, String roomName, String cinemaTheatreName, String movieName) {
        this.id = id;
        this.places = places;
        this.roomName = roomName;
        this.cinemaTheatreName = cinemaTheatreName;
        this.movieName = movieName;
    }

    @Override
    public String toString(){
        return "Reserv " + id +
                "Hall: " + roomName +
                "Cinema: " + cinemaTheatreName +
                "Movie: " + movieName +
                "Place(s): " + places;
    }

}
