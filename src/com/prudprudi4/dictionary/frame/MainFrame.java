package com.prudprudi4.dictionary.frame;

import com.prudprudi4.dictionary.Dictionary;
import com.prudprudi4.dictionary.DocumentSizeFilter;
import com.prudprudi4.dictionary.SortedListModel;
import com.prudprudi4.dictionary.WordEntity;
import com.prudprudi4.dictionary.util.Disposition;
import com.prudprudi4.dictionary.util.TextFormatter;
import com.prudprudi4.dictionary.util.WordStorage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final static String TITLE = "Dictionary";
    private final static int WIDTH = 454;
    private final static int HEIGHT = 520;

    private final Map<String, WordEntity> words = new HashMap<>();
    private final SortedListModel listModel = new SortedListModel();

    private final JList<String> listView = new JList<>(listModel);
    private final JTextField searchField = new JTextField();
    private final JEditorPane editorPane = new JEditorPane();
    private final JPanel mainPanel = new JPanel();

    SortedListModel getListModel() {
        return listModel;
    }
    Map<String, WordEntity> getWords() {
        return words;
    }
    JList<String> getListView() {
        return listView;
    }
    private void searchFieldHandler(DocumentEvent e) {
        String text = searchField.getText();
        int index = listModel.getIndexBySubstring(text);

        if(index != -1) {
            listView.setSelectedIndex(index);
        }
    }
    void showWordInfo() {
        int index = listView.getSelectedIndex();
        String text;
        WordEntity wEntity;

        if (index == -1) return;
        listView.setSelectionInterval(index, index);

        text = listModel.getElementAt(index);
        wEntity = words.get(text);
        text = TextFormatter.format(wEntity);
        editorPane.setText(text);
    }
    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocation(Disposition.getWindowCenterPos(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(mainPanel);

        mainPanel.setLayout(new BorderLayout());

        final JPanel containerPanel = new JPanel();
        final JScrollPane listPane = new JScrollPane(listView);
        final JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

        listPane.setPreferredSize(new Dimension(214,420));
        editorPane.setPreferredSize(new Dimension(214,420));
        editorScrollPane.setPreferredSize(new Dimension(214,420));
        containerPanel.setLayout(new FlowLayout());
        containerPanel.add(listPane);
        containerPanel.add(editorScrollPane);
        mainPanel.add(containerPanel, BorderLayout.NORTH);

        final Box box = Box.createVerticalBox();
        final JPanel searchFieldPane = new JPanel();
        searchFieldPane.setLayout(new FlowLayout());
        searchFieldPane.add(searchField);
        searchField.setPreferredSize(new Dimension(200, 20));

        final JPanel buttonsPane = new JPanel();
        final JButton addButton = new JButton("Добавить");
        final JButton delButton = new JButton("Удалить");
        buttonsPane.setLayout(new FlowLayout());
        buttonsPane.add(addButton);
        buttonsPane.add(delButton);
        box.add(searchFieldPane);
        box.add(buttonsPane);
        mainPanel.add(box, BorderLayout.SOUTH);

        DefaultStyledDocument doc = new DefaultStyledDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(Dictionary.MAX_SYMBOLS));
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchFieldHandler(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchFieldHandler(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        searchField.setDocument(doc);

        addButton.addActionListener((e) -> {
            JDialog dialog = new TranslatorFrame(this);
            dialog.setVisible(true);
        });
        delButton.addActionListener((e) -> {
            if (listModel.getSize() == 0) return;

            int index = listView.getSelectedIndex();
            listModel.removeElementAtIndex(index);
            listView.setSelectedIndex(index);

            if (index == 0 && listModel.getSize() != 0) {
                listView.setSelectedIndex(0);
                showWordInfo();
            } else if(index > 0) {
                listView.setSelectedIndex(--index);
                showWordInfo();
            } else {
                editorPane.setText("");
            }
        });

        listView.addListSelectionListener((e) -> showWordInfo());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                WordStorage.save(listModel, words);
            }
        });

    }

    public MainFrame() {
        init();
        WordStorage.load(listModel, words);
        listView.setSelectedIndex(0);
    }
}