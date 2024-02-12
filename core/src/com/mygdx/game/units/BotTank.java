package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameTanks;
import com.mygdx.game.Weapon;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.TankOwner;

public class BotTank extends Tank {

    Direction preferredDirection; //приоритетный угол бота
    float aiTimer; //таймер для действия
    float aiTimerTo; //будльник для смены действия
    boolean active;
    float pursuitRadius;


    public boolean isActive() {
        return active;
    }

    public BotTank(GameTanks game, TextureAtlas atlas) {
        super(game);
        this.ownerType = TankOwner.AI;
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("botTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(500.0f, 500.0f);
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
            aiTimerTo = MathUtils.random(2.5f, 4.0f);
            preferredDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
            angle = preferredDirection.getAngle();
        }
        position.add(speed * preferredDirection.getVx() * dt, speed * preferredDirection.getVy() * dt);
        float dst = this.position.dst(game.getPlayer().getPosition());
        if (dst < pursuitRadius) {
            rotateTurretToPoint(game.getPlayer().getPosition().x, game.getPlayer().getPosition().y, dt);
            fire(dt);
        }
        super.update(dt);
    }


}

