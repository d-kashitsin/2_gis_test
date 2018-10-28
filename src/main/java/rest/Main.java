package rest;
import org.json.simple.parser.JSONParser;
import rest.model.*;
import rest.view.View;
import rest.controller.Controller;


public class Main {
    public static void main(String[] args) throws Exception {
        //int portNumber = Integer.parseInt(args[0]);
        int portNumber = 9876;
        new Controller(portNumber).run();

    }
}