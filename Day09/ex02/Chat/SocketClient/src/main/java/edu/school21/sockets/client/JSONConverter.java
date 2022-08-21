package edu.school21.sockets.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class JSONConverter {
    private static final String TAG_MSG = "message";
    private static final String TAG_TIME = "time";

    public static JSONMessage parseToObject(String msg) {
        JSONMessage newMessage = new JSONMessage();
        JSONParser parser = new JSONParser();
        try {
            JSONObject messageObject = (JSONObject) parser.parse(msg);
            newMessage.setMessage((String) messageObject.get(TAG_MSG));
            String time = (String) messageObject.get(TAG_TIME);
            newMessage.setTime(LocalDateTime.parse(time));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newMessage;
    }

    public static JSONObject makeJSONObject(String msg) {
        try {
            Map<String, String> mapMessage = new HashMap<>();
            mapMessage.put(TAG_MSG, msg);
            mapMessage.put(TAG_TIME, LocalDateTime.now().toString());
            return new JSONObject(mapMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
