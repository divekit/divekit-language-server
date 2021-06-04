package server.gen;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ObjectVariableExtensionsGenerator {

    String extensionsPath;
    ArrayList<String> extensionKeys;

    public ObjectVariableExtensionsGenerator(String extensionsPath) {
        this.extensionsPath = extensionsPath;
        extensionKeys = new ArrayList<>();
    }

    public ArrayList<String> generateExtensionsFor(String key, ArrayList<String> extensions) {

        Object extensionsConfig = null;

        try {
            FileReader reader = new FileReader(extensionsPath);

            JSONParser parser = new JSONParser();
            extensionsConfig = parser.parse(reader);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = (JSONArray) extensionsConfig;

        for (Object object : jsonArray) {

            JSONObject objectDefinition = (JSONObject) object;

            String objectId = objectDefinition.get("id").toString();

            if(!extensions.contains(objectId)) {
                continue;
            }

            JSONObject variableExtensions = (JSONObject) objectDefinition.get("variableExtensions");

            for(Object extension : variableExtensions.keySet()) {

                StringBuilder variableBuilder = new StringBuilder(key);
                String next = (String) extension;

                variableBuilder.append(next);

                extensionKeys.add(variableBuilder.toString());

                LinkedHashSet<String> set = new LinkedHashSet<>(extensionKeys);
                extensionKeys.clear();
                extensionKeys.addAll(set);
            }
        }
        return extensionKeys;
    }
}
