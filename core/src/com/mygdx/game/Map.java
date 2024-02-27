package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {

    public static final int SIZE_X = 64;
    public static final int SIZE_Y = 36;

    public static final int CELL_SIZE = 20;

    private TextureRegion grassTexture;
    private TextureRegion wallTexture; //стена
    private int obstacles[][]; //препятствие

    public Map(TextureAtlas atlas) {
        this.grassTexture = atlas.findRegion("grass40");
        this.wallTexture = atlas.findRegion("block");
        this.obstacles = new int[SIZE_X][SIZE_Y];

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < 3; j++) {
                this.obstacles[i][SIZE_Y - 1 - j] = 5;
            }
        }
    }

    public void checkWallAndBulletsCollision(Bullet bullet) { // попадание в стену
        int cx = (int) (bullet.getPosition().x / CELL_SIZE);
        int cy = (int) (bullet.getPosition().y / CELL_SIZE);

        if (cx >= 0 && cy >= 0 && cx < SIZE_X && cy <= SIZE_Y) { //проверяем что пуля в пределах карты
            if (obstacles[cx][cy] > 0) {
                obstacles[cx][cy] -= bullet.getDamage();
                bullet.deactivate();
            }
        }
    }

    public boolean isAreaClear(float x, float y, float halfSize) {  //ограничения езды по стенам
        int leftX = (int) ((x - halfSize) / CELL_SIZE); //границы право и лево
        int rightX = (int) ((x + halfSize) / CELL_SIZE);

        int bottomY = (int) ((y - halfSize) / CELL_SIZE); //границы верх и низ
        int topY = (int) ((y + halfSize) / CELL_SIZE);

        if (leftX < 0) {
            leftX = 0;
        }
        if (rightX >= SIZE_X) {
            rightX = SIZE_X - 1;
        }
        if (bottomY < 0) {
            bottomY = 0;
        }
        if (topY >= SIZE_Y) {
            topY = SIZE_Y - 1;
        }

        for (int i = leftX; i <= rightX ; i++) {
            for (int j = bottomY; j <= topY ; j++) {
                if (obstacles [i] [j] > 0) { //если есть стенка то фолс и не можем двигаться туда
                    return false;
                }

            }
            
        }
        return true;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 1280 / 40; i++) {
            for (int j = 0; j < 720 / 40; j++) {
                batch.draw(grassTexture, i * 40, j * 40);
            }
        }

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (obstacles[i][j] > 0) {
                    batch.draw(wallTexture, i * CELL_SIZE, j * CELL_SIZE);
                }
            }
        }
    }
}
