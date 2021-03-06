package com.prudprudi4.dictionary.frame;

import com.prudprudi4.dictionary.Dictionary;
import com.prudprudi4.dictionary.DocumentSizeFilter;
import com.prudprudi4.dictionary.SortedListModel;
import com.prudprudi4.dictionary.WordEntity;
import com.prudprudi4.dictionary.util.TextFormatter;
import com.prudprudi4.dictionary.util.Translator;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Map;

class TranslatorFrame extends JDialog {
    private final static String TITLE = "Translate";
    private final static int WIDTH = 302;
    private final static int HEIGHT = 300;

    private final JTextField fromField = new JTextField();
    private final JEditorPane editorPane = new JEditorPane();
    private final JPanel mainPanel = new JPanel();
    private final MainFrame parent;

    private WordEntity wEntity;
    private Thread thread;

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
                String text = fromField.getText().trim().replaceAll("=", "");
                if (text.isEmpty()) {
                    editorPane.setText("");
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        thread.join();
                        dispose();
                        JList<String> listView = parent.getListView();
                        listView.requestFocus();

                        SortedListModel listModel = parent.getListModel();

                        boolean isAdded = listModel.addElement(text);
                        Map<String, WordEntity> words = parent.getWords();

                        if (isAdded) {
                            words.put(text, wEntity);
                        }

                        int index = listModel.getIndexByElement(text);
                        listView.setSelectedIndex(index);
                        parent.showWordInfo();

                        wEntity = null;

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
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
        thread = new Thread(new PrintWordInfoRunnable());
        thread.start();
    }

    private class PrintWordInfoRunnable implements Runnable {
        @Override
        public void run() {
            try {
                String text = fromField.getText();
                JSONObject obj = Translator.translate(text);

                if (obj.isEmpty()) {
                    editorPane.setText("");
                    return;
                }
                wEntity = new WordEntity(obj);
                String format = TextFormatter.format(wEntity);
                editorPane.setText(format);

            } catch (IOException e) {
                new ErrorDialog("Connection problem: " + e.toString());
            }
        }
    }

    TranslatorFrame(MainFrame parent) {
        this.parent = parent;
        init();
    }
}
