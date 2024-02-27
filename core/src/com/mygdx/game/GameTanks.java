package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.units.BotTank;
import com.mygdx.game.units.PlayerTank;
import com.mygdx.game.units.Tank;

public class GameTanks extends ApplicationAdapter {
	private SpriteBatch batch; //вся игровая область (хотим что то нарисовать, обращаемся к batch)
	private Map map;
	private PlayerTank player;

	private BulletEmitter bulletEmitter;

	private BotEmitter botEmitter;

	private float gameTimer;
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

	@Override
	public void create () { //метод отвечает за запуск приложения с начальной подготовкой
		TextureAtlas atlas = new TextureAtlas("game.pack");
		batch = new SpriteBatch();
		map = new Map(atlas);
		player = new PlayerTank(this, atlas);
		bulletEmitter = new BulletEmitter(atlas);
		botEmitter = new BotEmitter(this, atlas);
		botEmitter.activate(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()));


	}

	@Override
	public void render () { //метод отвечает за всю отрисовку
		float dt = Gdx.graphics.getDeltaTime();//дельтатайм// спрашиваем у видеокарты сколько времени прошло от предыдущей отрисовки
		update(dt);
		ScreenUtils.clear(0, 0.6f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch);
		player.render(batch);
		botEmitter.render(batch);
		bulletEmitter.render(batch);
		batch.end();
	}

	public void update(float dt){
		gameTimer += dt;
		if (gameTimer > 5.0f){
			gameTimer = 0.0f;
			botEmitter.activate(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()));
		}
		player.update(dt);
		botEmitter.update(dt);
		bulletEmitter.update(dt); //bulletEmitter сам посмотри и заапдейть
		checkCollisions();

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
	public void dispose () { //метод освобождения ресурсов
		batch.dispose();

	}

}
