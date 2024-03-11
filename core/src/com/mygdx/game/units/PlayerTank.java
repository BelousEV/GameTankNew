package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.GameTanks;
import com.mygdx.game.ScreenManager;
import com.mygdx.game.Weapon;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.TankOwner;

public class PlayerTank extends Tank {
    int index;
    int score;
    int lifes;

    public PlayerTank(int index,GameScreen game, TextureAtlas atlas) {
        super(game);
        this.index = index;
        this.ownerType = TankOwner.PLAYER; //играет человек
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("playerTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(100.0f, 100.0f);
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 10;
        this.hp = this.hpMax;
        this.circle = new Circle(position.x, position.y, (width + height) / 2);
        this.lifes = 5;
    }

    public void addScore (int amount){
        score +=amount;
    }

    @Override
    public void destroy() {
        lifes--;
        hp = hpMax;
    }

    public void update(float dt) {
        checkMovent(dt);

        ScreenManager.getInstance().getViewport().unproject(tmp);

        rotateTurretToPoint(gameScreen.getMousePosition().x, gameScreen.getMousePosition().y, dt);

        if (Gdx.input.isTouched()) {
            fire();
        }

        super.update(dt);
    }


    public void renderHUD (SpriteBatch batch, BitmapFont font24){
        font24.draw(batch, "Score: " + score + "\nLifes" + lifes, 20, 700);

    }

    public void checkMovent(float dt) { //метод отвечает за движения
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            move(Direction.LEFT, dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            move(Direction.RIGHT,dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            move(Direction.UP, dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move(Direction.DOWN, dt);
        }
    }


}




