package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.util.Translator;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        try {
            Translator.translate("test");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> new MainFrame());
    }
}