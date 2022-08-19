package com.github.UniHelper.views.notes.notesOptionsPanel;

import com.github.UniHelper.views.utils.NamedButton;

import javax.swing.border.LineBorder;
import java.awt.*;

public class NotesEditCategoryButton extends NamedButton {

    public NotesEditCategoryButton() {
        super("Edit color");
        setPreferredSize(new Dimension(150,62));
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        setBackground(Color.DARK_GRAY.darker().darker());
        setBorder(new LineBorder(Color.BLACK, 1));
    }
}
