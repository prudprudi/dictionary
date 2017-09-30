package com.prudprudi4.dictionary;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class DocumentSizeFilter extends DocumentFilter {
    private final int maxChars;

    public DocumentSizeFilter(int maxChars) {
        this.maxChars = maxChars;
    }
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (fb.getDocument().getLength() + string.length() <= maxChars) {
            super.insertString(fb, offset, string, attr);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (fb.getDocument().getLength() + text.length() <= maxChars) {
            super.insertString(fb, offset, text, attrs);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
