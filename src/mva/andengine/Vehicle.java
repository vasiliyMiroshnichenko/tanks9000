package mva.andengine;

import mva.andengine.block.Block;
import mva.andengine.bullets.Bullet;
import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;
import org.anddev.andengine.entity.sprite.Sprite;

import java.util.Random;

public class Vehicle extends BaseVehicle {

	private Battlefield bf;

	public final float SPEED = 5;

    private int HP = 5;
	private static final float ACCELERATION = 0.1f;
	private static final float INERTIANL_TIME = 1;
	private float inertialForse;

	private final float RELOAD_TIME = 100; // ms

	private final float x = GameActivity.CAMERA_WIDTH / 2 - GfxAssets.tank.getWidth() / 2 / 2;
    private final float y = GameActivity.CAMERA_HEIGHT / 2 - GfxAssets.tank.getHeight() / 2;

    public AnimatedSprite getSprite() {
        return sprite;
    }
    protected AnimatedSprite sprite;


    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    private final long[] frameDurration = { 50, 50 };

	private static Music engineSound;
	private static Music shotSound;
	private static Music idlingSound;
	static {
		engineSound = GfxAssets.engine;
		shotSound = GfxAssets.shot;
		idlingSound = GfxAssets.idling;
	}
	private long beginTime;
	private final long FRAME_PERIOD = 50; // ms

	public Vehicle(Battlefield _bf) {
		bf = _bf;

        Float[] x = {0f,0f};
        getFreePosition(x);

		sprite = new AnimatedSprite(x[0],x[1], Vehicle.SIZE, Vehicle.SIZE, GfxAssets.playerTank);
		sprite.stopAnimation(0);

		GfxAssets.loadMusic();
		engineSound = GfxAssets.engine;
		shotSound = GfxAssets.shot;
		idlingSound = GfxAssets.idling;

		idlingSound.setLooping(true);
		idlingSound.play();
		engineSound.setLooping(true);

        bf.gameScene.health.setText("♥"+HP);
	}

    private void getFreePosition( final Float[] x) {
        x[0] = ((9 * Block.SIZE)+(Block.SIZE - BaseVehicle.SIZE)/2);
        x[1] = ((15 * Block.SIZE)+(Block.SIZE - BaseVehicle.SIZE)/2);

    }

	public int getActing() {
		return action;
	}

    @Override
    public boolean isAlive() {
        return (HP != 0);
    }

    @Override
    public void hit() {
        HP--;
        bf.gameScene.health.setText("♥"+HP);
        if(!isAlive()){
            finish();
        }
    }

    public void criticalHit() {
        finish();
    }

    public void goTop() {
		direction = TOP_DIRECTION;
		action = MOVE;
		sprite.setRotation(0);

		idlingSound.pause();
		engineSound.play();
	}

	public void goRight() {
		direction = RIGHT_DIRECTION;
		action = MOVE;
		sprite.setRotation(90);

		idlingSound.pause();
		engineSound.play();
	}

	public void goBottom() {
		direction = DOWN_DIRECTION;
		action = MOVE;
		sprite.setRotation(180);

		idlingSound.pause();
		engineSound.play();
	}

	public void goLeft() {
		direction = LEFT_DIRECTION;
		action = MOVE;
		sprite.setRotation(270);

		idlingSound.pause();
		engineSound.play();
	}

	public void Stop() {
		action = STOP;

		idlingSound.play();
		engineSound.pause();
	}

	public void shot() {
		if (System.currentTimeMillis() - shotTime > RELOAD_TIME) {
			float xCorrection = 0, yCorrection = 0;

			if (direction == TOP_DIRECTION) {
				xCorrection = (sprite.getX() + SIZE / 2 - 2);
				yCorrection = (sprite.getY());
			}
			if (direction == DOWN_DIRECTION) {
				xCorrection = (sprite.getX() + SIZE / 2 - 1);
				yCorrection = (sprite.getY() + SIZE);
			}
			if (direction == LEFT_DIRECTION) {
				xCorrection = (sprite.getX());
				yCorrection = (sprite.getY() + SIZE / 2 - 1);
			}
			if (direction == RIGHT_DIRECTION) {
				xCorrection = (sprite.getX() + SIZE);
				yCorrection = (sprite.getY() + SIZE / 2 - 2);
			}

            Bullet newBullet = new Bullet(xCorrection, yCorrection,direction);
            bf.bullets.add(newBullet);
            bf.addBullet(newBullet);

			shotTime = System.currentTimeMillis();
			shotSound.play();
		}
	}

	public void update() {

		if (System.currentTimeMillis() - beginTime < FRAME_PERIOD) {
			return;
		}

		beginTime = System.currentTimeMillis();

		if (action == STOP) {
			sprite.stopAnimation(0);
			inertialForse = 0f;
			return;
		}
		if (inertialForse < INERTIANL_TIME) { // starting
			inertialForse += ACCELERATION;
			sprite.animate(frameDurration);
		}
		if (bf.checkSquare(this)) {
		/*
			if (direction == TOP_DIRECTION) {
				sprite.setPosition(sprite.getX(), sprite.getY() - SPEED * inertialForse);
				return;
			}
			if (direction == DOWN_DIRECTION) {
				sprite.setPosition(sprite.getX(), sprite.getY() + SPEED * inertialForse);
				return;
			}
			if (direction == RIGHT_DIRECTION) {
				sprite.setPosition(sprite.getX() + SPEED * inertialForse, sprite.getY());
				return;
			}
			if (direction == LEFT_DIRECTION) {
				sprite.setPosition(sprite.getX() - SPEED * inertialForse, sprite.getY());
			}*/
            switch (direction){
                case TOP_DIRECTION :
                    sprite.setPosition(sprite.getX(), sprite.getY() - SPEED * inertialForse);
                    break;
                case DOWN_DIRECTION :
                    sprite.setPosition(sprite.getX(), sprite.getY() + SPEED * inertialForse);
                    break;
                case RIGHT_DIRECTION :
                    sprite.setPosition(sprite.getX() + SPEED * inertialForse, sprite.getY());
                    break;
                case LEFT_DIRECTION :
                    sprite.setPosition(sprite.getX() - SPEED * inertialForse, sprite.getY());
                    break;
		    }
        }

	}

    public boolean isIn(Bullet bull) {

        float correction = (Block.SIZE - BaseVehicle.SIZE) / 2;
        if (sprite.getX() + correction < bull.getSprite().getX() && sprite.getY() + correction < bull.getSprite().getY()
                && sprite.getX() + SIZE - correction > bull.getSprite().getX() && sprite.getY() + SIZE - correction > bull.getSprite().getY())
            return true;
        return false;
    }

	public void finish() {
        sprite.setVisible(false);

        final Sprite sp = new Sprite(sprite.getX()  ,sprite.getY() ,GfxAssets.blow);
        bf.gameScene.attachChild(sp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (new Random().nextInt(100) != 5){
                        sp.setRotation(new Random().nextFloat() * 360);
                        Thread.sleep(25);
                    }
                } catch (InterruptedException e) {} finally {
                    bf.gameScene.detachChild(sp);
                }
            }
        }).start();

        bf.finish();
		engineSound.stop();
		idlingSound.stop();

	}
}
