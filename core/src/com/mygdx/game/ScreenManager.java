package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.units.GameType;

public class ScreenManager { //управление экранами

    public enum ScreenType {
        MENU, GAME
    }

    private static ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    private ScreenManager() {

    }

    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;

    private Game game;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private Viewport viewport; //масштабирование мира
    private Camera camera;

    public Viewport getViewport() {
        return viewport;
    }

    public Camera getCamera() {
        return camera;
    }

    public void init(Game game, SpriteBatch batch) {
        this.game = game;
        this.camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2,0);
        this.camera.update();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.gameScreen = new GameScreen(batch);
        this.menuScreen = new MenuScreen(batch);
    }



    public void resize (int width, int height){ // изменение размера окна
        viewport.update(width, height); //новые параметры окна
        viewport.apply(); //применить


    }
    public void setScreen(ScreenType screenType, Object ... args) {
        Gdx.input.setCursorCatched(false);
        Screen currentScreen = game.getScreen();  //запомним текущий экран
        switch (screenType) {//смотрим на тип экрана, куда нас просят перейти
            case MENU:
                game.setScreen(menuScreen);
                break;
            case GAME:
                gameScreen.setGameType((GameType) args [0]);
                game.setScreen(gameScreen);
                break;
        }
        if (currentScreen != null) {
            currentScreen.dispose(); //освобождаем ресурс
        }

    }

}
