package com.prudprudi4.dictionary.frame;

import com.prudprudi4.dictionary.SortedListModel;
import com.prudprudi4.dictionary.WordEntity;
import com.prudprudi4.dictionary.util.Disposition;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final static String TITLE = "Dictionary";
    private final static int WIDTH = 450;
    private final static int HEIGHT = 500;

    private final Map<String, WordEntity> words = new HashMap<>();
    private final SortedListModel<String> listModel = new SortedListModel<>();

    private final JList<String> listView = new JList<>(listModel);
    private final JPanel mainPanel = new JPanel();

    public Map<String, WordEntity> getWords() {
        return words;
    }

    public SortedListModel<String> getListModel() {
        return listModel;
    }

    public JList<String> getListView() {
        return listView;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        getContentPane().add(mainPanel);
        setLocation(Disposition.getWindowCenterPos(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BorderLayout());

        listModel.addElement("aaaa");
        listModel.addElement("bbbb");
        listModel.addElement("cccc");
        listModel.addElement("dddd");
        listModel.addElement("eeee");
        listModel.addElement("ffff");
        listView.setSelectedIndex(0);

        final JTextArea tArea = new JTextArea();
        tArea.setEditable(false);
        final JPanel panel = new JPanel();
        final JScrollPane listPane = new JScrollPane(listView);
        final JScrollPane tAreaPane = new JScrollPane(tArea);
        listPane.setPreferredSize(new Dimension(215,420));
        tAreaPane.setPreferredSize(new Dimension(215,420));
        panel.setLayout(new FlowLayout());
        panel.add(listPane);
        panel.add(tAreaPane);
        mainPanel.add(panel, BorderLayout.NORTH);

        final JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new FlowLayout());
        final JButton addButton = new JButton("Добавить");
        final JButton delButton = new JButton("Удалить");
        buttonsPane.add(addButton);
        buttonsPane.add(delButton);
        mainPanel.add(buttonsPane, BorderLayout.SOUTH);

        addButton.addActionListener((e) -> new TranslatorFrame(this).setVisible(true));

        listView.addListSelectionListener((e) -> {
            if(e.getValueIsAdjusting()) {
                int selectedIndex = listView.getSelectedIndex();
                listView.setSelectionInterval(selectedIndex, selectedIndex);
            }
        });
    }

    public MainFrame() {
        init();
    }
}