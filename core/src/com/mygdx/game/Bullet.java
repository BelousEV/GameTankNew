package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.units.Tank;

public class Bullet {

    private Tank owner;
    private Vector2 position; //позиция х у
    private Vector2 velocity; // скорость (без  ускорения)
    private float speed;
    private boolean active; // может быть включена или выключена
    private  int damage; //урон



    public boolean isActive() {
        return active;
    }

    public int getDamage() {
        return damage;
    }

    public Vector2 getPosition() {
        return position;
    }
    public Tank getOwner() {
        return owner;
    }

    public Bullet() {

        this.position = new Vector2();
        this.velocity = new Vector2();
        this.active = false;
        this.damage = 0;
    }



    public void activate (float x, float y, float vx, float vy, int damage){
        this.active = true;
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.damage = damage;
    }

    public void deactivate(){
        active = false;
    }

    public void update (float dt){
        position.mulAdd(velocity, dt); //складываем два вектора
        if (position.x < 0.0f || position.x > 1280f || position.y < 0.0f || position.y > 720.f) {
            deactivate();
        }


    }


}
