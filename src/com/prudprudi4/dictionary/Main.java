package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.util.Translator;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class Main {

    private static void setUIFont (FontUIResource f){
        Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
        while (keys.hasMoreElements()) {
        Object key = keys.nextElement();
        Object value = UIManager.get (key);
        if (value != null && value instanceof FontUIResource) {
            UIManager.put(key, f);
        }
    }
}

    public static void main(String[] args) {
        try {
            Translator.translate("структура");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setUIFont(new FontUIResource(new Font("Segoe UI", Font.PLAIN, 12)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> new Dictionary());
    }
}