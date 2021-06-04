package server.gen;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ObjectVariableGenerator {

    String variationsPath;
    String extensionsPath;

    char divideChar = '_';
    ArrayList<String> variationKeys = new ArrayList<>();

    public ObjectVariableGenerator(String variationsPath, String extensionsPath) {
        this.variationsPath = variationsPath;
        this.extensionsPath = extensionsPath;
    }

    public ArrayList<String> readVariables() {

        Object variationsConfig = null;

        try {
            FileReader reader = new FileReader(variationsPath);

            JSONParser parser = new JSONParser();
            variationsConfig = parser.parse(reader);


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonConfig = (JSONObject) variationsConfig;
        JSONArray objects = (JSONArray) jsonConfig.get("objects");

        for (Object object : objects) {

            JSONObject objectDefinition = (JSONObject) object;

            //Getting the id
            String objectId =
                    objectDefinition.get("ids") != null ? objectDefinition.get("ids").toString() : "";

            //Getting the extensions
            JSONArray extensions =
                    objectDefinition.get("variableExtensions") != null ? (JSONArray) objectDefinition.get("variableExtensions") : new JSONArray();

            ArrayList<String> extensionStrings = new ArrayList<>();
            for (Object ex : extensions) {
                extensionStrings.add(ex.toString());
            }

            //Getting the variations
            JSONArray variations =
                    objectDefinition.get("objectVariations") != null ? (JSONArray) objectDefinition.get("objectVariations") : new JSONArray() ;

            for (Object variation : variations) {

                loadNestedVariation(objectId, variation);
            }

            ObjectVariableExtensionsGenerator objectVariableExtensionsGenerator = new ObjectVariableExtensionsGenerator(extensionsPath);
            ArrayList<String> extensionList = objectVariableExtensionsGenerator.generateExtensionsFor(objectId, extensionStrings);
            variationKeys.addAll(extensionList);

        }
        return variationKeys;
    }

    /**
     * Recursive method to generate all variables
     */
    private void loadNestedVariation(String variable, Object object) {
        JSONObject jsonObject = (JSONObject) object;

        for (Object key : jsonObject.keySet()) {
            StringBuilder variableBuilder = new StringBuilder(variable);
            String next = (String) key;

            // don't want the id
            if(next.equals("id")) {
                continue;
            }

            if(jsonObject.get(next) instanceof JSONObject) {
                variableBuilder.append(divideChar);
                variableBuilder.append(next);
                loadNestedVariation(variableBuilder.toString(), jsonObject.get(next));
            } else {
                variableBuilder.append(next);

                variationKeys.add(variableBuilder.toString());

                // prevent duplicates
                LinkedHashSet<String> set = new LinkedHashSet<>(variationKeys);
                variationKeys.clear();
                variationKeys.addAll(set);
            }
        }
    }
}
