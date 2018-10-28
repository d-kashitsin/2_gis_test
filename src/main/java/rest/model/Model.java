package rest.model;



import java.sql.*;

public class Model {

    private final Reserv reserv;
    private final Room room;
    private final Cinema cinema;
    private final Seats seats;
    private final User user;

    public Model() throws Exception{
        final Connection connection = DriverManager.getConnection("jdbc:h2:./cinema_orders",
                "sa", "");

        this.room = new Room(connection);
        this.cinema = new Cinema(connection);
        this.seats = new Seats(connection);
        this.user = new User(connection);
        this.reserv = new Reserv(connection,room);
    }

    public Reserv getReserv() {
        return reserv;
    }

    public Room getRoom() {
        return room;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public Seats getSeats() {
        return seats;
    }

    //В условиях задание не указаны требования к работы с пользователями.
    // Для проверки использован единственный пользователь, но возможно расширение с использованием нескольких
    public User getUser() {
        return user;
    }

}
