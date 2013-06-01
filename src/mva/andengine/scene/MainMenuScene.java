package mva.andengine.scene;

import android.util.Log;
import mva.andengine.GameActivity;
import mva.andengine.GfxAssets;
import mva.andengine.R;
import mva.andengine.buttons.MainMenuItem;
import mva.andengine.Refer;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import java.util.Random;

public class MainMenuScene extends CameraScene implements ISwitchableScene {

    public final static int CODE = MainState.MAIN_MENU;
	private Sprite background;
	private Sprite exit;

	public MainMenuScene(final MainState mainState) {
		super(Refer._this.mCamera);

		background = new Sprite(0, 0, GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT, GfxAssets.mainMenuBackground);

		MainMenuItem mItem = new MainMenuItem(40, 40, Refer._this.getResources().getString(R.string.new_game)) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                mainState.ShowGameScene(1);
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
        MainMenuItem mItem1 = new MainMenuItem(40, 100, Refer._this.getResources().getString(R.string.levels)) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                mainState.ShowChooseLevelScene();
                return true;
            }
        };
        MainMenuItem mItem2 = new MainMenuItem(40, 160, Refer._this.getResources().getString(R.string.high_score)) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                mainState.ShowHighScoreScene();
                return true;
            }
        };
		MainMenuItem mItem3 = new MainMenuItem(40, 220, Refer._this.getResources().getString(R.string.menu_exit)) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				android.os.Process.killProcess(android.os.Process.myPid());
				return true;
			}
		};

		attachChild(background);
		attachChild(mItem);
		attachChild(mItem1);
        attachChild(mItem2);
        attachChild(mItem3);

		registerTouchArea(mItem);
		registerTouchArea(mItem1);
        registerTouchArea(mItem2);
        registerTouchArea(mItem3);
	}

	public void show() {
		setVisible(true);
		setIgnoreUpdate(false);
		setPosition(Refer._this.mCamera.getMinX(), Refer._this.mCamera.getMinY());
		//Refer._this.mCamera.reset();
		Refer._this.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				setPosition(Refer._this.mCamera.getMinX(), Refer._this.mCamera.getMinY());

			}
		});
	}

	public void hide() {
		setVisible(false);
		setIgnoreUpdate(true);
	}

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

    @Override
    public int getCode() {
        return CODE;
    }
}
