package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.units.Tank;

public class BulletEmitter {
    // массив готовых объектов (пуль) вкл и выключенных. задача эмиттера найти неактивную пулю и отдает в игру
    private TextureRegion bulletTexture;
    private Bullet[] bullets;

    public Bullet[] getBullets() {
        return bullets;
    }

    public static final int MAX_BULLETS_COUNT = 500; // 500 пуль всего

    public BulletEmitter(TextureAtlas atlas) {
        this.bulletTexture = atlas.findRegion("projectile");
        this.bullets = new Bullet[MAX_BULLETS_COUNT];
        for (int i = 0; i < bullets.length; i++) {
            this.bullets[i] = new Bullet(); //инициализируем весь массив пуль
        }
    }

    public void activate(float x, float y, float vx, float vy, int damage) {
        for (int i = 0; i < bullets.length; i++) {
            if (!bullets[i].isActive()) { //если пуля неактивна активируем ее с координатами)
                bullets[i].activate(x, y, vx, vy, damage);
                break;
            }
        }
    }
    public void activate(Tank tank) {
    }

    public void render(SpriteBatch batch) { //отрисовка
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                batch.draw(bulletTexture, bullets[i].getPosition().x - 8, bullets[i].getPosition().y - 8);
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) { //если пуля активна апдейтим
                bullets[i].update(dt);
            }
        }
    }
}
