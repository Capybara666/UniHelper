package com.github.UniHelper.views.utils;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

public class RadioButton extends JPanel {

    private final HashSet<RadioButtonListener> listeners;

    @Getter
    private Color defaultColor;
    private Color hoveredColor;
    private Color clickedColor;
    @Getter
    private boolean active;
    @Getter
    private boolean backgroundChangeInternal;

    public RadioButton() {
        super();
        listeners = new HashSet<>();
        active = false;
        backgroundChangeInternal = false;
        initializeMouseListener();
        initializeLook();
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        if (!isBackgroundChangeInternal()) {
            defaultColor = color;
            hoveredColor = color.brighter();
            clickedColor = color.brighter().brighter();
        }
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
        if (isActive) {
            setBackgroundOnMouseEvent(clickedColor);
        } else {
            setBackgroundOnMouseEvent(defaultColor);
        }
    }

    public void addListener(RadioButtonListener listener) {
        listeners.add(listener);
    }

    private void initializeLook() {
        setBorder(new LineBorder(Color.BLACK, 1));
        setBackground(Color.DARK_GRAY.darker().darker());
    }

    private void initializeMouseListener() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(final MouseEvent e) {
                if (!active) {
                    setActive(true);
                    notifyListeners();
                    setBackgroundOnMouseEvent(clickedColor);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!active) {
                    setBackgroundOnMouseEvent(hoveredColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!active) {
                    setBackgroundOnMouseEvent(defaultColor);
                }
            }
        });
    }

    private void notifyListeners() {
        for (RadioButtonListener rbl : listeners) {
            if (rbl == null) {
                listeners.remove(null);
            } else {
                rbl.handleButtonEvent(this);
            }
        }
    }

    private void setBackgroundOnMouseEvent(Color color) {
        backgroundChangeInternal = true;
        setBackground(color);
        backgroundChangeInternal = false;
    }
}
