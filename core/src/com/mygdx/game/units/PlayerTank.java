package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGameTank;
import com.mygdx.game.Weapon;

public class PlayerTank extends Tank {
    public PlayerTank(MyGdxGameTank game) {
        super(game);

        this.game = game;
        this.weapon = new Weapon();
        this.texture = new Texture("player_tank_base.png");

        this.position = new Vector2(100.f, 100.0f);
        this.speed = 100.0f;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.hpMax = 10;
        this.hp = this.hpMax; //здоровье максимально (аптечку съели сразу)
    }

    public void checkMoment(float dt) { //метод отвечает за движения
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= speed * dt;
            angle = 180.f;
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += speed * dt;
            angle = 0;
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += speed * dt;
            angle = 90.0f;
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= speed * dt;
            angle = 270.0f;
            return;
        }
    }

    public void update(float dt) {
        checkMoment(dt);
        float mx = Gdx.input.getX();
        float my = Gdx.graphics.getHeight() - Gdx.input.getY();
        rotateTurretToPoint(mx, my, dt);

        if (Gdx.input.isTouched()) { //нажатие в данный момент (мышка)
            fire(dt);
        }
    }
}
