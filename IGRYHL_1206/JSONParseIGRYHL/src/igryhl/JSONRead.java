package igryhl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class JSONRead {
    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();

            Object obj = parser.parse(new FileReader("kurzusfelvetelIGRYHL.json"));

            JSONObject jsonObject = (JSONObject) obj;

            displayJsonObject("", jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayJsonObject(String prefix, JSONObject jsonObject) {
        for (Object key : jsonObject.keySet()) {
            String keyStr = (String) key;
            Object value = jsonObject.get(keyStr);

            if (value instanceof JSONObject) {
                System.out.println(prefix + keyStr + ": ");
                displayJsonObject(prefix + "  ", (JSONObject) value);
            } else if (value instanceof JSONArray) {
                System.out.println(prefix + keyStr + ": ");
                displayJsonArray(prefix + "  ", (JSONArray) value);
            } else {
                System.out.println(prefix + keyStr + ": " + value);
            }
        }
    }

    private static void displayJsonArray(String prefix, JSONArray jsonArray) {
        for (Object item : jsonArray) {
            if (item instanceof JSONObject) {
                displayJsonObject(prefix + "  ", (JSONObject) item);
                System.out.println();
            } else if (item instanceof JSONArray) {
                displayJsonArray(prefix + "  ", (JSONArray) item);
            } else {
                System.out.println(prefix + "  " + item);
            }
        }
    }
}
