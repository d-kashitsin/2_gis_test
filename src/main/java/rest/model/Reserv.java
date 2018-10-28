package rest.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Reserv {
    private String tableName = "reserv";
    private Room room;

    public Reserv(Connection connection,Room room) {
        this.connection = connection;
        this.room = room;
    }

    private final Connection connection;


    public int massReserving(int reservedRoom, int userID, List<Integer> places){
        int placeNumber;
        int rID=0,reservId=0;
        for(placeNumber=0;placeNumber<places.size();placeNumber++){
            if(!isAvailable(reservedRoom,places.get(placeNumber))){
                //System.out.println("selected reserved places");
                reservId = -1;
                return reservId;
            }
        }
        try {

                String sql = "INSERT INTO " + tableName +
                        "(room_id,userid,place_number) VALUES ('" +
                        reservedRoom + "','" +
                        userID + "','" +
                        places.get(0) + "')";

                Statement statement = connection.createStatement();
                statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                reservId = rID = rs.getInt("r_id");
                String sqlChangeReservID = "UPDATE " + tableName + " SET reserv_id=" + rID + " WHERE r_id=" + rID;
                statement.executeUpdate(sqlChangeReservID);
                //System.out.println(cinemaID);
                for (placeNumber = 1; placeNumber < places.size(); placeNumber++) {
                    sql = "INSERT INTO " + tableName +
                            "(reserv_id,room_id,userid,place_number) VALUES ('" +
                            rID + "','" +
                            reservedRoom + "','" +
                            userID + "','" +
                            places.get(placeNumber) + "')";
                    statement.executeUpdate(sql);
                }
                rs.close();
        }
        catch (Exception e) {
                e.printStackTrace();
            }
        //System.out.println(rID);
        return reservId;
    }
    public void deleteReserv(int reserv_id){
        String sql = "DELETE FROM " + tableName + " WHERE reserv_id =" + reserv_id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isAvailable(int reservedRoom, int placeNumber){
        boolean available = false;
        String sql = "SELECT place_number FROM " + tableName +
                " WHERE place_number =" + placeNumber +
                " AND room_id =" + reservedRoom;
        ResultSet rs = null;
        try {

            Statement statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            if(rs.next())
                available=false;
            else
                if(placeNumber>room.getPlacesAmount(reservedRoom)||placeNumber<=0)
                    available=false;
                else
                    available=true;
                
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return available;
    }
    public void printReserv(){
        try{

            String sql = "SELECT * FROM " + tableName;
            Statement statement = connection.createStatement();
            ResultSet rs  = statement.executeQuery(sql);
            while (rs.next()) {

                System.out.println(rs.getInt(1)+
                        " | "+rs.getInt(2)+
                        " | "+rs.getInt(3)+
                        " | "+rs.getInt(4)+
                        " | "+rs.getInt(5));
            }
        }
        catch(Exception e) {e.printStackTrace();}
    }



}