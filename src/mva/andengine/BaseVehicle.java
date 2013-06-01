package mva.andengine;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.ChangeableText;

public abstract class BaseVehicle {
    public abstract int getActing();

    public abstract AnimatedSprite getSprite();

	public final float SPEED = 5;
	public final static float SIZE = 40;

	public static final int DOWN_DIRECTION = 1;
	public static final int LEFT_DIRECTION = 2;
	public static final int TOP_DIRECTION = 3;
	public static final int RIGHT_DIRECTION = 4; 

	public static final int STOP = 0;
	public static final int MOVE = 1;

	protected long shotTime;
	
	protected int direction = TOP_DIRECTION;	
	public int getDirection() {
		return direction;
	}

    protected abstract boolean isAlive();

    protected int action = STOP;

    public abstract void hit();
}
