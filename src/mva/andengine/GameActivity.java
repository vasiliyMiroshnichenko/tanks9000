package mva.andengine;

import android.os.Bundle;
import android.view.KeyEvent;
import mva.andengine.db.RecordDataSource;
import mva.andengine.scene.MainState;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class GameActivity extends BaseGameActivity {

	public boolean gameLoaded = false;
	public Camera mCamera;
	public Engine mEngine;
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	public static MainState mainState;
    public static RecordDataSource recordDS;


	private static ScreenOrientation _orientation = ScreenOrientation.LANDSCAPE;

    public GameActivity(){
        recordDS  = new RecordDataSource(this);
    }


	@Override
	public void onCreate(Bundle savedInstanceState) {
		Refer._this = this;
		super.onCreate(savedInstanceState);
	}

	@Override
	public Engine onLoadEngine() {

		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mEngine = new Engine(
				new EngineOptions(true, _orientation,
						new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
						mCamera).setNeedsMusic(true).setNeedsSound(true));
		return mEngine;
	}

	@Override
	public void onLoadResources() {
		mEngine.registerUpdateHandler(new FPSLogger());
		GfxAssets.loadTextures();
		GfxAssets.loadFonts();
		GfxAssets.loadMusic();
	}

	@Override
	public Scene onLoadScene() {
		mainState = new MainState();
		return mainState;
	}

	@Override
	public void onLoadComplete() {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			mainState.backButton();
			return true;
		}
        else return false;
	}
}
