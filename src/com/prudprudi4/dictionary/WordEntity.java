package com.prudprudi4.dictionary;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordEntity {
    private String translation;
    private Map<String, ArrayList<String>> translationInfo = new HashMap<>();

    public String getTranslation() {
        return translation;
    }

    public Map<String, ArrayList<String>> getTranslationInfo() {
        return translationInfo;
    }

    public WordEntity(JSONArray jsonArr){
        translation = (String) jsonArr.get(0);

        if (jsonArr.size() == 1) { return; }

        JSONObject obj = (JSONObject) jsonArr.get(1);
        JSONArray arr;
        ArrayList<String> al;
        String type;
        for (WordType wordType: WordType.values()) {
            type = wordType.getType();

            if (obj.containsKey(type)) {
                al = new ArrayList<>();
                arr = (JSONArray) obj.get(type);
                for (Object s: arr) {
                    al.add((String)s);
                }
                translationInfo.put(type, al);
            }
        }
    }
}
