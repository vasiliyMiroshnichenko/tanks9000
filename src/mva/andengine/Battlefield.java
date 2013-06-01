package mva.andengine;

import java.util.ArrayList;
import java.util.LinkedList;

import android.util.Log;
import mva.andengine.block.*;
import mva.andengine.bot.Bot;

import mva.andengine.bullets.Bullet;
import mva.andengine.bullets.BulletList;
import mva.andengine.scene.GameScene;
import mva.andengine.xml.Cube;
import mva.andengine.xml.XmlLevelParser2;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.Sprite;

public class Battlefield {

    public boolean PAUSE = false;

    public int BOT_COUNT;
    public int BOT_SAME_TIME_COUNT;

    private int widthBlock;
	private int heightBlock;

	public final float width;
	public final float height;

	private float defaultX;
	private float defaultY;

	private float x;
	private float y;

	private Sprite background;
	public GameScene gameScene;
	private Vehicle player;

	public Vehicle getPlayer() {
		return player;
	}

	private EnemyList enemys = new EnemyList();
	public BulletList bullets = new BulletList();
	private BlockList blocks = new BlockList();

	private Track track;

	//public static final LinkedList<Bot> botPool = new LinkedList<Bot>();

	public static float maxField = 1500;

	public Battlefield(GameScene gs, int level) {
		gameScene = gs;
		XmlLevelParser2 parser = new XmlLevelParser2(level);
		widthBlock = parser.getWidth();
		heightBlock = parser.getHeight();
        BOT_COUNT = parser.getBotCount();
        BOT_SAME_TIME_COUNT = parser.getSameTimeBot();

		width = widthBlock * Block.SIZE;
		height = heightBlock * Block.SIZE;
        //TODO
		defaultX = height - GameActivity.CAMERA_HEIGHT;
		defaultY = width / 2 - GameActivity.CAMERA_WIDTH / 2;

		background = new Sprite(x, y, width, height, GfxAssets.getField(parser.getFieldType()));

        player = new Vehicle(this);

		for (Cube cube : parser.getBricks()) {
            if(cube.type == Brick.CODE) {
			    blocks.add(new Brick(cube.x, cube.y));
            } else if(cube.type == Stone.CODE){
                blocks.add(new Stone(cube.x,cube.y));
            }else if(cube.type == Eagle.CODE){
                blocks.add(new Eagle(cube.x,cube.y,player,this)) ;
            }else if(cube.type == Water.CODE){
                blocks.add(new Water(cube.x,cube.y)) ;
            }
		}

		track = new Track(widthBlock, heightBlock);
		track.newTrack();
	}

	public Sprite getSprite() {
		return background;
	}

	public ArrayList<Entity> getSpritesList() {
		ArrayList<Entity> arl = new ArrayList<Entity>();
		arl.add(background);

		for (Block entity : blocks) {
			arl.add(entity.getSprite());
		}

		arl.add(player.getSprite());
		return arl;
	}

	public void goTop() {
        if(!PAUSE)
		player.goTop();
	}

	public void goRight() {
        if(!PAUSE)
		player.goRight();
	}

	public void goBottom() {
        if(!PAUSE)
		player.goBottom();
	}

	public void goLeft() {
        if(!PAUSE)
		player.goLeft();
	}

	public void Stop() {
        if(!PAUSE)
		player.Stop();
	}

	public void shot() {
        if(!PAUSE)
		player.shot();
	}

	public synchronized void addBullet(Bullet bullet) {
		try {
			gameScene.attachChild(bullet.getSprite());
		} catch (Exception ex) {
		}
	}

	public boolean checkSquare(final BaseVehicle tank) {
		if ((tank.getDirection() == BaseVehicle.TOP_DIRECTION)) {
			for (Block brick : blocks) {
				if (!brick.isAlive())
					continue;
				if (!(brick.getXBlock() > tank.getSprite().getX() + tank.SIZE)
						&& !(brick.getXBlock() + Block.SIZE < tank.getSprite().getX()) // right
						&& !(tank.getSprite().getY() + tank.SIZE - tank.SPEED < brick.getYBlock()) // top
						&& !(tank.getSprite().getY() - tank.SPEED > brick.getYBlock() + Block.SIZE)) // down
                {
                    return false;
                }
				else
					continue;
			}
		}
		if ((tank.getDirection() == BaseVehicle.LEFT_DIRECTION)) {
			for (Block brick : blocks) {
				if (!brick.isAlive())
					continue;
				if (!(brick.getXBlock() > tank.getSprite().getX() + tank.SIZE - tank.SPEED)
						&& !(brick.getXBlock() + Block.SIZE < tank.getSprite().getX() - tank.SPEED)
						&& !(tank.getSprite().getY() + tank.SIZE < brick.getYBlock())
						&& !(tank.getSprite().getY() > brick.getYBlock() + Block.SIZE)) {

					return false;
				} else
					continue;
			}
		}
		if ((tank.getDirection() == BaseVehicle.DOWN_DIRECTION)) {
			for (Block block : blocks) {
				if (!block.isAlive())
					continue;
				if (!(block.getXBlock() > tank.getSprite().getX() + tank.SIZE)
						&& !(block.getXBlock() + Block.SIZE < tank.getSprite().getX())
						&& !(tank.getSprite().getY() + tank.SIZE + tank.SPEED < block.getYBlock())
						&& !(tank.getSprite().getY() + tank.SPEED > block.getYBlock() + Block.SIZE))
					return false;
				else
					continue;
			}
		}
		if ((tank.getDirection() == BaseVehicle.RIGHT_DIRECTION)) {
			for (Block block : blocks) {
				if (!block.isAlive())
					continue;
				if (!(block.getXBlock() > tank.getSprite().getX() + tank.SIZE + tank.SPEED)// 0
						&& !(block.getXBlock() + Block.SIZE < tank.getSprite().getX() + tank.SPEED)// r
						&& !(tank.getSprite().getY() + tank.SIZE < block.getYBlock())
						&& !(tank.getSprite().getY() > block.getYBlock() + Block.SIZE)) {
					return false;
				} else
					continue;
			}
		}
		return true;
	}

