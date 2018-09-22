package com.swingy.ui.view.map;

import org.newdawn.slick.opengl.Texture;

import static com.swingy.ui.view.map.GameMapGenerator.DRawQuadTexture;
import static com.swingy.ui.view.map.GameMapGenerator.QuickLoad;


public class TileMap {

    private float x;
    private float y;
    private float width;
    private float height;
    private Texture texture;
    private TileType type;

    public TileMap(float x, float y, float width, float height, TileType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
         this.texture = QuickLoad(type.textureName, "PNG");
    }

    public void Draw() {
        DRawQuadTexture(texture, x, y, width, height);
    }

    public String getType() {return type.textureName;}
}
