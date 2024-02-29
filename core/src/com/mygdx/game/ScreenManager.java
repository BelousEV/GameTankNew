package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenManager { //управление экранами

    public enum ScreenType{
        MENU, GAME
    }
    private static ScreenManager ourInstance =  new ScreenManager();
    public static ScreenManager getInstance() {return ourInstance;}
    private ScreenManager (){

    }
    private Game game;
    private GameScreen gameScreen;

    public void init(Game game, SpriteBatch batch){
        this.game = game;
        this.gameScreen = new GameScreen(batch);
    }

    public void setScreen (ScreenType screenType) {
        Screen currentScreen = game.getScreen();  //запомним текущий экран
        switch (screenType) {//смотрим на тип экрана, куда нас просят перейти
            case GAME:
                game.setScreen(gameScreen);
                break;
        }
        if (currentScreen !=null) {
            currentScreen.dispose(); //освобождаем ресурс
        }

    }

}
