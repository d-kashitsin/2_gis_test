package rest.view;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rest.controller.ApiResponse;
import rest.controller.Message;
import rest.model.ReservData;
import rest.model.ReservDataView;
import rest.model.RoomData;
import rest.model.RoomDataView;

import java.util.*;


public class View {

    public static String render(final Object object) {
        // преобразуем java-объект в вид, с которым работает библиотека
        final Object json = toJson(object);
        // если получившийся объект - json object
        // отрисовываем как json объект
        if (json instanceof Map) {
            return new JSONObject((Map) json).toJSONString();
        }
        // если получившийся объект - json list
        // отрисовываем как json список
        if (json instanceof List) {
            final JSONArray result = new JSONArray();
            result.addAll((List) json);
            return result.toJSONString();
        }
        // иначе просто отрисовываем сам объект
        return json.toString();
    }


    private static Object toJson(final Object object) {
        // если это json-объект
        if (object instanceof Map) {
            final Map o = (Map) object;
            final Map result = new HashMap();
            for (Object key: o.keySet()) {
                result.put(key, toJson(o.get(key)));
            }
            return result;
        }
        // если это json-список
        if (object instanceof List) {
            final List l = (List) object;
            final List result = new ArrayList();
            for (Object o: l) {
                result.add(toJson(o));
            }
            return result;
        }
        //Если это массив, то приводим его к списку
        if (object instanceof byte[]) {
            final byte[] ints = (byte[]) object;
            final List result = new ArrayList();
            for (byte i: ints) {
                result.add(i);
            }
            return result;
        }
        if (object instanceof int[]) {
            final int[] ints = (int[]) object;
            final List result = new ArrayList();
            for (int i: ints) {
                result.add(i);
            }
            return result;
        }
        if (object instanceof long[]) {
            final long[] ints = (long[]) object;
            final List result = new ArrayList();
            for (long i: ints) {
                result.add(i);
            }
            return result;
        }
        if (object instanceof short[]) {
            final short[] ints = (short[]) object;
            final List result = new ArrayList();
            for (short i: ints) {
                result.add(i);
            }
            return result;
        }
        if (object instanceof char[]) {
            final char[] ints = (char[]) object;
            final List result = new ArrayList();
            for (char i: ints) {
                result.add(i);
            }
            return result;
        }
        if (object instanceof boolean[]) {
            final boolean[] ints = (boolean[]) object;
            final List result = new ArrayList();
            for (boolean i: ints) {
                result.add(i);
            }
            return result;
        }
        if (object instanceof float[]) {
            final float[] ints = (float[]) object;
            final List result = new ArrayList();
            for (float i: ints) {
                result.add(i);
            }
            return result;
        }
        if (object instanceof double[]) {
            final double[] ints = (double[]) object;
            final List result = new ArrayList();
            for (double i: ints) {
                result.add(i);
            }
            return result;
        }


            if (object instanceof RoomData) {
                return RoomDataView.toJson((RoomData) object);
            }
            if (object instanceof ReservData) {
                return ReservDataView.toJson((ReservData) object);
                }

                //Обрабатываем ответы апи
        if (object instanceof ApiResponse) {
            final ApiResponse response = (ApiResponse) object;
            final Map<String, Object> result = new HashMap<>();
            result.put("code", response.getCode());
            result.put("status", response.getStatus());
            result.put("result", toJson(response.getResult()));
            return result;
        }
        if (object instanceof Message) {
            final Message message = (Message) object;
            final Map<String, Object> result = new HashMap<>();
            result.put("message", message.getMessage());
            return result;
        }
        // Если же это другой класс оставляем как есть
        return object;
    }



}


