package mva.andengine.block;

import mva.andengine.bullets.Bullet;
import org.anddev.andengine.entity.sprite.Sprite;

public interface Block {
	public final static int SIZE = 50;

    public abstract boolean isAlive();
    public abstract Sprite getSprite();

    public int getXBlock();
    public int getYBlock();

    public boolean isIn(Bullet bullet);

    public void hit();

    public int getX();
    public int getY();
}
