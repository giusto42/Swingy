package com.swingy.ui.view.map;

import com.swingy.MainConsolRun;
import com.swingy.db.DbHandler;
import com.swingy.herotype.HeroClass;
import com.swingy.ui.controller.HeroPosition;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class TileGridMap {

    private TileMap[][] map;
    private int heroPositionX;
    private int heroPositionY;
    private int mapSize;
    private int enemyCount = 0;
    private int id;
    private HeroClass currentHero;
    private DbHandler dbHandler = new DbHandler();
    private boolean fightTime = false;
    private HeroPosition heroPosition;

    public TileGridMap(int sizeMap, HeroClass hero, int id) {
        this.id = id;
        mapSize = sizeMap;
        currentHero = hero;
        heroPositionX = sizeMap / 2;
        heroPositionY = sizeMap / 2;

        map = new TileMap[sizeMap][sizeMap];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new TileMap(i * 36, j * 36, 36, 36, TileType.Grass);
            }
        }

        map[heroPositionX][heroPositionY] = new TileMap(heroPositionX * 36, heroPositionY * 36, 36, 36, TileType.Hero);

        for (int i = 1; i <= 3 * sizeMap / 2; i++) {
            enemyCount += 1;
            int enemyX = ThreadLocalRandom.current().nextInt(0, sizeMap);
            int enemyY = ThreadLocalRandom.current().nextInt(0, sizeMap);
            if (map[enemyX][enemyY].getType().equals(TileType.Grass.textureName)) {
                map[enemyX][enemyY] = new TileMap(enemyX * 36, enemyY * 36, 36, 36, TileType.Enemy);
            } else if (i > 2) i--;
        }
    }

    public void DrawMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                TileMap tile = map[i][j];
                tile.Draw();
            }
        }
    }

    public void heroMove(String position) {

        switch (position) {
            case "up":
                if (isPossible(heroPositionX, heroPositionY - 1)) {
                    if (isFight(heroPositionX, heroPositionY - 1)) {
                        switch (showPopUp()) {
                            case 1:
                                fight(heroPositionX, heroPositionY - 1);
                                break;
                            case 2:
                                generateConsole();
                                break;
                            case 0:
                                if (isReallyFight()) {
                                    Sys.alert("!!You can't run!!", "DrawRanger: You can't RUN from me stranger!");
                                    fight(heroPositionX, heroPositionY - 1);
                                }
                                break;
                        }
                    } else {
                        generateNewPosition(heroPositionX, heroPositionY, TileType.Grass);
                        heroPositionY -= 1;
                        generateNewPosition(heroPositionX, heroPositionY, TileType.Hero);
                    }
                }
                break;
            case "down":
                if (isPossible(heroPositionX, heroPositionY + 1)) {
                    if (isFight(heroPositionX, heroPositionY + 1)) {
                        switch (showPopUp()) {
                            case 1:
                                fight(heroPositionX, heroPositionY + 1);
                                break;
                            case 2:
                                generateConsole();
                                break;
                            case 0:
                                if (isReallyFight()) {
                                    Sys.alert("!!You can't run!!", "DrawRanger: You can't RUN from me stranger!");
                                    fight(heroPositionX, heroPositionY + 1);
                                }
                                break;
                        }
                    } else {
                        generateNewPosition(heroPositionX, heroPositionY, TileType.Grass);
                        heroPositionY += 1;
                        generateNewPosition(heroPositionX, heroPositionY, TileType.Hero);
                    }
                }
                break;
            case "left":
                if (isPossible(heroPositionX - 1, heroPositionY)) {
                    if (isFight(heroPositionX - 1, heroPositionY)) {
                        switch (showPopUp()) {
                            case 1:
                                fight(heroPositionX - 1, heroPositionY);
                                break;
                            case 2:
                                generateConsole();
                                break;
                            case 0:
                                if (isReallyFight()) {
                                    Sys.alert("!!You can't run!!", "DrawRanger: You can't RUN from me stranger!");
                                    fight(heroPositionX - 1, heroPositionY);
                                }
                                break;
                        }
                    } else {
                        generateNewPosition(heroPositionX, heroPositionY, TileType.Grass);
                        heroPositionX -= 1;
                        generateNewPosition(heroPositionX, heroPositionY, TileType.Hero);
                    }
                }
                break;
            case "right":
                if (isPossible(heroPositionX + 1, heroPositionY)) {
                    if (isFight(heroPositionX + 1, heroPositionY)) {
                        switch (showPopUp()) {
                            case 1:
                                fight(heroPositionX + 1, heroPositionY);
                                break;
                            case 2:
                                generateConsole();
                                break;
                            case 0:
                                if (isReallyFight()) {
                                    Sys.alert("!!You can't run!!", "DrawRanger: You can't RUN from me stranger!");
                                    fight(heroPositionX + 1, heroPositionY);
                                }
                                break;
                        }
                    } else {
                        generateNewPosition(heroPositionX, heroPositionY, TileType.Grass);
                        heroPositionX += 1;
                        generateNewPosition(heroPositionX, heroPositionY, TileType.Hero);
                    }
                    break;
                }
        }
    }

    private boolean isPossible(int x, int y) {
        return (x >= 0 && y >= 0 && x < map.length && y < map.length);
    }

    private boolean isFight(int x, int y) {
        return map[x][y].getType().equals("enemy");
    }

    private boolean isReallyFight() {
        return ThreadLocalRandom.current().nextInt(1, 3) == 1;
    }

    private boolean isArtefact() {
        return ThreadLocalRandom.current().nextInt(0, 3 + 1) == 3;
    }

    private void generateNewPosition(int x, int y, TileType type) {
        map[x][y] = new TileMap(heroPositionX * 36, heroPositionY * 36, 36, 36, type);
        map[x][y].Draw();
    }

    private void generateConsole() {
        MainConsolRun console = new MainConsolRun(mapSize, heroPositionX, heroPositionY);
        Sys.alert("!!Run in Console!!", "Main is blocked till console is running!");
        heroPosition = console.runConsolFromGui();
        Sys.alert("!!Run in Console Finish!!", "Now you are back here!");
        generateNewPosition(heroPositionX, heroPositionY, TileType.Grass);
        heroPositionX = heroPosition.getX();
        heroPositionY = heroPosition.getY();
        generateNewPosition(heroPositionX, heroPositionY, TileType.Hero);
    }

    private int showPopUp() {
        String[] options = new String[]{"Run", "Fight", "Open in console"};
        int response = JOptionPane.showOptionDialog(null, "You can fight or you can run( 50% to fight)", "Ready to fight?",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        return response;
    }

    private void fight(int x, int y) {
        fightTime = false;
        Sys.alert("Fight!", "You going to start a fight with a DrawRanger! Good Luck!");
        int winChance = ThreadLocalRandom.current().nextInt(0, 11);
        if (winChance <= 3) {
            Sys.alert("Fight Lose :( !", "You lose your fight, sorryyy!! :( :(");
            generateNewPosition(heroPositionX, heroPositionY, TileType.Grass);
            heroPositionX = mapSize / 2;
            heroPositionY = mapSize / 2;
            generateNewPosition(heroPositionX, heroPositionY, TileType.Hero);
        } else {
            Sys.alert("Fight WIN !!", "Congratulations, you WIN your FIGHT! \n You are a REAL FIGHTER! GOOD JOB!!");
            currentHero.setExperience(currentHero.getExperience() + 450);

            if ((currentHero.getLevel() * 1000 + (currentHero.getLevel() - 1) * (currentHero.getLevel() - 1) * 450) <= currentHero.getExperience()) {
                Sys.alert("Level UP!", "Congratulations, you level UP!");
                currentHero.setLevel(currentHero.getLevel() + 1);
                dbHandler.updateData(id, currentHero.getLevel(), currentHero.getExperience());
            }
            if (isArtefact()) {
                switch (ThreadLocalRandom.current().nextInt(0, 3 + 1)) {
                    case 1:
                        Sys.alert("Artefact Dropt!", "Attack + 60");
                        dbHandler.updateData(id, "weapon", currentHero.getAttack() + 60);
                        break;
                    case 2:
                        Sys.alert("Artefact Dropt!", "Armor + 5");
                        dbHandler.updateData(id, "armor", currentHero.getArmor() + 5);
                        break;
                    case 3:
                        Sys.alert("Artefact Dropt!", "HitPoint + 1000");
                        dbHandler.updateData(id, "helmet", currentHero.getHitPoint() + 1000);
                        break;
                }
            }
            generateNewPosition(heroPositionX, heroPositionY, TileType.Grass);
            heroPositionX = x;
            heroPositionY = y;
            generateNewPosition(heroPositionX, heroPositionY, TileType.Hero);
            enemyCount -= 1;
            if (enemyCount == 0) {
                Sys.alert("WINNER!!", "Congratulations, you win the game! \n Winner winner chicken diner!");
                Display.destroy();
            }
        }
    }
}

