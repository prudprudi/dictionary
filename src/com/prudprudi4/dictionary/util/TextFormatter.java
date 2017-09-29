package com.prudprudi4.dictionary.util;

import com.prudprudi4.dictionary.WordEntity;

import java.util.ArrayList;
import java.util.Map;

public class TextFormatter {
    public static String format(WordEntity wEntity) {

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(wEntity.getTranslation() + "<br><br>");

        Map<String, ArrayList<String>> translationInfo = wEntity.getTranslationInfo();
        for (Map.Entry<String, ArrayList<String>> e: translationInfo.entrySet()) {
            sb.append("<b>" + e.getKey() + "</b><br>");
            for (String s: e.getValue()) {
                sb.append(s + "<br>");
            }
            sb.append("<br>");
        }
        sb.append("</html>");

        return sb.toString();
    }
}
