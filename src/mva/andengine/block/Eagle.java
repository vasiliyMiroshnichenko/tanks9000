package mva.andengine.block;

import android.util.Log;
import mva.andengine.Battlefield;
import mva.andengine.GfxAssets;
import mva.andengine.Vehicle;
import mva.andengine.block.Block;
import mva.andengine.bullets.Bullet;
import org.anddev.andengine.entity.sprite.Sprite;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 24.05.13
 * Time: 0:20
 * To change this template use File | Settings | File Templates.
 */
public class Eagle implements Block{

    public final static int CODE = 99;
    private short hp = 1;
    private final Sprite sprite;
    private int x, y;
    private Vehicle vehicle;
    private Battlefield bf;

    public Eagle(int x1, int y1,Vehicle pl,Battlefield _bf) {
        sprite = new Sprite(x1 * Block.SIZE, y1 * Block.SIZE, GfxAssets.eagle);
        x = x1;
        y = y1;
        vehicle = pl;
        bf = _bf;
    }

    public void hit() {
        vehicle.criticalHit();
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

    }
    @Override
    public int getX() {
        //
        return x;
    }
    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
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
        if (!isAlive()) {
            return false;
        }

        if (sprite.getX() < bull.getSprite().getX() && sprite.getY() < bull.getSprite().getY()
                && sprite.getX() + SIZE > bull.getSprite().getX() && sprite.getY() + SIZE > bull.getSprite().getY())
            return true;
        return false;

    }
}
