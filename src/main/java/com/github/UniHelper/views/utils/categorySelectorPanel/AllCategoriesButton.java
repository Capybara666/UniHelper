package com.github.UniHelper.views.utils.categorySelectorPanel;

import com.github.UniHelper.views.utils.RadioButton;

import javax.swing.*;
import java.awt.*;

public class AllCategoriesButton extends RadioButton {

    public AllCategoriesButton() {
        setPreferredSize(new Dimension(120, 62));
        FlowLayout fl = new FlowLayout();
        fl.setVgap(13);
        fl.setHgap(20);
        setLayout(fl);
        JLabel textLabel = new JLabel("All");
        textLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        add(textLabel);
    }
}
