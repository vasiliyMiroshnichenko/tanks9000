package mva.andengine.scene;

import android.util.Log;
import mva.andengine.GameActivity;
import mva.andengine.GfxAssets;
import mva.andengine.R;
import mva.andengine.Refer;
import mva.andengine.buttons.MainMenuItem;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.animator.AlphaMenuAnimator;
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 19.05.13
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class FinalScene extends MenuScene {

    private GameScene _gameScene;

    public FinalScene(GameScene gameScene){
        super(Refer._this.mCamera);

        _gameScene = gameScene;

        TextMenuItem item = new TextMenuItem(0, GfxAssets.finalMenuFont, Refer._this.getResources().getString(R.string.restore_new_game)) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                _gameScene.restartLevel();
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        TextMenuItem item2 = new TextMenuItem(0, GfxAssets.finalMenuFont, Refer._this.getResources().getString(R.string.exit)) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                GameActivity.mainState.ShowMainMenuScene();
                //_gameScene.hide();
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        addMenuItem(item);
        addMenuItem(item2);

        setMenuAnimator(new AlphaMenuAnimator());
        buildAnimations();

        setBackgroundEnabled(false);
    }
    public void show() {
        setVisible(true);
        setIgnoreUpdate(false);
    }

    public void hide() {
        setVisible(false);
        setIgnoreUpdate(true);

    }
}
