package com.github.UniHelper.views.mainWindow;

import com.github.UniHelper.views.commands.Command;
import com.github.UniHelper.views.utils.ActionButton;

import javax.swing.*;

public class MenuButton extends JButton implements ActionButton {
    private Command command;

    public MenuButton(String name) {
        super(name);
        command = () -> {};
    }

    @Override
    public void setCommand(Command command) {
        removeActionListener(e -> this.command.execute());
        this.command = command;
        addActionListener(e -> command.execute());
    }

    @Override
    public void addCommand(Command command) {
        addActionListener(e -> command.execute());
    }
}
