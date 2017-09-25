package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.util.Translator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;

public class TranslatorFrame extends BaseFrame {
    private JTextField tField = new JTextField();
    private JTextArea tArea = new JTextArea();

    protected void init() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        tArea.setLineWrap(true);
        tArea.setEditable(false);

        JScrollPane sPane = new JScrollPane(tArea);
        sPane.setPreferredSize(new Dimension(0, 238));
        mainPanel.add(tField);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(sPane);

        tField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textFieldListener(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textFieldListener(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void textFieldListener(DocumentEvent event) {
        String text = tField.getText();

        if (text.length() == 0) return;

        try {
            String result = Translator.translate(text);
            tArea.setText(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    TranslatorFrame() {
        super("Translate", 300, 300);
        init();
        setVisible(true);
    }
}
