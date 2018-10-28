package rest.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Room {

    private String tablename = "rooms";


    private final Connection connection;
    public Room(Connection connection) {
        this.connection = connection;
    }


    public int addRoom(int cinemaID, String roomName, int placesAmount) {
        int roomID=0;
        String sql = "INSERT INTO " + tablename + "(cinema_name,cinema_adress) VALUES ('" + cinemaID + "','" + roomName + "','" + placesAmount + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            roomID = rs.getInt("cinema_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomID;
    }

    public void deleteRoom(int roomID) {
        String sql = "DELETE FROM " + tablename + " WHERE room_name =" + roomID;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editRoom(int roomID, int cinemaID, String roomName, int placesAmount) {
        String sql = "UPDATE " + tablename +
                " SET cinemaID='" + cinemaID +
                "', roomName='" + roomName +
                "', places_amount='" + placesAmount +
                "' WHERE room_id=" + roomID;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printRoom() {
        try {
            String sql = "SELECT * FROM " + tablename;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {

                System.out.println(rs.getInt(1) +
                        " | " + rs.getString(2) +
                        " | " + rs.getString(3) +
                        " | " + rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPlacesAmount(int roomID){
        int placesAmount=0;
        try {
            String sql = "SELECT places_amount FROM " + tablename + " WHERE room_id = " + roomID;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

                rs.next();
                placesAmount = rs.getInt(1);

        } catch (Exception e) {
            //e.printStackTrace();
            placesAmount=-1;
        }
        return placesAmount;
    }


}


