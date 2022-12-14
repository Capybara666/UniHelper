package com.github.UniHelper.views.timetable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    private final JLabel imageContainerLabel;

    public ImagePanel() {
        super();
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY.darker());
        imageContainerLabel = new JLabel();
        add(imageContainerLabel, BorderLayout.CENTER);
    }

    public void setImage(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        imageContainerLabel.setIcon(icon);
        revalidate();
    }

    public void clearImage() {
        imageContainerLabel.setIcon(null);
    }
}
