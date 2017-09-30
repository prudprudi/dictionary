package com.prudprudi4.dictionary.util;

import com.prudprudi4.dictionary.Dictionary;
import com.prudprudi4.dictionary.SortedListModel;
import com.prudprudi4.dictionary.WordEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Map;

public class WordStorage {
    public static void load(SortedListModel model, Map<String, WordEntity> words) {
        File file = new File(Dictionary.WORDS_STORAGE_PATH);
        BufferedReader br = null;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            br = new BufferedReader(new FileReader(file));

            JSONParser parser = new JSONParser();
            JSONObject obj;
            String line;
            String[] strs;
            WordEntity wEntity;

            while((line = br.readLine()) != null) {
                strs = line.split("=", 2);
                model.addElement(strs[0]);
                obj = (JSONObject) parser.parse(strs[1]);
                wEntity = new WordEntity(obj);
                words.put(strs[0], wEntity);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }
    public static void save(SortedListModel model, Map<String, WordEntity> words) {
        File file = new File(Dictionary.WORDS_STORAGE_PATH);
        PrintWriter pw = null;
        WordEntity wEntity;
        String jStr;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            for (String s: model) {
                wEntity = words.get(s);
                jStr = wEntity.getJson().toString();
                pw.println(s + "=" + jStr);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
