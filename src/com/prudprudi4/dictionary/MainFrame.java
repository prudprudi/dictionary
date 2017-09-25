package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.util.Disposition;
import com.prudprudi4.dictionary.util.Translator;
import javafx.scene.layout.Border;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainFrame extends BaseFrame {

    protected void init() {
        mainPanel.setLayout(new BorderLayout());

        final DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> jList = new JList<>(model);
        jList.setModel(model);
        model.addElement("abcde");
        model.addElement("fghij");
        model.addElement("abcde");
        model.addElement("fghij");
        model.addElement("abcde");
        model.addElement("fghij");
        jList.setSelectedIndex(0);

        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        final JPanel panel = new JPanel();
        final JScrollPane sPane1 = new JScrollPane(jList);
        final JScrollPane sPane2 = new JScrollPane(textArea);
        sPane1.setPreferredSize(new Dimension(215,420));
        sPane2.setPreferredSize(new Dimension(215,420));
        panel.setLayout(new FlowLayout());
        panel.add(sPane1);
        panel.add(sPane2);
        mainPanel.add(panel, BorderLayout.NORTH);

        final JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new FlowLayout());

        final JButton addButton = new JButton("Добавить");
        final JButton delButton = new JButton("Удалить");
        buttonsPane.add(addButton);
        buttonsPane.add(delButton);
        mainPanel.add(buttonsPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addButton.addActionListener((e) -> new TranslatorFrame(this));

        jList.addListSelectionListener((e) -> {
            if(e.getValueIsAdjusting()) {
                int selectIndex = jList.getSelectedIndex();
                jList.clearSelection();
                jList.setSelectedIndex(0);
            }
        });
    }

    MainFrame() {
        super("Dictionary", 450, 500);
        init();
        setVisible(true);
    }
}