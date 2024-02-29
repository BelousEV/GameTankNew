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
        ScreenManager.getInstance().init(this, batch);
        ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME); //переход на экран игры


    }

    @Override
    public void render() { //метод отвечает за всю отрисовку
        float dt = Gdx.graphics.getDeltaTime();//дельтатайм// спрашиваем у видеокарты сколько времени прошло от предыдущей отрисовки
        getScreen().render(dt); //запрашиваем ссылку на текущий экран активен
    }




    @Override
    public void dispose() { //метод освобождения ресурсов
        batch.dispose();

    }

}
