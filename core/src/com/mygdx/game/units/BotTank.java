package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGameTank;
import com.mygdx.game.Weapon;
import com.mygdx.game.utils.Direction;

public class BotTank extends Tank {

    Direction preferredDirection; //приоритетный угол бота
    float aiTimer; //таймер для действия
    float aiTimerTo; //будльник для смены действия
    boolean active;

    public boolean isActive() {
        return active;
    }

    public BotTank(MyGdxGameTank game) {
        super(game);
        // this.game = game;
        this.weapon = new Weapon();
        this.texture = new Texture("bot_tank_base.png");
        this.position = new Vector2(500.f, 500.0f);
        this.speed = 100.0f;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.hpMax = 4;
        this.hp = this.hpMax; //здоровье максимально (аптечку съели сразу)
        this.aiTimerTo = 3.0f; //ты должен изменить свое действие через 3 сек.
        this.preferredDirection = Direction.UP;
    }


    public void activate (float x, float y) {
        hpMax = 4;
        hp = this.hpMax;
        preferredDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
        position.set(x,y);
        aiTimer = 0.0f;
        active = true;
    } //кнопка активации бота

    public void update(float dt) {
        aiTimer += dt; // таймер тикает в голове со скоростью dt
        if (aiTimer >= aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(2.5f, 4f);
            preferredDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)]; //выбирает случ.направл из 4
        }
        position.add(speed * preferredDirection.getVx() * dt, speed * preferredDirection.getVy() * dt); //если у Х=1, а У = 0, то двигаемся вправо
    }


}

