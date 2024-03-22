package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemsEmitter { //управление
    private Item [] items;
    private TextureRegion [][] regions;

    public Item[] getItems() {
        return items;
    }

    public ItemsEmitter (TextureAtlas atlas){
        items = new Item[50];
        for (int i = 0; i < items.length; i++) {
            items [i] = new Item();
            
        }
        regions = new TextureRegion(atlas.findRegion("powerUps")).split(32,32);
    }

    public void render (SpriteBatch batch){
        for (int i = 0; i < items.length; i++) {
            if (items[i].isActive()) {
                int frameIndex = (int) (items[i].getTime() / 0.2f) % regions[items[i].getType().index].length;
                batch.draw(regions[items[i].getType().index][frameIndex], items[i].getPosition().x - 16, items[i].getPosition().y - 16 );
            }
            
        }
    }

}
