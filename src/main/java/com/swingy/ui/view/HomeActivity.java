package com.swingy.ui.view;

import javax.swing.*;

public class HomeActivity extends JFrame {

    private JPanel mainActivity;
    private JButton addHeroBtn;
    private JList heroDetails;
    private JButton startBtn;
    private JComboBox heroClassSelector;
    private JTextField heroNameText;
    private JTextArea titleText;
    private JComboBox heroSelector;
    private JButton refreshBtn;

    private JButton openConsoleBtn;

    public HomeActivity() {
        super("Hero Selector");
        setSize(860, 390);
        setContentPane(mainActivity);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JButton getOpenConsoleBtn() {
        return openConsoleBtn;
    }

    public JButton getRefreshBtn() {
        return refreshBtn;
    }

    public JButton getAddHeroBtn() {
        return addHeroBtn;
    }

    public JList getHeroDetails() {
        return heroDetails;
    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JComboBox getHeroClassSelector() {
        return heroClassSelector;
    }

    public JTextField getHeroNameText() {
        return heroNameText;
    }

    public JTextArea getTitleText() {
        return titleText;
    }

    public JComboBox getHeroSelector() {
        return heroSelector;
    }
}
