package com.github.UniHelper.views.utils.categorySelectorPanel;

import com.github.UniHelper.views.utils.NamedButton;

import javax.swing.border.LineBorder;
import java.awt.*;

public class EditCategoryButton extends NamedButton {

    public EditCategoryButton() {
        super("Edit color");
        setPreferredSize(new Dimension(150, 62));
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        setBackground(Color.DARK_GRAY.darker().darker());
        setBorder(new LineBorder(Color.BLACK, 1));
    }
}
