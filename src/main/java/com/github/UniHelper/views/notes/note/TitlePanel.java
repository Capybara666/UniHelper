package com.github.UniHelper.views.notes.note;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TitlePanel extends JPanel {

    private final JTextPane titleTextPane;

    public TitlePanel() {
        super();
        titleTextPane = new TitleTextPane();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(0, 50));
        add(titleTextPane);
    }

    public String getTitle() {
        return titleTextPane.getText();
    }

    public void setTitle(String title) {
        titleTextPane.setText(title);
    }

    public void addTitleDocumentListener(DocumentListener documentListener) {
        titleTextPane.getDocument().addDocumentListener(documentListener);
    }

    public void setTitleBackground(Color color) {
        setBackground(color);
        titleTextPane.setBackground(color);
    }

    public void setEditable(boolean editable) {
        titleTextPane.setEditable(editable);
    }
}
