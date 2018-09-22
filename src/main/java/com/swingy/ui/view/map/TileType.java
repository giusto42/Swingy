package com.swingy.ui.view.map;

public enum TileType {

    Grass("grass", true), Hero("hero", true), Enemy("enemy", true);

    String textureName;
    Boolean buildable;

    TileType(String textureName, boolean buildable) {

        this.textureName = textureName;
        this.buildable = buildable;
    }
}
