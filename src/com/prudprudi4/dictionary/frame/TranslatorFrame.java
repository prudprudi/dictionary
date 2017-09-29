package com.prudprudi4.dictionary.frame;

import com.prudprudi4.dictionary.Dictionary;
import com.prudprudi4.dictionary.DocumentSizeFilter;
import com.prudprudi4.dictionary.SortedListModel;
import com.prudprudi4.dictionary.WordEntity;
import com.prudprudi4.dictionary.util.TextFormatter;
import com.prudprudi4.dictionary.util.Translator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class TranslatorFrame extends JDialog {
    private final static String TITLE = "Translate";
    private final static int WIDTH = 302;
    private final static int HEIGHT = 300;

    private final JTextField fromField = new JTextField();
    private final JEditorPane editorPane = new JEditorPane();
    private final JPanel mainPanel = new JPanel();
    private final MainFrame parent;

    private WordEntity wEntity;

    private void saveWord() {

    }

    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parent);
        getContentPane().add(mainPanel);
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        final JScrollPane toPane = new JScrollPane(editorPane);
        editorPane.setPreferredSize(new Dimension(288, 237));
        mainPanel.add(fromField);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(toPane);

        fromField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (fromField.getText().trim().isEmpty()) {
                    editorPane.setText("");
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dispose();
                    parent.getListView().requestFocus();

                    String text = fromField.getText().trim();
                    SortedListModel listModel = parent.getListModel();

                    boolean isAdded = listModel.addElement(text);
                    Map<String, WordEntity> words = parent.getWords();

                    if (isAdded) {
                        words.put(text, wEntity);
                    }

                    int index = listModel.getIndexByElement(text);
                    parent.getListView().setSelectedIndex(index);

                    wEntity = null;
                }
            }
        });

        DefaultStyledDocument doc = new DefaultStyledDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(Dictionary.MAX_SYMBOLS));
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fromFieldHandler(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fromFieldHandler(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        fromField.setDocument(doc);
    }


    private void fromFieldHandler(DocumentEvent event) {
        new Thread(() -> {
            try {
                String text = fromField.getText();
                JSONObject obj = Translator.translate(text);
                JSONArray arr = (JSONArray) obj.get("result");
                if (arr == null || arr.isEmpty()) {
                    editorPane.setText("");
                    return;
                }
                wEntity = new WordEntity(arr);
                String format = TextFormatter.format(wEntity);
                editorPane.setText(format);

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
