package mva.andengine.bullets;

import mva.andengine.Battlefield;
import mva.andengine.GfxAssets;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.Sprite;

public class Bullet {

	private final float SPEED = 3;//9

	public static final int DOWN_DIRECTION = 1;
	public static final int LEFT_DIRECTION = 2;
	public static final int TOP_DIRECTION = 3;
	public static final int RIGHT_DIRECTION = 4;

	private BulletList rootlist;

	private int direction;

	public void setDirection(int direction) {
		this.direction = direction;
	}

	private boolean alive = true;

	public boolean isAlive() {
		return alive;
	}

	private Sprite sprite;

	public Bullet(float x, float y, int dir) {
		sprite = new Sprite(x - GfxAssets.bullet.getHeight() / 2, y
				- GfxAssets.bullet.getWidth() / 2, GfxAssets.bullet);
		direction = dir;
	}

	public void setPosition(float x, float y) {
		alive = true;
		sprite.setPosition(x, y);
	}

	public void update() {
		if (Math.abs(sprite.getX()) > Battlefield.maxField
				|| Math.abs(sprite.getY()) > Battlefield.maxField)
			die();

		if (!alive)
			return;

		if (direction == TOP_DIRECTION)
			sprite.setPosition(sprite.getX(), sprite.getY() - SPEED);
		if (direction == RIGHT_DIRECTION)
			sprite.setPosition(sprite.getX() + SPEED, sprite.getY());
		if (direction == DOWN_DIRECTION)
			sprite.setPosition(sprite.getX(), sprite.getY() + SPEED);
		if (direction == LEFT_DIRECTION)
			sprite.setPosition(sprite.getX() - SPEED, sprite.getY());

	}

	public IEntity getSprite() {
		return sprite;
	}

	public void die() {
		getSprite().setVisible(false);
		getSprite().setIgnoreUpdate(true);
		rootlist.remove(this);
		alive = false;
	}

	public void setRootList(BulletList bulletList) {
		rootlist = bulletList;
	}

	public void getReady() {
		getSprite().setVisible(true);
		getSprite().setIgnoreUpdate(false);
	}
}
