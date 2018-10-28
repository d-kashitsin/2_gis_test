
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import rest.controller.Controller;


import static org.junit.Assert.assertEquals;

public class ApiWebTest {
    private static final String USER_AGENT = "Mozilla/5.0";
    private final JSONParser parser = new JSONParser();

        @Before
        public void before() throws Exception {
            final Path databaseFilePath = Paths.get("cinema_orders.mv.db");
            if (Files.exists(databaseFilePath)) {
                Files.delete(databaseFilePath);
            }
            PrepareDB();
            Controller controller = new Controller(9876);
            controller.run();
            Thread.sleep(3000);
        }
    @Test
    public void testApiReservPost() throws Exception{

        System.out.println("\nTesting 1 - Send Http POST request");


        Map<String,String> correctParametersSet1 = new HashMap<>();
        correctParametersSet1.put("room", "2");
        correctParametersSet1.put("user", "1");
        correctParametersSet1.put("places", "{\"0\":22,\"1\":23}");
        String thirdTest = ApiWebTest.sendPostApache(correctParametersSet1).toString();

        assertEquals("{\"result\":{\"message\":\"Your reserv number: '1'\"},\"code\":200,\"status\":\"OK\"}",thirdTest);

        Thread.sleep(1000);
        Map<String,String> correctParametersSet2 = new HashMap<>();
        correctParametersSet2.put("room", "2");
        correctParametersSet2.put("user", "1");
        correctParametersSet2.put("places", "{\"0\":12}"); //нужно менять каждый раз или подготавливать базу

        thirdTest = ApiWebTest.sendPostApache(correctParametersSet2).toString();
        assertEquals("{\"result\":{\"message\":\"Your reserv number: '3'\"},\"code\":200,\"status\":\"OK\"}",thirdTest);

        Thread.sleep(1000);
        Map<String,String> incorrectParametersSet = new HashMap<>();
        incorrectParametersSet.put("room", "2");
        incorrectParametersSet.put("user", "1");
        incorrectParametersSet.put("places", "{\"0\":12}");
        thirdTest = ApiWebTest.sendPostApache(incorrectParametersSet).toString();
        Object json = parser.parse(thirdTest);
        Map<String, Object> object = (Map<String, Object>) json;
        System.out.println(object.get("message"));
        Long code = (Long) object.get("code");
        assertEquals(400,code.intValue());
        Thread.sleep(1000);





        String urlParams;

        System.out.println("Testing 2 - Get room status info");
        //room number = testID
        urlParams = "/room/" + String.valueOf("2");
        String firstTest = ApiWebTest.sendGetApache(urlParams).toString();


        assertEquals("{\"result\":{\"1\":\"Free\",\"2\":\"Free\",\"3\":\"Free\",\"4\":\"Free\",\"5\":\"Free\",\"6\":\"Free\",\"7\":\"Free\",\"8\":\"Free\",\"9\":\"Free\",\"10\":\"Free\",\"11\":\"Free\",\"12\":\"Reserved\",\"13\":\"Free\",\"14\":\"Free\",\"15\":\"Free\",\"16\":\"Free\",\"17\":\"Free\",\"18\":\"Free\",\"19\":\"Free\",\"20\":\"Free\",\"21\":\"Free\",\"22\":\"Reserved\",\"23\":\"Reserved\",\"24\":\"Free\",\"25\":\"Free\",\"26\":\"Free\",\"27\":\"Free\",\"28\":\"Free\",\"29\":\"Free\",\"30\":\"Free\"},\"code\":200,\"status\":\"OK\"}",firstTest);


            Thread.sleep(1000);


            System.out.println("Testing 3 - Get reserv info");
            //reserv naumber = testID
            urlParams = "/reserv/" + String.valueOf("3");
            String secondTest = ApiWebTest.sendGetApache(urlParams).toString();
            json = parser.parse(secondTest);
            object = (Map<String, Object>) json;
            assertEquals("{\"result\":{\"Cinema Name\":\"Синемапарк\",\"Room Name\":\"1\",\"id\":3,\"place(s)\":[12],\"movieName\":\"movieName\"},\"code\":200,\"status\":\"OK\"}",secondTest);
            Thread.sleep(1000);

//Incorrect params
        Map<String,String> incorrectParametersSet2 = new HashMap<>();
        incorrectParametersSet2.put("room", "2");
        incorrectParametersSet2.put("user", "1");
        incorrectParametersSet2.put("places", "{b:p}");
        thirdTest = ApiWebTest.sendPostApache(incorrectParametersSet2).toString();
        json = parser.parse(thirdTest);
        object = (Map<String, Object>) json;
        System.out.println(object.get("message"));
        code = (Long) object.get("code");
        assertEquals(400,code.intValue());

        Thread.sleep(1000);

            urlParams = "/room/" + "a";
            firstTest = ApiWebTest.sendGetApache(urlParams).toString();
            json = parser.parse(firstTest);
            object = (Map<String, Object>) json;
            code = (Long) object.get("code");
            assertEquals(400,code.intValue());


        }


    // HTTP POST request
    private static StringBuffer sendGetApache(String urlParams) throws Exception{
        String url = "http://localhost:9876/"+urlParams;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
        return result;
    }

    private static StringBuffer sendPostApache(Map<String,String> parametersMap) throws Exception {


        String url ="http://localhost:9876/reserv";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        //urlParameters = "room=2&user=1&places={\"0\":52,\"1\":53}";
        for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
            urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            String key = entry.getKey();
            Object value = entry.getValue();
            // ...
        }

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
        return result;

    }

    private static void PrepareDB() throws Exception{
        final Connection connection = DriverManager.getConnection("jdbc:h2:./cinema_orders",
                "sa", "");

        final String query1 = "CREATE TABLE cinema (cinema_id identity PRIMARY KEY, cinema_name varchar(255) NOT NULL, cinema_adress varchar(255) NOT NULL)";
        final String query2 = "CREATE TABLE rooms (room_id identity PRIMARY KEY, cinema_id INT, room_name varchar(255) NOT NULL, places_amount INT NOT NULL, FOREIGN KEY (cinema_id) REFERENCES cinema(cinema_id))";
        final String query3 = "CREATE TABLE users (userid identity PRIMARY KEY, first_name varchar(255) NOT NULL, email varchar(255) NOT NULL)";
        final String query4 = "CREATE TABLE reserv (r_id identity PRIMARY KEY, reserv_id INT, room_id INT, userid INT, place_number INT, FOREIGN KEY (userid) REFERENCES users(userid))";

        final String cinemaTable = "INSERT INTO cinema(cinema_name,cinema_adress) VALUES('Синемапарк','пл.Маркса 7')";
        final String roomTable = "INSERT INTO rooms(cinema_id,room_name,places_amount) VALUES(1,'IMAX',600)";
        final String roomTable2 = "INSERT INTO rooms(cinema_id,room_name,places_amount) VALUES(1,'1',30)";
        final String userTable = "INSERT INTO users(first_name,email) VALUES('John','john@gmail.com')";



        try (final Statement statement = connection.createStatement()) {
            statement.execute(query1);
            statement.execute(query2);
            statement.execute(query3);
            statement.execute(query4);

            statement.execute(cinemaTable);
            statement.execute(roomTable);
            statement.execute(roomTable2);
            statement.execute(userTable);
        }
    }
}
