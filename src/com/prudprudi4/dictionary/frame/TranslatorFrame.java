package com.prudprudi4.dictionary.frame;

import com.prudprudi4.dictionary.Dictionary;
import com.prudprudi4.dictionary.DocumentSizeFilter;
import com.prudprudi4.dictionary.SortedListModel;
import com.prudprudi4.dictionary.WordEntity;
import com.prudprudi4.dictionary.util.Translator;
import org.json.simple.JSONArray;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
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
    private final JEditorPane textPane = new JEditorPane();
    private final Document textPaneDoc = textPane.getDocument();
    private final JPanel mainPanel = new JPanel();
    private final MainFrame parent;

    private boolean block = true;

    private void saveWord() {

    }

    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parent);
        getContentPane().add(mainPanel);

        textPane.setEditable(false);
        textPane.setContentType("text/html");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        final JScrollPane toPane = new JScrollPane(textPane);
        textPane.setPreferredSize(new Dimension(288, 237));
        mainPanel.add(fromField);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(toPane);

        fromField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !block) {
                    dispose();
                    parent.getListView().requestFocus();

                    String text = fromField.getText();
                    SortedListModel listModel = parent.getListModel();

                    boolean isAdded = listModel.addElement(text);

                    int index = listModel.getIndexByElement(text);
                    parent.getListView().setSelectedIndex(index);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

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
        block = true;
        new Thread(() -> {
            try {
                String text = fromField.getText();
                JSONArray jArr = Translator.translate(text);
                if (jArr.isEmpty()) {
                    textPane.setText("");
                    block = true;
                    return;
                }
                WordEntity wEntity = new WordEntity(jArr);
                formattedWordInfo(wEntity);
                block = false;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void formattedWordInfo(WordEntity wEntity) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(wEntity.getTranslation() + "<br><br>");

        Map<String, ArrayList<String>> translationInfo = wEntity.getTranslationInfo();
        for (Map.Entry<String, ArrayList<String>> e: translationInfo.entrySet()) {
            sb.append("<b>" + e.getKey() + "</b><br>");
            for (String s: e.getValue()) {
                sb.append(s + "<br>");
            }
            sb.append("<br>");
        }
        sb.append("</html>");
        textPane.setText(sb.toString());
    }

    TranslatorFrame(MainFrame parent) {
        this.parent = parent;
        init();
    }
}
