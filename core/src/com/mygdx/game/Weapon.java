package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
//оружие

public class Weapon {

    private Texture texture;
    private float firePeriod;

    public Texture getTexture() {
        return texture;
    }

    public float getFirePeriod() {
        return firePeriod;
    }

    public int getDamage() {
        return damage;
    }

    private int damage; // урон

    public Weapon() {
        this.texture = new Texture("simple_weapon.png");
        this.firePeriod = 0.2f;
        this.damage = 1;
    }


}


