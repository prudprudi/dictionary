package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.util.Translator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class TranslatorFrame extends BaseFrame {
    private final JTextArea fromArea = new JTextArea();
    private final JTextArea toArea = new JTextArea();
    private final JFrame parent;

    private void setTextArea(JTextArea tArea, boolean isEditable) {
        tArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tArea.setLineWrap(true);
        tArea.setWrapStyleWord(true);
        tArea.setEditable(isEditable);
    }

    protected void init() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setTextArea(fromArea, true);
        setTextArea(toArea, false);

        final JScrollPane fromPane = new JScrollPane(fromArea);
        final JScrollPane toPane = new JScrollPane(toArea);
        toPane.setPreferredSize(new Dimension(0,150));
        mainPanel.add(fromPane);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(toPane);

        fromArea.getDocument().addDocumentListener(new DocumentListener() {
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setEnabled(true);
            }
        });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void textFieldListener(DocumentEvent event) {
        String text = fromArea.getText();
        String result;

        try {
            result = (text.length() == 0) ? "" : Translator.translate(text);

            toArea.setText(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    TranslatorFrame(JFrame parent) {
        super("Translate", 302, 300);
        this.parent = parent;
        parent.setEnabled(false);
        init();
        setVisible(true);
    }
}
