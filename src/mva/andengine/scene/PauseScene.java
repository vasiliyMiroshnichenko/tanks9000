package mva.andengine.scene;

import mva.andengine.GameActivity;
import mva.andengine.GfxAssets;
import mva.andengine.R;
import mva.andengine.Refer;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.animator.AlphaMenuAnimator;
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 22.05.13
 * Time: 2:36
 * To change this template use File | Settings | File Templates.
 */
public class PauseScene extends MenuScene {

    private GameScene _gameScene;

    public PauseScene(GameScene gameScene){
        super(Refer._this.mCamera);

        _gameScene = gameScene;
       //
        Refer._this.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                Refer._this.mCamera.reset();
            }
        });

        TextMenuItem item = new TextMenuItem(0, GfxAssets.finalMenuFont, Refer._this.getResources().getString(R.string.restore)) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                _gameScene.unpause();
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        TextMenuItem item2 = new TextMenuItem(0, GfxAssets.finalMenuFont, Refer._this.getResources().getString(R.string.exit)) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                GameActivity.mainState.ShowMainMenuScene();
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        addMenuItem(item);
        addMenuItem(item2);

        setMenuAnimator(new AlphaMenuAnimator());
        buildAnimations();

        setBackgroundEnabled(false);
    }
}
