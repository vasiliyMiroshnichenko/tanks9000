package mva.andengine.scene;

import android.util.Log;
import mva.andengine.GameActivity;
import mva.andengine.GfxAssets;
import mva.andengine.Refer;
import mva.andengine.buttons.RecordItem;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.sprite.Sprite;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 25.05.13
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */
public class HighScoreScene extends CameraScene implements ISwitchableScene {

    private Sprite background;
    public final static int CODE = MainState.HIGH_SCORE_SCENE;
    private final MainState mainState;
    private ArrayList<RecordItem> recList = new ArrayList<RecordItem>();
    private int yCoord = 30;
    private int coordStep = GameActivity.CAMERA_HEIGHT / 12;

    public HighScoreScene(final MainState mainState) {
        super(Refer._this.mCamera);
        this.mainState = mainState;

        background = new Sprite(0, 0, GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT, GfxAssets.mainMenuBackground);
        attachChild(background);
    }

    private int[] getRecords() {
        GameActivity.recordDS.open();
        int []records = GameActivity.recordDS.getRecords(10);
        GameActivity.recordDS.close();
        return records;
    }

    public void show() {

        for(RecordItem ri : recList)
            detachChild(ri);

        int[] records = getRecords();

        int curYCoord = yCoord;
        RecordItem ri;
        for(int i = 0; i < records.length; i++){
            if (i < 9)
                ri = new RecordItem(40,curYCoord, (i + 1) + ".  " +records[i]);
            else
                ri = new RecordItem(40,curYCoord, (i + 1) + ". " +records[i]);
            recList.add(ri);
            ri.setColor(0,0,.1f);
            attachChild(ri);
            curYCoord += coordStep;
        }
        setVisible(true);
        setIgnoreUpdate(false);
    }

    public void hide() {
        setVisible(false);
        setIgnoreUpdate(true);
    }

    @Override
    public int getCode() {
        return CODE;
    }
}
