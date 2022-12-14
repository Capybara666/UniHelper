package com.github.UniHelper.views.mainWindow;

import com.github.UniHelper.presenters.commands.Command;
import com.github.UniHelper.views.FeatureView;
import com.github.UniHelper.views.utils.NamedButton;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class DefaultMainWindowView implements MainWindowView {

    private final MainFrame mainFrame;
    private final SideMenuPanel sideMenuPanel;
    private final ActiveFeaturePanel activeFeaturePanel;
    private final ArrayList<Command> onCloseCommands;
    private final ArrayList<Command> onFeatureChangeCommands;

    public DefaultMainWindowView() {
        mainFrame = new MainFrame();
        sideMenuPanel = new SideMenuPanel();
        activeFeaturePanel = new ActiveFeaturePanel();
        onCloseCommands = new ArrayList<>();
        onFeatureChangeCommands = new ArrayList<>();
        assembleView();
    }

    @Override
    public void show() {
        mainFrame.setVisible(true);
    }

    @Override
    public void addFeatureView(FeatureView featureView) {
        addFeatureToMenu(featureView);
        activeFeaturePanel.addFeaturePanel(featureView);
    }

    @Override
    public void addOnCloseCommand(Command command) {
        onCloseCommands.add(command);
    }

    @Override
    public void addOnFeatureChangeCommand(Command command) {
        onFeatureChangeCommands.add(command);
    }

    @Override
    public Dimension getFeaturePanelSize() {
        return activeFeaturePanel.getPreferredSize();
    }

    @Override
    public void showFeature(String featureName) {
        executeOnFeatureChangeCommands();
        this.activeFeaturePanel.chooseView(featureName);
    }

    private void assembleView() {
        mainFrame.add(sideMenuPanel, BorderLayout.WEST);
        mainFrame.add(activeFeaturePanel, BorderLayout.CENTER);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                executeOnCloseCommands();
            }
        });
    }

    private void addFeatureToMenu(FeatureView featureView) {
        String featureName = featureView.getFeatureName();
        NamedButton featureButton = new NamedButton(featureName);
        featureButton.addCommand(() -> showFeature(featureName));
        sideMenuPanel.addButton(featureButton);
    }

    private void executeOnCloseCommands() {
        for (Command c : onCloseCommands) {
            c.execute();
        }
    }

    private void executeOnFeatureChangeCommands() {
        for (Command c : onFeatureChangeCommands) {
            c.execute();
        }
    }
}
