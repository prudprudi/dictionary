package com.prudprudi4.dictionary;

import com.prudprudi4.dictionary.util.Disposition;

import javax.swing.*;
import java.awt.*;

public abstract class BaseFrame extends JFrame {
    protected String title;
    protected int width;
    protected int height;
    protected JPanel mainPanel = new JPanel();

    protected abstract void init();

    private void setPosition() {
        Point centerPos = Disposition.getWindowCenterPos(width, height);
        setBounds(centerPos.x, centerPos.y, width, height);
    }

    BaseFrame(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        setPosition();
        setTitle(title);
        setResizable(false);
        getContentPane().add(mainPanel);
    }
}
