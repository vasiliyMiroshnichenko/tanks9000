package mva.andengine.block;

import mva.andengine.GfxAssets;
import mva.andengine.block.Block;
import mva.andengine.bullets.Bullet;
import org.anddev.andengine.entity.sprite.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 24.05.13
 * Time: 0:20
 * To change this template use File | Settings | File Templates.
 */
public class Water implements Block{

    public final static int CODE = 2;
    private final Sprite sprite;
    private int x, y;

    public Water(int x1, int y1) {
        sprite = new Sprite(x1 * Block.SIZE, y1 * Block.SIZE, GfxAssets.water);
        x = x1;
        y = y1;
    }

    public void hit() {
        //TODO
    }
    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public int getXBlock() {
        return x * Block.SIZE;
    }

    public int getYBlock() {
        return y * Block.SIZE;
    }

    @Override
    public boolean isIn(Bullet bull ) {
        return false;
    }
}
