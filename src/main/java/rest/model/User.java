package rest.model;

import java.sql.Connection;

public class User {
    public int id;
    public String firstName;
    public String email;

    private final Connection connection;

    public User(Connection connection) {
        this.connection = connection;
    }




    public int addUser(String first_name, String email )
    {
        return id;
    }

}