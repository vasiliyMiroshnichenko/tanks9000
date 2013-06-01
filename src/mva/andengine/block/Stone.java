package mva.andengine.block;

import mva.andengine.GfxAssets;
import mva.andengine.bullets.Bullet;
import org.anddev.andengine.entity.sprite.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 24.05.13
 * Time: 0:21
 * To change this template use File | Settings | File Templates.
 */
public class Stone implements Block {
    public final  static int CODE = 1;

    private final Sprite sprite;
    private int x, y;

    public Stone(int x1, int y1) {
        sprite = new Sprite(x1 * Block.SIZE, y1 * Block.SIZE, GfxAssets.stone);
        x = x1;
        y = y1;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }
    @Override
    public int getXBlock() {
        return x * Block.SIZE;
    }
    @Override
    public int getYBlock() {
        return y * Block.SIZE;
    }

    @Override
    public boolean isIn(Bullet bull ) {
        if (!isAlive()) {
            return false;
        }

        if (sprite.getX() < bull.getSprite().getX() && sprite.getY() < bull.getSprite().getY()
                && sprite.getX() + SIZE > bull.getSprite().getX() && sprite.getY() + SIZE > bull.getSprite().getY())
            return true;
        return false;

    }

    @Override
    public void hit() {
        //??
    }

    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }
}
