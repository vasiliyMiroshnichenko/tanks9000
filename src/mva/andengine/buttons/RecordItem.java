package mva.andengine.buttons;

import mva.andengine.GfxAssets;
import org.anddev.andengine.entity.text.Text;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 26.05.13
 * Time: 0:25
 * To change this template use File | Settings | File Templates.
 */
public class RecordItem extends Text {

    public RecordItem(float x, float y, String txt) {
        super(x, y, GfxAssets.mainMenuFont, txt);
    }
}
