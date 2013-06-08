package mva.andengine.scene;

import java.util.LinkedList;

import mva.andengine.GameActivity;
import mva.andengine.buttons.BackButton;

import mva.andengine.GfxAssets;
import mva.andengine.Refer;
import mva.andengine.XmlLevelParser;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;

public class LevelScene extends CameraScene implements ISwitchableScene {
	private Sprite background;
	private BackButton back;//TODO
    public final static int CODE = MainState.SELECT_LEVEL;
	private float xCoordinates = 50;
	private float yCoordinates = 50;

	private float step = 25;

	private LinkedList<LevelButton> buttons = new LinkedList<LevelButton>();
	private MainState mainState;

	public LevelScene(MainState mainState) {
		super(Refer._this.mCamera);
		this.mainState = mainState;
		
		background = new Sprite(0, 0, GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT, GfxAssets.mainMenuBackground);
		attachChild(background);
		
		XmlLevelParser parser = new XmlLevelParser(1);
		int count = parser.getLevelCount();	

		if (0 == count)
			return;

        createMenu(count);
    }

    private void createMenu(int count) {
        for (int i = 0; i <= 30; i += 10) {
            for (int j = 1; j <= 10; j++) {
                if (i + j > count)
                    return;
                LevelButton lb = new LevelButton(i + j, xCoordinates + (j - 1) * (LevelButton.SIZE + step), yCoordinates + (i / 10)
                        * (LevelButton.SIZE + step));
                //buttons.add(lb);
                attachChild(lb);
                registerTouchArea(lb);
            }
        }
    }

    @Override
    public int getCode() {
        return CODE;
    }

    public void show() {
		setVisible(true);
		setIgnoreUpdate(false);
		/*for(LevelButton lb : buttons)
			lb.setVisible(true);*/
	}

	public void hide() {
		setVisible(false);
		setIgnoreUpdate(true);
	}

    public class LevelButton extends Sprite {
        private Text text;
        public final static float SIZE = 50;
        private final int level;

        // TODO size
        public LevelButton(int num, float pX, float pY) {
            super(pX, pY, SIZE, SIZE, GfxAssets.lvlbutton);

            level = num;
            if (num > 9)
                text = new Text(SIZE / 4, SIZE / 4, GfxAssets.levelFont, String.valueOf(num));
            else
                text = new Text(SIZE / 3, SIZE / 4, GfxAssets.levelFont, String.valueOf(num));
            attachChild(text);
        }

        @Override
        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
            mainState.ShowGameScene(level);
            return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
        }

    }

}
