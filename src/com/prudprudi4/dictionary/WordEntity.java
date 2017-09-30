package com.prudprudi4.dictionary;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordEntity {
    private final String translation;
    private final JSONObject json;
    private final Map<String, ArrayList<String>> translationInfo = new HashMap<>();

    public String getTranslation() {
        return translation;
    }
    public JSONObject getJson() {
        return json;
    }
    public Map<String, ArrayList<String>> getTranslationInfo() {
        return translationInfo;
    }

    public WordEntity(JSONObject jsobObj){
        json = jsobObj;
        JSONArray jsonArr = (JSONArray) jsobObj.get("result");
        translation = (String) jsonArr.get(0);

        if (jsonArr.size() == 1) return;

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
