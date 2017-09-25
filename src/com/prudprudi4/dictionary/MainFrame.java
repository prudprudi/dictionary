package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.util.Disposition;
import com.prudprudi4.dictionary.util.Translator;
import javafx.scene.layout.Border;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainFrame extends BaseFrame {

    protected void init() {
        mainPanel.setLayout(new BorderLayout());

        JList<String> jList = new JList<>(new String[]{"aaaaaqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", "aaaaa", "aaaaa", "aaaaa","sfsdf",
                "sdfsdf", "sfsdf", "assfaf", "sfsdf", "sdfsdf", "sfsdf", "assfaf"});
        JList<String> jList1 = new JList<>(new String[]{"aaaaa", "aaaaa", "aaaaa", "aaaaa","sfsdf",
                "sdfsdf", "sfsdf", "assfaf", "sfsdf", "sdfsdf", "sfsdf", "assfaf"});

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JPanel panel = new JPanel();
        JScrollPane sPane1 = new JScrollPane(jList);
        JScrollPane sPane2 = new JScrollPane(textArea);
        sPane1.setPreferredSize(new Dimension(215,420));
        sPane2.setPreferredSize(new Dimension(215,420));
        panel.setLayout(new FlowLayout());
        panel.add(sPane1);
        panel.add(sPane2);
        mainPanel.add(panel, BorderLayout.NORTH);

        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new FlowLayout());
        JButton addButton = new JButton("Добавить");
        JButton delButton = new JButton("Удалить");
        buttonsPane.add(addButton);
        buttonsPane.add(delButton);
        mainPanel.add(buttonsPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addButton.addActionListener((e) -> new TranslatorFrame());
    }

    MainFrame() {
        super("Dictionary", 450, 500);
        init();
        setVisible(true);
    }
}