	public void update() {
        if(PAUSE){
            return;
        }
		player.update();
		bullets.update();
		track.newTrack();
        enemys.update(this);

        //checkBulletHitBullet();
        checkBulletHitBrick();
        checkBulletTargetVehicle();

        blocks.Check(gameScene);
	}

    private void checkBulletHitBullet() {
        //if(bullets.size() < 2) return;
        if(bullets.size() <2 )return;

        fr: for (int i = 0; i < bullets.size() - 1; i++) {
            for (int j = i + 1; j < bullets.size(); i++) {
                if (bulletsFaced(bullets.get(i).getSprite().getX(), bullets.get(j).getSprite().getX()) &&
                        bulletsFaced(bullets.get(i).getSprite().getY(),bullets.get(j).getSprite().getY())
                        && bullets.get(i).isAlive() && bullets.get(j).isAlive()) {

                    Bullet b1  = bullets.get(i);
                    Bullet b2  = bullets.get(j);

                    Log.v("pzdc",b1.getSprite().getX() + ":"+b1.getSprite().getY()+"   "+b2.getSprite().getX() + ":"+b2.getSprite().getY());

                    /*b1.die();
                    b2.die();

                    i = 0;
                    continue fr;   */
                }
            }
        }
    }

    private boolean bulletsFaced(float i, float j) {
        return (i <= j + 2) && (i >= j - 2);
    }

    private void checkBulletHitBrick() {
        fr: for (int i = 0; i < bullets.size(); i++) {
            for (Block block : blocks) {
                if (block.isIn(bullets.get(i)) && bullets.get(i).isAlive()) {
                    bullets.get(i).die();
                    block.hit();
                    i = 0;
                    continue fr;
                }
            }
        }
    }

    private void checkBulletTargetVehicle() {
        frr:for (int i = 0; i < bullets.size(); i++) {
            for (Bot bot : enemys) {
              if (player.isIn(bullets.get(i)) && bullets.get(i).isAlive()){
                    bullets.get(i).die();
                    player.hit();
                    i = 0;
                    continue frr;
                }

                if (bot.isIn(bullets.get(i)) && bullets.get(i).isAlive()){
                    bullets.get(i).die();
                    bot.hit();
                    i = 0;
                    continue frr;
                }
            }
        }
    }

    public void finish() {
        PAUSE = true;

        gameScene.finishDialog();
//        gameScene.fScene.show();
//        gameScene.setChildScene(gameScene.fScene, false, true, true);
	}

	public Bot takeOrCreateBot() {
			Bot bt = new Bot(this,track);
			gameScene.attachChild(bt.getSprite());
			return bt;
	}

    private void nextLevel(){
        gameScene.nextLevel();
    }

    public void unpause() {
        PAUSE = false;
    }

    public void setPause() {
        PAUSE = true;
    }

    public class Track {
            public int[][] trackMatrix;
            public final int width, height;

            public static final int WALL = 1;
            public static final int SPACE = 0;
            public static final int PLAYER = 2;

            public int playerX;
            public int playerY;

            public Track(int _width, int _height) {
                width = _width;
                height = _height;
                trackMatrix = new int[width][height];
            }

            public int pixelToBlock(float coords) {
                return ((int)coords / Block.SIZE);
            }

            public void newTrack() {
                for (Block block : blocks) {
                    if (block.isAlive())
                        trackMatrix[block.getY()][block.getX()] = WALL;
                    else
                        trackMatrix[block.getY()][block.getX()] = SPACE;
                }
                if (player != null){
                    playerX = pixelToBlock(player.getX());
                    playerY = pixelToBlock(player.getY());
                }
            }
    }
}
