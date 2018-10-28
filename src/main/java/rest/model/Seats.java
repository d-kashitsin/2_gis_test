package rest.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Seats {

    private final Connection connection;

    public Seats(Connection connection) {
        this.connection = connection;
    }

    //Передаем номер интересующего кинозала, инстансы для вызова соответствующих методов.
    public Map<Integer, String> getAvailable(String roomNumber, Room room, Reserv reserv){
        //Для упрощения отображения список хранится в строковом формате,
        //если отображение данных будет реализовано иным методом целесообразнее хранить Boolean
        Map<Integer,String> seats = new HashMap<>();
        int roomNum;
        try{
            roomNum = Integer.parseInt(roomNumber);
            }
            catch (NumberFormatException e) {
                //e.printStackTrace();
                seats.put(1,"Room does not exist");
                return seats;
            }

        if(room.getPlacesAmount(roomNum)!=-1)  {
            for(int i=1;i<=room.getPlacesAmount(roomNum); i++){

                if(reserv.isAvailable(roomNum,i))
                    seats.put(i,"Free");
                else
                    seats.put(i,"Reserved");
            }
        }
        else
            seats.put(1,"Room does not exist");

        return seats;
    }

    public Map<Integer, String> getAvailable(int roomNumber, Room room, Reserv reserv){
        return getAvailable(Integer.toString(roomNumber),room,reserv);
    }

    public ReservData getReservData(int rID){
        List<Integer> places = new ArrayList();
        int id=0;
        String roomName="";
        String cinemaTheatreName="";
        ReservData reservData;
       try{
            String sql = "SELECT reserv.reserv_id, reserv.place_number, rooms.room_name, cinema.cinema_name " +
                    " FROM  reserv" +
                    " LEFT JOIN rooms ON reserv.room_id = rooms.room_id" +
                    " LEFT JOIN cinema ON rooms.cinema_id = cinema.cinema_id" +
                    " WHERE reserv.reserv_id = " + rID;
            Statement statement = connection.createStatement();
            ResultSet rs  = statement.executeQuery(sql);
            if(rs.next())
            {id = rs.getInt(1);
            places.add(rs.getInt(2));
            roomName = rs.getString(3);
            cinemaTheatreName = rs.getString(4);
            while (rs.next()){
               places.add(rs.getInt(2));}

            }
            else{id=-1;places.add(-1);roomName="-1";cinemaTheatreName="-1";

           }
        }
        catch(Exception e) {e.printStackTrace();}
        reservData = new ReservData(id, places, roomName, cinemaTheatreName, "movieName");
        return reservData;
    }
}
