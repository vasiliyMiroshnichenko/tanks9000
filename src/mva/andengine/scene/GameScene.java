package mva.andengine.scene;

import mva.andengine.*;
import mva.andengine.bot.Bot;
import mva.andengine.buttons.GameButton;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;

public class GameScene extends Scene implements ISwitchableScene {

	private Battlefield bf;
    public final static int CODE = MainState.GAME_IS_RUNNING;
    public ChangeableText health;
    public ChangeableText score;
    private int points;

    private Integer pickedLevel = null;

	public final GameButton leftButton;
	public final GameButton bottomButton;
	public final GameButton topButton;
	public final GameButton rightButton;
	public final GameButton shotButton;

	private MainState mainState;

	private HUD hud;

	public GameScene(MainState mainState) {
		super();
		this.mainState = mainState;

		leftButton = new GameButton(0, 280) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					bf.goLeft();
				} else if (pSceneTouchEvent.isActionUp()) {
					bf.Stop();
				}
				return true;
			};
		};
		rightButton = new GameButton(160, 280) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					bf.goRight();
				} else if (pSceneTouchEvent.isActionUp()) {
					bf.Stop();
				}

				return true;
			};
		};
		bottomButton = new GameButton(80, 370) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					bf.goBottom();
				} else if (pSceneTouchEvent.isActionUp()) {
					bf.Stop();
				}
				return true;
			};
		};
		topButton = new GameButton(80, 190) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					bf.goTop();

				} else if (pSceneTouchEvent.isActionUp()) {
					bf.Stop();
				}

				return true;
			};
		};
		shotButton = new GameButton(650, 280) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					bf.shot();
				} else if (pSceneTouchEvent.isActionUp()) {
					bf.Stop();
				}
				return true;
			};
		};

        health =  new ChangeableText(0, 10, GfxAssets.healthFont, "-", 5);
        score = new ChangeableText(GameActivity.CAMERA_WIDTH - 100,10,GfxAssets.healthFont,"0",5);

		hud = new Hud(health,score) {
			@Override
			public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
				return super.onSceneTouchEvent(pSceneTouchEvent);
			}
		};


		hud.attachChild(topButton);
		hud.attachChild(leftButton);
		hud.attachChild(rightButton);
		hud.attachChild(shotButton);
		hud.attachChild(bottomButton);
		hud.registerTouchArea(leftButton);
		hud.registerTouchArea(rightButton);
		hud.registerTouchArea(bottomButton);
		hud.registerTouchArea(topButton);
		hud.registerTouchArea(shotButton);
		// ----------------------------------
		setTouchAreaBindingEnabled(true);
 	}

    public void show() {
        show(1);
    }

	public void show(int level) {
        points = 0;
        score.setText("");
        pickedLevel = level;

		bf = new Battlefield(this, level);

		for (Entity tmp : bf.getSpritesList()) {
			attachChild(tmp);
		}

		Refer._this.mCamera.setHUD(hud);

        clearChildScene();

		setVisible(true);
		Refer._this.mCamera.reset();
		setIgnoreUpdate(false);
	}

	public void hide() {
		setVisible(false);
		setIgnoreUpdate(true);
		if (bf != null) {
			bf.finish();
			clean();
			hud.setCamera(null);

			Refer._this.runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					Refer._this.mCamera.setCenter(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2);
				}
			});
		}
		bf = null;
	}

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		// Refer._this.mCamera.setCenter(Refer._this.mCamera.getCenterX() + 10,
		// Refer._this.mCamera.getCenterY() + 10);
		return super.onSceneTouchEvent(pSceneTouchEvent);

	}

	public void clean() {
		final Scene scene = this;
		Refer._this.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				scene.clearTouchAreas();
				while (scene.getChildCount() > 0) {
					Entity layer = (Entity) getChild(0);
					while (layer.getChildCount() > 0) {
						Entity obj = (Entity) layer.getChild(0);
						while (obj.getChildCount() > 0) {
							Entity sg_child = (Entity) obj.getChild(0);
							obj.detachChild(sg_child);
							sg_child.setIgnoreUpdate(true);
							sg_child = null;
						}
						layer.detachChild(obj);
						obj.setIgnoreUpdate(true);
						obj = null;
					}
					scene.detachChild(layer);
					layer.setIgnoreUpdate(true);
					layer = null;
				}
			}
		});
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (bf != null && !bf.PAUSE) {
			Refer._this.mCamera.setCenter(bf.getPlayer().getSprite().getX() + BaseVehicle.SIZE / 2, bf.getPlayer()
					.getSprite().getY()
					+ BaseVehicle.SIZE / 2);
			bf.update();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

    public void nextLevel() {
         show(pickedLevel + 1);
    }
    public void restartLevel(){
        show(pickedLevel);
    }

    public void unpause(){
        clearChildScene();
        if(bf != null){
            bf.unpause();
        }
    }

    public void pause() {
        if(!bf.PAUSE){
            Refer._this.runOnUpdateThread(new Runnable() {
                @Override
                public void run() {
                    Refer._this.mCamera.reset();

                }
            });
            setChildScene(new PauseScene(this), false, true, true);
            bf.setPause();
        }
    }

    public void finishDialog(){
        Refer._this.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                Refer._this.mCamera.reset();
                Refer._this.mCamera.setCenter(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2);
            }
        });
        GameActivity.recordDS.open();
        GameActivity.recordDS.addRecord(points);
        GameActivity.recordDS.close();

        setChildScene(new FinalScene(this), false, true, true);
    }
    public void botKill(Bot vh){
        points += vh.WORTH;
        score.setText(points+"");
    }
    @Override
    public int getCode() {
        return CODE;
    }
}
