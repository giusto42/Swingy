package com.swingy.ui.controller;

import com.swingy.MainConsolRun;
import com.swingy.db.DbHandler;
import com.swingy.herotype.HeroClass;
import com.swingy.ui.view.HomeActivity;
import com.swingy.ui.view.map.GameMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class HomeActivityController {

    private HomeActivity homeActivity;
    private JButton addHeroBtn;
    private JList heroList;
    private JButton startBtn;
    private JComboBox heroClassSelector;
    private JTextField heroNameText;
    private JComboBox heroSelector;
    private JButton refreshDataBtn;
    private JButton openConsoleBtn;

    private ArrayList<HeroClass> heroExisting = new ArrayList<HeroClass>();
    private DefaultListModel<String> heroModelList = new DefaultListModel<String>();
    private int level = 0;
    private int experience = 0;
    private double attack = 70.00;
    private double armor = 4.00;
    private double hitPoint = 560.00;
    private String artefact = "No";
    private int lastPosition = 0;

    private DbHandler dbHandler = new DbHandler();

    public HomeActivityController() {

        initComponents();
        showHomeActivity();
    }

    public void showHomeActivity() {
        homeActivity.setVisible(true);
    }

    private void initComponents() {

        homeActivity = new HomeActivity();

        addHeroBtn = homeActivity.getAddHeroBtn();
        heroList = homeActivity.getHeroDetails();
        startBtn = homeActivity.getStartBtn();
        heroClassSelector = homeActivity.getHeroClassSelector();
        heroNameText = homeActivity.getHeroNameText();
        JTextArea titleText = homeActivity.getTitleText();
        heroSelector = homeActivity.getHeroSelector();
        refreshDataBtn = homeActivity.getRefreshBtn();
        openConsoleBtn = homeActivity.getOpenConsoleBtn();
        refreshDataBtn.setVisible(false);

        titleText.setEditable(false);
        titleText.setText("Welcome to the Jungle!!");
        titleText.setBackground(null);

        heroClassSelector.addItem("Warrior");
        heroClassSelector.addItem("Assasin");
        heroClassSelector.addItem("Mage");

        heroExisting = dbHandler.readData();

        for (int i = 0; i < heroExisting.size(); i++) {
            heroSelector.addItem(heroExisting.get(i).getName() + " " + heroExisting.get(i).getLevel());
        }
        if (heroExisting.size() != 0) {
            heroSelector.setSelectedIndex(0);
            setModel(0);
        }
        initListeners();
    }

    private void initListeners() {

        heroSelector.addActionListener(e -> {
            if (heroSelector.getItemCount() > 0) setModel(heroSelector.getSelectedIndex());
        });

        heroClassSelector.addActionListener(e -> {
            switch (heroClassSelector.getSelectedIndex()) {
                case 0:
                    attack -= 10.00;
                    armor += 2500.00;
                    hitPoint += 8000.00;
                    break;
                case 1:
                    attack += 100.00;
                    armor += 3.00;
                    hitPoint += 250.00;
                    break;
                case 2:
                    attack += 6000.00;
                    armor -= 20.00;
                    hitPoint += 1000.00;
                    break;
            }
        });

        addHeroBtn.addActionListener(e -> {

            if (heroNameText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(homeActivity, "Insert a hero name!");
            } else {
                HeroClass currentHero = new HeroClass();
                currentHero.setName(heroNameText.getText());
                currentHero.setType(heroClassSelector.getSelectedItem().toString());
                currentHero.setExperience(experience);
                currentHero.setLevel(level);
                currentHero.setAttack(attack);
                currentHero.setArmor(armor);
                currentHero.setHitPoint(hitPoint);
                currentHero.setArtefact(artefact);

                heroExisting.add(currentHero);
                heroSelector.addItem(heroExisting.get(heroExisting.size() - 1).getName() + " " + heroExisting.get(heroExisting.size() - 1).getLevel());
                setModel(heroExisting.size() - 1);
                heroSelector.setSelectedIndex(heroExisting.size() - 1);
                dbHandler.insertDateInTable(heroExisting.size(), currentHero.getName(), currentHero.getType(),
                        currentHero.getLevel(), currentHero.getExperience(),
                        currentHero.getAttack(), currentHero.getArmor(), currentHero.getHitPoint(), currentHero.getArtefact());
            }
            heroNameText.setText("");
        });

        startBtn.addActionListener(e -> {

            if (heroSelector.getItemCount() < 1) {
                JOptionPane.showMessageDialog(homeActivity, "Please insert a hero and select it!");
            } else {
                level = heroExisting.get(heroSelector.getSelectedIndex()).getLevel();
                int mapSize = (level - 1) + 10 - (level % 2);
                GameMap startGame = new GameMap(mapSize, heroExisting.get(heroSelector.getSelectedIndex()), heroSelector.getSelectedIndex() + 1);
                startGame.showMap();
                lastPosition = heroSelector.getSelectedIndex();
                refreshDataBtn.setVisible(true);
            }
        });

        refreshDataBtn.addActionListener((ActionEvent e) -> {

            heroSelector.removeAllItems();
            heroExisting = dbHandler.readData();

            for (int i = 0; i < heroExisting.size(); i++) {
                heroSelector.addItem(heroExisting.get(i).getName() + " " + heroExisting.get(i).getLevel());
            }
            if (heroExisting.size() != 0) {
                heroSelector.setSelectedIndex(lastPosition);
                setModel(lastPosition);
            }
            refreshDataBtn.setVisible(false);
        });

        openConsoleBtn.addActionListener(e -> {
            MainConsolRun console = new MainConsolRun(10, 5, 5);
            JOptionPane.showMessageDialog(homeActivity, "You are about to run the game in console.\nThe main will be blocked till you came back.");
            console.runConsolFromGui();
            JOptionPane.showMessageDialog(homeActivity, "You are back! Welcome!");
        });
    }

    private void setModel(int index) {
        heroModelList.clear();
        heroModelList.addElement("Hero Name: " + heroExisting.get(index).getName());
        heroModelList.addElement("Hero Class: " + heroExisting.get(index).getType());
        heroModelList.addElement("Hero level: " + heroExisting.get(index).getLevel());
        heroModelList.addElement("Hero Experience: " + heroExisting.get(index).getExperience());
        heroModelList.addElement("Hero Attack: " + heroExisting.get(index).getAttack());
        heroModelList.addElement("Hero Armor: " + heroExisting.get(index).getArmor());
        heroModelList.addElement("Hero HitPoint: " + heroExisting.get(index).getHitPoint());
        heroModelList.addElement("Artefact: " + heroExisting.get(index).getArtefact());
        heroList.setModel(heroModelList);
    }
}