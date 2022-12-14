package com.github.UniHelper.views.notes.note;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TitleTextPane extends JTextPane {

    private final int maxNumberOfCharacters;

    public TitleTextPane() {
        super();
        maxNumberOfCharacters = 20;
        setPreferredSize(new Dimension(280, 50));
        setBackground(Color.BLACK);
        setTextLook();
    }

    private void setTextLook() {
        StyledDocument styledDocument = getStyledDocumentWithLimitedCharacters();
        setStyledDocument(styledDocument);
        setText("New note");
        SimpleAttributeSet align = new SimpleAttributeSet();
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), align, false);
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        setEditable(false);
    }

    private StyledDocument getStyledDocumentWithLimitedCharacters() {
        return new DefaultStyledDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (isInsertionValid(getLength(), str))
                    super.insertString(offs, str, a);
            }
        };
    }

    private boolean isInsertionValid(int currentTextLength, String insertedString) {
        int newLength = currentTextLength + insertedString.length();
        return (newLength <= maxNumberOfCharacters) && doesNotContainNewLine(insertedString);
    }

    private boolean doesNotContainNewLine(String s) {
        return !(s.contains("\n"));
    }
}
