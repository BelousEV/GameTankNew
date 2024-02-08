package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.units.PlayerTank;
import com.mygdx.game.units.Tank;

public class GameTanks extends ApplicationAdapter {
	private SpriteBatch batch; //вся игровая область (хотим что то нарисовать, обращаемся к batch)
	private Map map;
	private PlayerTank player;

	private BulletEmitter bulletEmitter;

	private BotEmitter botEmitter;

	private float gameTimer;

	public BulletEmitter getBulletEmitter() {
		return bulletEmitter;
	}

	@Override
	public void create () { //метод отвечает за запуск приложения с начальной подготовкой
		batch = new SpriteBatch();
		map = new Map();
		player = new PlayerTank(this);
		bulletEmitter = new BulletEmitter();
		botEmitter = new BotEmitter(this);
		botEmitter.activate(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()));


	}

	@Override
	public void render () { //метод отвечает за всю отрисовку
		float dt = Gdx.graphics.getDeltaTime();//дельтатайм// спрашиваем у видеокарты сколько времени прошло от предыдущей отрисовки
		update(dt);
		ScreenUtils.clear(0, 0.6f, 0, 1);
		batch.begin();
		map.render(batch);
		player.render(batch);
		botEmitter.render(batch);
		bulletEmitter.render(batch);
		batch.end();
	}

	public void update(float dt){
		gameTimer += dt;
		if (gameTimer > 10.0f){
			gameTimer = 0.0f;
			botEmitter.activate(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()));
		}
		player.update(dt);
		botEmitter.update(dt);
		bulletEmitter.update(dt); //bulletEmitter сам посмотри и заапдейть

	}
	@Override
	public void dispose () { //метод освобождения ресурсов
		batch.dispose();

	}

}
