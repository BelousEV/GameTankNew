package com.mygdx.game.utils;

import static java.lang.Math.abs;

public class Utils {
    public static final float pi = 90;
    public static final float pi2 = 180;
    public static final float piD2 = 45;
    public static final float pi3D2 = 270;


    public static float getAngle(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float) Math.toDegrees((float) Math.atan2(dy, dx));
    }

    public static float makeRotation(float angle, float angleTo, float rotationSpeed, float dt) { //поворачиваем пушку с указ.скоростью
        if (angle < angleTo) { //если после вычитания наш угол меньше угла, куда нам надо, и меньше 180 гр. то крутим в плюс по оси пушку
            if (Math.abs(angle - angleTo) < 180) {
                angle += rotationSpeed * dt;
            } else { // если нет, то вминус
                angle -= rotationSpeed * dt;
            }
        }
        if (angle > angleTo) { //если после вычитания угол больше угла куда нам надо и меньше 180 гр то крутим пушку против оси
            if (Math.abs(angle - angleTo) < 180){
                angle -=rotationSpeed * dt;
            } else  {
                angle += rotationSpeed * dt;
            }
        }
        if (abs(angle - angleTo) < 1.5f * rotationSpeed * dt) { //если разница между углами небольшая, то устанавливаем его туда
            angle = angleTo;
        }
        return angle;
    }


    public static float angleToFromNegPiToPosPi (float ang) { //метод удерживает угол в пределах от - пи до пи
        while (ang < -180|| ang > 180) {
            if (ang > 180) {
                ang -= 360;

            }
            if (ang < -180) {
                ang += 360;
            }
        }
        return ang;
    }


}


