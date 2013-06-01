package mva.andengine.block;

import mva.andengine.bullets.Bullet;
import mva.andengine.GfxAssets;
import org.anddev.andengine.entity.sprite.Sprite;

public class Brick implements Block {

    public final  static int CODE = 0;
	private final Sprite sprite;
	private short hp = 3;
	private int x, y;

	public Brick(int x1, int y1) {
		sprite = new Sprite(x1 * Block.SIZE, y1 * Block.SIZE, GfxAssets.brick100);
		x = x1;
		y = y1;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void hit() {
		hp--;
		if (hp == 0) {
			sprite.setVisible(false);
			return;
		} else if (hp == 2) {
			sprite.setTextureRegion(GfxAssets.brick50);
			return;
		} else if (hp == 1) {
			sprite.setTextureRegion(GfxAssets.brick1);
			return;
		}
	}

	public boolean isInArea(float x, float y) {
		return false;
	}

	public boolean isIn(Bullet bull) {
		if (hp == 0) {
			return false;
		}

		if (sprite.getX() < bull.getSprite().getX() && sprite.getY() < bull.getSprite().getY()
				&& sprite.getX() + SIZE > bull.getSprite().getX() && sprite.getY() + SIZE > bull.getSprite().getY())
			return true;
		return false;

	}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getXBlock() {
        return x * Block.SIZE;
    }

    public int getYBlock() {
        return y * Block.SIZE;
    }

	public boolean isAlive() {
		return hp != 0;
	}
}
