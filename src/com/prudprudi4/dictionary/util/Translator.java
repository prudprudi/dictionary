package com.prudprudi4.dictionary.util;

import com.sun.istack.internal.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Properties;

public class Translator {
    private final static String API_KEY = getApiKey();

    private static String getApiKey() {
        Properties props = new Properties();
        String key = null;

        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);

            key = props.getProperty("api_key");
            key = (key != null) ? key : "trnsl.1.1.20170922T205950Z.e8fdcc1a5f095ef8.4985b96369d828b60dd9ece94dba46da2ea61fd9";

        } catch (IOException e) {
            e.printStackTrace();
        }

        return key;
    }

    private final static String UNIQUE_TRANSLATE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
            "key=" + API_KEY + "&lang=%s&text=%s";
    private final static String MULTIPLE_TRANSLATE_URL = "https://dictionary.yandex.net/dicservice.json/lookupMultiple?" +
            "ui=ru&srv=tr-text&dict=%s.regular&text=%s&flags=103";

    private static String getFromToLang(String phrase) {
        return phrase.codePointAt(0) > 1039 ? "ru-en" : "en-ru";
    }

    private static String prepareUrl(String url, String phrase) {
        String fromTo = getFromToLang(phrase);
        try {
            phrase = URLEncoder.encode(phrase, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format(url, fromTo, phrase);
    }

    private static String getResponse(String url) throws IOException {
        StringBuilder result = null;
        BufferedReader in = null;

        try {
            URLConnection conn = new URL(url).openConnection();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = new StringBuilder();

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } finally {
            if (in != null) {
                in.close();
            }
        }
        return (result == null) ? "" : result.toString();
    }

    public static JSONObject translate(String orig) throws IOException {
        orig = orig.trim();
        if (orig.length() == 0) return new JSONObject();

        String urlUnuque = prepareUrl(UNIQUE_TRANSLATE_URL, orig);
        String urlMultiple = prepareUrl(MULTIPLE_TRANSLATE_URL, orig);

        String translation = getResponse(urlUnuque);
        String translationInfo = getResponse(urlMultiple);

        return getFinalJSON(translation, translationInfo);
    }

    private static JSONObject getFinalJSON(String translation, String translationInfo) {
        JSONParser parser = new JSONParser();
        JSONArray array = new JSONArray();
        JSONObject result = new JSONObject();
        result.put("result", array);

        try {
            JSONObject obj = (JSONObject) parser.parse(translation);
            JSONArray arr = (JSONArray) obj.get("text");
            translation = (String) arr.get(0);
            array.add(translation);

            obj = (JSONObject) parser.parse(translationInfo);
            String str = (obj.containsKey("ru-en")) ? "ru-en" : "en-ru";
            obj = (JSONObject) obj.get(str);
            arr = (JSONArray) obj.get("regular");

            if (arr.isEmpty()) return result;

            array.add(new JSONObject());
            obj = (JSONObject) arr.get(0);
            arr = (JSONArray) obj.get("tr");

            JSONObject objInfo = (JSONObject) array.get(1);
            JSONArray tArr;
            String tooltip;
            for (int i = 0; i < arr.size(); i++) {
                obj = (JSONObject) arr.get(i);
                str = (String) obj.get("text");
                obj = (JSONObject) obj.get("pos");
                tooltip = (String) obj.get("tooltip");

                if (!objInfo.containsKey(tooltip)) {
                    objInfo.put(tooltip, new JSONArray());
                }

                tArr = (JSONArray) objInfo.get(tooltip);
                tArr.add(str);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
