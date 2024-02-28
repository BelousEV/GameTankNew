package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameTanks;
import com.mygdx.game.Weapon;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.TankOwner;

public class BotTank extends Tank {

    Direction preferredDirection; //приоритетный угол бота
    float aiTimer; //таймер для действия
    float aiTimerTo; //будльник для смены действия
    boolean active;
    float pursuitRadius; //радиус когда бот реагирует на персонажа

    Vector3 lastPosition;


    public boolean isActive() {
        return active;
    }

    public BotTank(GameTanks game, TextureAtlas atlas) {
        super(game);
        this.ownerType = TankOwner.AI; //играет бот
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("botTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(500.0f, 500.0f);
        this.lastPosition = new Vector3 (0.0f, 0.0f, 0.0f);
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 3;
        this.hp = this.hpMax; //здоровье максимально (аптечку съели сразу)
        this.aiTimerTo = 3.0f; //ты должен изменить свое действие через 3 сек.
        this.pursuitRadius = 300.0f;
        this.preferredDirection = Direction.UP;
        this.circle = new Circle(position.x, position.y, (width + height) / 2);
    }


    public void activate (float x, float y) {
        hpMax = 3;
        hp = this.hpMax;
        preferredDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
        angle = preferredDirection.getAngle();
        position.set(x,y);
        aiTimer = 0.0f;
        active = true;
    } //кнопка активации бота
    @Override
    public void destroy() {
        active = false;
    }

    public void update(float dt) {
        aiTimer += dt; // таймер тикает в голове со скоростью dt
        if (aiTimer >= aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(3.5f, 6.0f);
            preferredDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
            angle = preferredDirection.getAngle();
        }
        move(preferredDirection, dt);
        float dst = this.position.dst(game.getPlayer().getPosition());
        if (dst < pursuitRadius) { //посчитать расстояние до плеера
            rotateTurretToPoint(game.getPlayer().getPosition().x, game.getPlayer().getPosition().y, dt);
            fire();
        }
        if (Math.abs (position.x - lastPosition.x) < 0.5f && Math.abs (position.y - lastPosition.y) < 0.5f) {  //если бот залип на 0,3 сек сбрасываем таймер и другое направление выбирает
            lastPosition.z += dt;
            if (lastPosition.z > 0.3f) {
                aiTimer += 10.0f;
            }
        } else {
            lastPosition.x = position.x;
            lastPosition.y = position.y;
            lastPosition.z = 0.0f;
        }
        super.update(dt);
    }


}

