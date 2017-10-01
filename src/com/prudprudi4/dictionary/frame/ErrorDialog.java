package com.prudprudi4.dictionary.frame;

import com.prudprudi4.dictionary.util.Disposition;

import javax.swing.*;

public class ErrorDialog extends JDialog {
    private final String TITLE = "Error";
    private final static int WIDTH = 300;
    private final static int HEIGHT = 150;

    public ErrorDialog(String msg) {
        super();
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setModal(true);
        setResizable(false);
        setLocation(Disposition.getWindowCenterPos(WIDTH, HEIGHT));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        textArea.setText(msg);
        getContentPane().add(scrollPane);
        setVisible(true);
    }
}
