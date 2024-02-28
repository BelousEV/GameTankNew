package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.units.BotTank;
import com.mygdx.game.units.PlayerTank;
import com.mygdx.game.units.Tank;

public class GameTanks extends Game {
    private SpriteBatch batch; //вся игровая область (хотим что то нарисовать, обращаемся к batch)


    @Override
    public void create() { //метод отвечает за запуск приложения с начальной подготовкой
        batch = new SpriteBatch();


    }

    @Override
    public void render() { //метод отвечает за всю отрисовку
        float dt = Gdx.graphics.getDeltaTime();//дельтатайм// спрашиваем у видеокарты сколько времени прошло от предыдущей отрисовки
        getScreen().render(dt); //запрашиваем ссылку на текущий экран активен
    }



    public void checkCollisions() { // наносим урон
        for (int i = 0; i < bulletEmitter.getBullets().length; i++) {
            Bullet bullet = bulletEmitter.getBullets()[i];
            if (bullet.isActive()) {
                for (int j = 0; j < botEmitter.getBots().length; j++) {
                    BotTank bot = botEmitter.getBots()[j];
                    if (bot.isActive()) {
                        if (checkBulletAndTank(bot, bullet) && bot.getCircle().contains(bullet.getPosition())) { // если столкнулись проверяем столкновение
                            bullet.deactivate(); //если нет, то и не проверяем
                            bot.takeDamage(bullet.getDamage());
                            break; //одна пуля попадет в один танк
                        }
                    }
                }

                if (checkBulletAndTank(player, bullet) && player.getCircle().contains(bullet.getPosition())) { //проверка с плеером
                    bullet.deactivate();
                    player.takeDamage(bullet.getDamage());
                }

                map.checkWallAndBulletsCollision(bullet); //проверка что пуля влетела в стену
            }
        }
    }

    public boolean checkBulletAndTank(Tank tank, Bullet bullet) { //настройка дружественного огня
        if (!FIRENDLY_FIRE) { //выключен, то
            return tank.getOwnerType() != bullet.getOwner().getOwnerType(); //стреляем только по танку ппротиволожного типа
        } else { //включен
            return tank != bullet.getOwner(); //танк не должен попадать по любому танку (владелец пули не должен совпадать с танком тогда попадаем
        }
    }

    @Override
    public void dispose() { //метод освобождения ресурсов
        batch.dispose();

    }

}
