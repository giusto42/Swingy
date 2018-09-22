package com.swingy.ui.view.map;

import com.swingy.herotype.HeroClass;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static com.swingy.ui.view.map.GameMapGenerator.BeginSession;

public class GameMap {

    private int mapSize;
    private int id;
    private HeroClass currentHero;

    public GameMap(int mapSize, HeroClass hero, int id) {
        this.mapSize = mapSize;
        this.currentHero = hero;
        this.id = id;
    }

    public void showMap() {

        BeginSession(mapSize * 36, mapSize * 36);

        TileGridMap map = new TileGridMap(mapSize, currentHero, id);

        while (!Display.isCloseRequested()) {

            map.DrawMap();

            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                map.heroMove("left");

            }
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                map.heroMove("right");
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                map.heroMove("up");

            }
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                map.heroMove("down");

            }
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                Display.destroy();
                break;
            }
            Display.update();
            Display.sync(9);
        }

        Display.destroy();
    }
}