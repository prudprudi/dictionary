package com.prudprudi4.dictionary.frame;

import com.prudprudi4.dictionary.SortedListModel;
import com.prudprudi4.dictionary.util.Translator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TranslatorFrame extends JDialog {
    private final static String TITLE = "Translate";
    private final static int WIDTH = 302;
    private final static int HEIGHT = 300;

    private final JTextArea fromArea = new JTextArea();
    private final JTextArea toArea = new JTextArea();
    private final JPanel mainPanel = new JPanel();
    private final MainFrame parent;

    private void setTextArea(JTextArea tArea, boolean isEditable) {
        tArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tArea.setLineWrap(true);
        tArea.setWrapStyleWord(true);
        tArea.setEditable(isEditable);
    }

    private void saveWord() {

    }

    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parent);
        getContentPane().add(mainPanel);

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

        fromArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                String fromText = fromArea.getText().trim();
                if (fromText.length() == 0) {
                    toArea.setText("");
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dispose();
                    parent.getListView().requestFocus();

                    String text = fromArea.getText();
                    SortedListModel<String> listModel = parent.getListModel();

                    boolean isAdded = listModel.addElement(text);

                    int index = listModel.getIndexByElement(text);
                    parent.getListView().setSelectedIndex(index);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void textFieldListener(DocumentEvent event) {
        new Thread(() -> {
            try {
                String text = fromArea.getText();
                toArea.setText(Translator.translate(text));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    TranslatorFrame(MainFrame parent) {
        this.parent = parent;
        init();
    }
}
