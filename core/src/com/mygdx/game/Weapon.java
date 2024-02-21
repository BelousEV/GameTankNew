package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//оружие

public class Weapon { //оружие

    private TextureRegion texture;
    private float firePeriod;
    private int damage; // урон
    private  float radius; //радиус атаки пуль
    private  float projectileSpeed;
    private float projectileLifeTime;

    public TextureRegion getTexture() {
        return texture;
    }

    public float getFirePeriod() {
        return firePeriod;
    }

    public int getDamage() {
        return damage;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public float getProjectileLifeTime() {
        return projectileLifeTime;
    }

    public float getRadius() {
        return radius;
    }

    public Weapon(TextureAtlas atlas) {
        this.texture = atlas.findRegion("simpleWeapon");
        this.firePeriod = 0.2f;
        this.damage = 1;
        this.radius = 300.0f;
        this.projectileSpeed = 320.0f;
        this.projectileLifeTime = this.radius/this.projectileSpeed;
    }


}


