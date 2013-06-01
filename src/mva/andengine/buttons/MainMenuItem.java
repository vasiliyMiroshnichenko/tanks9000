package mva.andengine.buttons;
 
import mva.andengine.GfxAssets;
import org.anddev.andengine.entity.text.Text;

public class MainMenuItem extends Text {

	public MainMenuItem(float x, float y, String txt) {
		super(x, y, GfxAssets.mainMenuFont, txt);
	}
}
