package com.github.UniHelper.views.utils;

import com.github.UniHelper.presenters.commands.Command;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

public class RadioButtonBundle implements RadioButtonListener {

    private final ArrayList<Command> onActiveButtonChangedCommands;
    @Getter
    private ArrayList<RadioButton> buttons;
    @Getter
    private RadioButton activeButton;

    public RadioButtonBundle() {
        onActiveButtonChangedCommands = new ArrayList<>();
        buttons = new ArrayList<>();
        initializeButtonsState();
    }

    public RadioButtonBundle(Collection<RadioButton> buttons) {
        onActiveButtonChangedCommands = new ArrayList<>();
        setButtons(buttons);
    }

    public void setButtons(Collection<RadioButton> buttons) {
        this.buttons = new ArrayList<>(buttons);
        initializeButtonsState();
    }

    public void addOnActiveButtonChangedCommand(Command command) {
        onActiveButtonChangedCommands.add(command);
    }

    private void initializeButtonsState() {
        activeButton = null;
        for (RadioButton rb : buttons) {
            rb.setActive(false);
            rb.addListener(this);
        }
    }

    private void onButtonClicked(RadioButton button) {
        setActiveButton(button);
        executeOnActiveButtonChangedCommands();
    }

    private void executeOnActiveButtonChangedCommands() {
        for (Command c : onActiveButtonChangedCommands) {
            c.execute();
        }
    }

    public void setActiveButton(RadioButton button) {
        button.setActive(true);
        activeButton = button;
        for (RadioButton rb : buttons) {
            if (rb != button) {
                rb.setActive(false);
            }
        }
    }

    @Override
    public void handleButtonEvent(RadioButton button) {
        onButtonClicked(button);
    }
}
