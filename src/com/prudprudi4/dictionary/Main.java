package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.util.Translator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try {
            Translator.translate("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> new MainFrame());
    }
}