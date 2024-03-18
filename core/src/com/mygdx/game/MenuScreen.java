package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
import com.mygdx.game.utils.KeysControl;

import java.util.ArrayList;
import java.util.List;

public class MenuScreen extends AbstractScreen {
    private SpriteBatch batch;
    private TextureAtlas atlas;

    private BitmapFont font24; //шрифт

    private Stage stage; //делаем кнопки и интерфейс


    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;

    }


    @Override
    public void show() { //когда экран показали
        atlas = new TextureAtlas("game.pack");
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
        stage = new Stage();


        //делаем кнопки
        Skin skin = new Skin();
        skin.add("simpleButton", new TextureRegion(atlas.findRegion("SimpleButton")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(); //создаем стиль
        textButtonStyle.up = skin.getDrawable("simpleButton"); // как выглядит книпка когда она отжата
        textButtonStyle.font = font24;//вид текста


        Group group = new Group();
        final TextButton start1Button = new TextButton("Start 1 P", textButtonStyle);
        final TextButton start2Button = new TextButton("Start 2 P", textButtonStyle);
        final TextButton exitButton = new TextButton("Exit", textButtonStyle);


        start1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME);
            }
        });
        start2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        start1Button.setPosition(0, 80); //расположение кнопки
        start2Button.setPosition(0, 40); //расположение кнопки
        exitButton.setPosition(0, 0);
        group.addActor(start1Button);
        group.addActor(start2Button);
        group.addActor(exitButton);
        group.setPosition(580, 40);
        stage.addActor(group);  //выводим на экран кнопку
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        update(delta);//дельтатайм// спрашиваем у видеокарты сколько времени прошло от предыдущей отрисовки

        ScreenUtils.clear(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();


    }

    public void update(float dt) {

        stage.act(dt);


    }


    @Override
    public void dispose() {
        atlas.dispose();
        font24.dispose();
        stage.dispose();

    }

}

