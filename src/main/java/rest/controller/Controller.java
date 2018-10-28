package rest.controller;

import org.json.simple.parser.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.model.*;
import rest.view.View;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static spark.Spark.*;

public class Controller implements Runnable {
    private final Logger logger;
    private final Model model;
    Cinema cinema;
    Reserv reserv;
    Room room;
    Seats seats;

    public Controller(final int portNumber) throws Exception {
        port(portNumber);
        this.model = new Model();
        this.cinema = model.getCinema();
        this.reserv = model.getReserv();
        this.room = model.getRoom();
        this.seats = model.getSeats();
        this.logger = LoggerFactory.getLogger(Controller.class);
    }

    @Override
    public void run() {


        get("/room/:id", this::getRoomSeats);
        get("/reserv/:id", this::getReservInfo);
        post("/reserv", this::createReserv);
        get("/reserv/delete/:id", this::deleteReserv);


        notFound(View.render(new NotFound(new Message("The requested resource is not found"))));

        internalServerError(View.render(new InternalServerError(new Message("Oops! Something is goes wrong. Try to reload page..."))));

        exception(Exception.class, (e, req, res) -> {
            logger.error(String.format("%s", e.getMessage()), e);
            res.status(HTTP_INTERNAL_ERROR);
            res.body(View.render(new InternalServerError(new Message(e.getMessage()))));
        });

        after((req, res) -> res.type("application/json"));
    }


    private Object deleteReserv(Request request, Response response) throws Exception {
        final String idString = request.params(":id");
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message(String.format("Unable to parse reserv id from string: '%s'", idString))));
        }
        reserv.deleteReserv(id);

        return View.render(new ApiResponse<>(new Message(String.format("Reserv '%s' deleted", idString))));
    }

    //curl -X POST --data "room=2&user=1&places={\"0\":22,\"1\":23}" http://127.0.0.1:9876/reserv/ - забронировать места
    private Object createReserv(Request request, Response response) throws Exception {


        final String userID = request.queryParams("user");
        final String places = request.queryParams("places");
        final String roomString = request.queryParams("room");
        //System.out.println(places);
        if (userID == null) {
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message("Missing form field:userID")));
        }
        if (places == null) {
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message("Missing form field:places")));
        }
        if (roomString == null) {
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message("Missing form field:roomString")));
        }
        if (userID.isEmpty()||places.isEmpty()||roomString.isEmpty()) {
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message("Empty value")));
        }

        final int roomNumber;
        try {roomNumber = Integer.parseInt(roomString);}
        catch (NumberFormatException e){
            System.out.println("wrong room");
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message("Incorrect room number")));
        }

        List<Integer> intPlaces = new ArrayList<>();
        final JSONParser parser = new JSONParser();

        final Object json;
        try {json = parser.parse(places);}
        catch (Exception e){
            System.out.println("not json parameters");
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message("Incorrect places numbers")));
        }

        if (json instanceof Map) {
            final Map<String, Long> object = (Map<String, Long>) json;

            for(String key:object.keySet()){

                intPlaces.add(object.get(key).intValue());
            }
        }

        final int id = reserv.massReserving(roomNumber, Integer.parseInt(userID),intPlaces);
        if(id==-1){
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message("Selected reserved places")));
        }
        else
        return View.render(new ApiResponse<>(new Message(String.format("Your reserv number: '%s'", id))));

    }

    private Object getReservInfo(Request request, Response response) throws Exception {
        final String idString = request.params(":id");
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message(String.format("Unable to parse reserv id from string: '%s'", idString))));
        }
        ReservData reservData = seats.getReservData(id);
        if(reservData.getId()==-1){

            return View.render(new ApiResponse<>(new Message("Reserv does not exist")));
        }

        return View.render(new ApiResponse<>(reservData));

    }


    private Object getRoomSeats(Request request, Response response) throws Exception {
        final String idString = request.params(":id");
        int id;
        try {id = Integer.parseInt(idString);}
        catch (NumberFormatException e) {
            response.status(HTTP_BAD_REQUEST);
            return View.render(new BadRequest(new Message(String.format("Unable to parse reserv id from string: '%s'", idString))));
        }
        return View.render(new ApiResponse<>(seats.getAvailable(idString,room,reserv)));

    }



}
