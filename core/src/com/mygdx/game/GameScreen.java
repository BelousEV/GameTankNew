package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class GameScreen implements Screen {
    private SpriteBatch batch;

    private BitmapFont font24; //шрифт
    private Map map;
    private PlayerTank player;

    private BulletEmitter bulletEmitter;

    private BotEmitter botEmitter;

    private float gameTimer;
    private Stage stage; //делаем кнопки и интерфейс
    private boolean paused;

    private static final boolean FIRENDLY_FIRE = false;

    public Map getMap() {
        return map;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    public PlayerTank getPlayer() {
        return player;
    }

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;

    }

    @Override
    public void show() { //когда экран показали
        TextureAtlas atlas = new TextureAtlas("game.pack");
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
        map = new Map(atlas);
        player = new PlayerTank(this, atlas);
        bulletEmitter = new BulletEmitter(atlas);
        botEmitter = new BotEmitter(this, atlas);
        gameTimer = 6.0f;
        stage = new Stage();
        //делаем кнопки
        Skin skin = new Skin();
        skin.add("simpleButton", new TextureRegion(atlas.findRegion("SimpleButton")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(); //создаем стиль
        textButtonStyle.up = skin.getDrawable("simpleButton"); // как выглядит книпка когда она отжата
        textButtonStyle.font = font24;//вид декста


        Group group = new Group();
        final TextButton pauseButton = new TextButton("Pause", textButtonStyle);
        final TextButton exitButton = new TextButton("Exit", textButtonStyle);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = !paused;
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        pauseButton.setPosition(0, 40); //расположение кнопки
        exitButton.setPosition(0, 0);
        group.addActor(pauseButton);
        group.addActor(exitButton);
        group.setPosition(1100, 640);
        stage.addActor(group);  //выводим на экран кнопку
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update(delta);//дельтатайм// спрашиваем у видеокарты сколько времени прошло от предыдущей отрисовки

        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//камера следит за игроком (не удалять, пригодится)
       // ScreenManager.getInstance().getCamera().position.set(player.getPosition().x, player.getPosition().y, 0);
        //ScreenManager.getInstance().getCamera().update();

        batch.setProjectionMatrix(ScreenManager.getInstance().getCamera().combined);
        batch.begin();
        map.render(batch);
        player.render(batch);
        botEmitter.render(batch);
        bulletEmitter.render(batch);
        player.renderHUD(batch, font24);
        stage.draw();
        batch.end();
    }

    public void update(float dt) {
        if (!paused) {
            gameTimer += dt;
            if (gameTimer > 5.0f) {
                gameTimer = 0.0f;

                float coordX, coordY; //тобы боты не появлялись в стене
                do {
                    coordX = MathUtils.random(0, Gdx.graphics.getWidth());
                    coordY = MathUtils.random(0, Gdx.graphics.getHeight());
                } while (!map.isAreaClear(coordX, coordY, 20));

                botEmitter.activate(coordX, coordY);
            }
            player.update(dt);
            botEmitter.update(dt);
            bulletEmitter.update(dt); //bulletEmitter сам посмотри и заапдейть
            checkCollisions();
        }
        stage.act(dt);


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
    public void resize(int width, int height) { //когда отмасштабировали
        ScreenManager.getInstance().resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() { //развернули экран обратно

    }

    @Override
    public void hide() { //скрылся

    }

    @Override
    public void dispose() {

    }

}
