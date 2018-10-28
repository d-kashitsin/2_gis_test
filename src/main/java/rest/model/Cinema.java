package rest.model;

import java.sql.*;

public class Cinema {
    private String tablename = "cinema";

    private final Connection connection;
    public Cinema(Connection connection) {
        this.connection = connection;
    }


    public int addCinema(String cinemaName, String cinemaAdress){
        int cinemaID=0;
        try {

            String sql = "INSERT INTO " + tablename + "(cinema_name,cinema_adress) VALUES ('" + cinemaName +"','" + cinemaAdress+"')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            cinemaID = rs.getInt("cinema_id");
            }
        catch(Exception e) {e.printStackTrace();}
        return cinemaID;
    }
    public void deleteCinema(int cinemaID){
        try{
            String sql = "DELETE FROM " + tablename + " WHERE cinema_id="+cinemaID;
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch(Exception e) {e.printStackTrace();}
    }
    public void editCinema(int cinemaID, String cinemaName,String cinemaAdress){
        try{
            String sql = "UPDATE " + tablename + " SET cinema_name='"+cinemaName+"', cinema_adress='"+cinemaAdress+"' WHERE cinema_id="+cinemaID;
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        }
        catch(Exception e) {e.printStackTrace();}
    }
    public void printCinema(){

        try{
            String sql = "SELECT * FROM " + tablename;
            Statement statement = connection.createStatement();
            ResultSet rs  = statement.executeQuery(sql);
            while (rs.next()) {

                System.out.println(rs.getInt(1)+
                        " | "+rs.getString(2)+
                        " | "+rs.getString(3));
            }
        }
       catch(Exception e) {e.printStackTrace();}
    }


}