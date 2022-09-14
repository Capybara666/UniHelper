package com.github.UniHelper.views.notes.note;

import com.github.UniHelper.views.utils.ColorPalette;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseWheelListener;

public class TextPanel extends JPanel {

    private final JTextArea text;
    private final JScrollPane scrollPane;

    public TextPanel() {
        super();
        setLayout(new GridLayout());
        text = new JTextArea();
        scrollPane = new JScrollPane(text);
        setTextLook();
        configureScrollbar();
        add(scrollPane);
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
        this.text.setCaretPosition(0);
    }

    public void addTextDocumentListener(DocumentListener documentListener) {
        text.getDocument().addDocumentListener(documentListener);
    }

    public Color getTextBackground() {
        return text.getBackground();
    }

    public void setTextBackground(Color color) {
        text.setBackground(color);
        scrollPane.getVerticalScrollBar().setBackground(color);
    }

    public void setEditable(boolean editable) {
        text.setEditable(editable);
    }

    private void setTextLook() {
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setBackground(ColorPalette.BLUE);
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        text.setEditable(false);
    }

    private void configureScrollbar() {
        MouseWheelListener defaultListener = scrollPane.getMouseWheelListeners()[0];
        scrollPane.removeMouseWheelListener(defaultListener);
    }
}
