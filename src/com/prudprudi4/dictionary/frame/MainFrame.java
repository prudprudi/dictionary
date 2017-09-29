package com.prudprudi4.dictionary.frame;

import com.prudprudi4.dictionary.Dictionary;
import com.prudprudi4.dictionary.DocumentSizeFilter;
import com.prudprudi4.dictionary.SortedListModel;
import com.prudprudi4.dictionary.WordEntity;
import com.prudprudi4.dictionary.util.TextFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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

    public Map<String, WordEntity> getWords() {
        return words;
    }
    public SortedListModel getListModel() {
        return listModel;
    }

    public JList<String> getListView() {
        return listView;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void loadWords() {
        File file = new File(Dictionary.filePath);
        BufferedReader br = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            br = new BufferedReader(new FileReader(file));

            JSONParser parser = new JSONParser();
            JSONObject obj;
            JSONArray arr;
            String line;
            String[] strs;
            WordEntity wEntity;

            while((line = br.readLine()) != null) {
                strs = line.split("=", 2);
                listModel.addElement(strs[0]);
                obj = (JSONObject) parser.parse(strs[1]);
                wEntity = new WordEntity(obj);
                words.put(strs[0], wEntity);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        getContentPane().add(mainPanel);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Point p = new Point(d.width / 2 - WIDTH / 2, d.height / 2 - HEIGHT / 2);

        setLocation(p);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        listView.addListSelectionListener((e) -> {
            showWordInfo();
        });

    }

    protected void showWordInfo() {
        int index = listView.getSelectedIndex();

        if (index == -1) return;
        listView.setSelectionInterval(index, index);

        String text = listModel.getElementAt(index);
        WordEntity wEntity = words.get(text);
        text = TextFormatter.format(wEntity);
        editorPane.setText(text);
    }

    private void searchFieldHandler(DocumentEvent e) {
        String text = searchField.getText();
        int index = listModel.getIndexBySubstring(text);

        if(index != -1) {
            listView.setSelectedIndex(index);
        }
    }

    public MainFrame() {
        init();
        loadWords();
        listView.setSelectedIndex(0);
    }
}