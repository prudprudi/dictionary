package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.frame.MainFrame;

import javax.swing.*;

public class Dictionary {
    public final static int MAX_SYMBOLS = 35;
    public final static String WORDS_STORAGE_PATH = "words.txt";

    Dictionary() {
        JFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
