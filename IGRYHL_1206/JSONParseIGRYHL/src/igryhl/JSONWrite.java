package igryhl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONWrite {


    public static void main(String[] args) {
        List<Map<String, Object>> kurzusok = new ArrayList<>();
        kurzusok.add(createKurzus("Adatkezelés XML-ben", "kedd", 12, 14, "XXXII. előadó", "Dr. Bednarik László", "Programtervező informatikus"));
        kurzusok.add(createKurzus("Adatkezelés XML-ben", "szerda", 10, 12, "Inf. 101", "Dr. Bednarik László", "Programtervező informatikus"));
        kurzusok.add(createKurzus("Vállalati Információs Rendszerek Fejlesztése", "szerda", 14, 16, "Inf. 101", "Dr. Sasvári Péter", "Programtervező informatikus"));
        kurzusok.add(createKurzus("Vállalati Információs Rendszerek Fejlesztése", "szerda", 18, 20, "Inf. 101", "Dr. Sasvári Péter", "Programtervező informatikus"));

        Map<String, Object> kurzusfelvetel = new HashMap<>();
        kurzusfelvetel.put("kurzusok", kurzusok);

        Map<String, Object> root = new HashMap<>();
        root.put("kurzusfelvetelIGRYHL", kurzusfelvetel);

        fileWrite(root, "kurzusfelvetelIGRYHL1.json");
        consoleWrite(root);
    }


    private static void consoleWrite(Map<String, Object> jsonObject) {
        System.out.println("A felépített JSON dokumentum tartalma:\n");

        String formattedOutput = formatJson(new JSONObject(jsonObject).toJSONString());
        System.out.println(formattedOutput);
    }

    private static String formatJson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(new StringReader(json)).getAsJsonObject();
        return gson.toJson(jsonObject);
    }

    private static void fileWrite(Map<String, Object> jsonObject, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(indentJson(toJsonString(jsonObject)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String indentJson(String json) {
        String out = "";
        int indent = 0;
        for (int i = 0; i < json.length() - 1; i++) {
            out += json.charAt(i);
            if (json.charAt(i) == ',') {
                out += "\n" + "  ".repeat(indent > 0 ? indent : 0);
            } else if (json.charAt(i) == '{' || json.charAt(i) == '[') {
                indent++;
                out += "\n" + "  ".repeat(indent > 0 ? indent : 0);
            } else if ((json.charAt(i + 1) == '}' || json.charAt(i + 1) == ']')) {
                indent--;
                out += "\n" + "  ".repeat(indent > 0 ? indent : 0);
            }
        }
        out += json.charAt(json.length() - 1);
        return out;
    }

    private static String toJsonString(Map<String, Object> jsonObject) {
        return new JSONObject(jsonObject).toJSONString();
    }


    private static Map<String, Object> createKurzus(String kurzus, String nap, int tol, int ig, String helyszin, String oktato, String szak) {
        Map<String, Object> kurzusObj = new HashMap<>();
        Map<String, Object> idopont = new HashMap<>();

        idopont.put("nap", nap);
        idopont.put("tol", tol);
        idopont.put("ig", ig);

        kurzusObj.put("kurzus", kurzus);
        kurzusObj.put("idopont", idopont);
        kurzusObj.put("helyszin", helyszin);
        kurzusObj.put("oktato", oktato);
        kurzusObj.put("szak", szak);

        return kurzusObj;
    }
}
