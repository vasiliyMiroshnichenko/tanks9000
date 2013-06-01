package mva.andengine.bot;

import java.util.LinkedList;
import java.util.Random;

import android.util.Log;
import mva.andengine.BaseVehicle;
import mva.andengine.Battlefield;
import mva.andengine.EnemyList;
import mva.andengine.Battlefield.Track;
import mva.andengine.block.Block;
import mva.andengine.bullets.Bullet;
import mva.andengine.GfxAssets;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Pair;
import org.anddev.andengine.entity.sprite.Sprite;

public class Bot extends BaseVehicle {

    private final int INITIAL_HP = 2;
    public int HP = INITIAL_HP;
	public final float SPEED = 5;
    public static final int WORTH = 5;
    private float RELOAD_TIME = 3000; // ms
	private AnimatedSprite sprite;
	private long beginTime;
	private long FRAME_PERIOD = 100; // ms

    public AnimatedSprite getSprite() {
        return sprite;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public int getXBlock() {
        return pixelToBlock(sprite.getX());
    }

    public int getYBlock() {
        return pixelToBlock(sprite.getY());
    }

	private Battlefield bf;

	private EnemyList rootlist;
	private Track track;
	private static Music shotSound;

	public Bot(Battlefield _bf, Track _track) {
		this.bf = _bf;
		track = _track;

       // bf.gameScene.attachChild(sp);

        Float[] x = {0f,0f};
        getFreePosition(_track, x);

        sprite = new AnimatedSprite(x[0],x[1], BaseVehicle.SIZE, BaseVehicle.SIZE, GfxAssets.tank);
		sprite.animate(new long[]{ 50, 50 });
        direction = newRandomDirection();
		shotSound = GfxAssets.shot;
	}
    public void RandomPosition() {
        Float[] x = {0f,0f};
        getFreePosition(track,x);
        sprite.setPosition(x[0]+50,x[1]+50);
    }
    private void getFreePosition(Track _track, final Float[] x) {
        for(int i = 1; i < _track.width; i++) {
                i = new Random().nextInt(_track.width-2);
                if(_track.trackMatrix[i][1] == 0 )
                {
                    x[0] = ((i * Block.SIZE)+(Block.SIZE - BaseVehicle.SIZE)/2);
                    x[1] = ((1 * Block.SIZE)+(Block.SIZE - BaseVehicle.SIZE)/2);
                    break;
                }
            }
    }

    private void setRotation()   {
        switch(direction){
            case TOP_DIRECTION :
                sprite.setRotation(0);
                break;
            case DOWN_DIRECTION :
                sprite.setRotation(180);
                break;
            case LEFT_DIRECTION :
                sprite.setRotation(270);
                break;
            case RIGHT_DIRECTION :
                sprite.setRotation(90);
                break;
        }
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
		if (System.currentTimeMillis() - beginTime < FRAME_PERIOD) return;
        beginTime = System.currentTimeMillis();

        if(sprite.getX() % 50 == 5 && sprite.getY() % 50 == 5)
        {
            if(isPlayerVisible()){
                direction = getSideDirection();
                setRotation();
                shot();
                return;
            }
            else {
                if( Random(45))//
                {
                    direction = newRandomDirection();
                    setRotation();
                }
            }
        }
        if (bf.checkSquare(this)) {
			if (direction == TOP_DIRECTION) {
				sprite.setPosition(sprite.getX(), sprite.getY() - SPEED);
				return;
			}
			if (direction == DOWN_DIRECTION) {
				sprite.setPosition(sprite.getX(), sprite.getY() + SPEED);
				return;
			}
			if (direction == RIGHT_DIRECTION) {
				sprite.setPosition(sprite.getX() + SPEED, sprite.getY());
				return;
			}
			if (direction == LEFT_DIRECTION) {
				sprite.setPosition(sprite.getX() - SPEED, sprite.getY());
			}

            switch(direction){
                case TOP_DIRECTION :
                    sprite.setRotation(0);
                    break;
                case DOWN_DIRECTION :
                    sprite.setRotation(180);
                    break;
                case LEFT_DIRECTION :
                    sprite.setRotation(270);
                    break;
                case RIGHT_DIRECTION :
                    sprite.setRotation(90);
                    break;
            }
		}
	}

    private int newRandomDirection() {
        return new Random().nextInt(5) + 1;
    }

    private boolean Random(int i) {
        Random r = new Random();
        return (r.nextDouble() * 100) < i;
    }

    private boolean isPlayerVisible() {
        int dir = 0;
        Pair<Integer,Integer> curent = new Pair<Integer, Integer>(getXBlock(), getYBlock());

        if( curent.first == track.playerX || curent.second == track.playerY)
        {
            dir = getSideDirection();

            int i  = 0;
            while((curent.first != track.playerX || curent.second != track.playerY )&&
                            (curent.first > 0 && curent.first < pixelToBlock(bf.width) &&
                             curent.second > 0 && curent.second < pixelToBlock(bf.height))){
                i++;
                if(i > bf.width ) return true;
                if(track.trackMatrix[curent.first][curent.second] == Track.WALL)
                    return false;
                curent = move(curent.first,curent.second,dir);
            }
            return true;
        }
        return false;
    }

    private Pair<Integer,Integer> move(int curentX, int curentY, int dir) {
        switch (dir)  {
            case BaseVehicle.TOP_DIRECTION :
                curentY -= Block.SIZE;
                break;
            case BaseVehicle.DOWN_DIRECTION :
                curentY += Block.SIZE;
                break;
            case BaseVehicle.LEFT_DIRECTION :
                curentX -= Block.SIZE;
                break;
            case BaseVehicle.RIGHT_DIRECTION :
                curentX += Block.SIZE;
                break;
        }
        return new Pair<Integer, Integer>(curentX,curentY) ;
    }

    public int pixelToBlock(float coords) {
        return Math.round(coords / Block.SIZE);
    }

	public boolean isAlive() {
		return HP != 0;
	}

	@Override
	public int getActing() {
		return 0;
	}

	public void die() {
        final Sprite sp = new Sprite(sprite.getX()  ,sprite.getY() ,GfxAssets.blow);

        bf.gameScene.botKill(this);
        bf.gameScene.detachChild(this.sprite);
		getSprite().setVisible(false);
		getSprite().setPosition(-10f, -10f);
		getSprite().setIgnoreUpdate(true);

		rootlist.remove(this);

        bf.gameScene.attachChild(sp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while (i < 20){
                        i++;
                        sp.setRotation(new Random().nextFloat() * 360);
                        Thread.sleep(25);
                    }
                } catch (InterruptedException e) {}
                finally {
                    sp.setVisible(false);
                    sp.setIgnoreUpdate(true);
                    bf.gameScene.detachChild(sp);
                }

            }
        }).start();
	}

	public void setRootList(EnemyList enemyList) {
		rootlist = enemyList;
	}

    private int getSideDirection(){
        int dir = 0;
        if(getYBlock() == track.playerY && getXBlock() <= track.playerX)
            dir = BaseVehicle.RIGHT_DIRECTION;
        if(getYBlock() == track.playerY && getXBlock() > track.playerX)
            dir = BaseVehicle.LEFT_DIRECTION;

        if(getXBlock() == track.playerX && getYBlock() <= track.playerY)
            dir = BaseVehicle.DOWN_DIRECTION;
        if(getXBlock() == track.playerX && getYBlock() > track.playerY)
            dir = BaseVehicle.TOP_DIRECTION;
        return dir;
    }

    public void hit() {                                              //TODO
        HP--;
        if(!isAlive()){
            die();
            HP = INITIAL_HP;
        }
    }

    public boolean isIn(Bullet bull) {
        if (!isAlive()) {
            return false;
        }
        float correction = (Block.SIZE - BaseVehicle.SIZE) / 2;
        if (sprite.getX() + correction < bull.getSprite().getX() && sprite.getY() + correction < bull.getSprite().getY()
                && sprite.getX() + SIZE - correction > bull.getSprite().getX() && sprite.getY() + SIZE - correction > bull.getSprite().getY())
            return true;
        return false;
    }
}
