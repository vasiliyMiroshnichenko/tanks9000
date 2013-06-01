package mva.andengine.buttons;

import javax.microedition.khronos.opengles.GL10;

import mva.andengine.GfxAssets;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;



public class GameButton extends Sprite {

	private final static float SIZE = 120;
	public GameButton(float pX, float pY) {
		
		super(pX, pY, SIZE, SIZE, GfxAssets.button);
		
		setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		setAlpha(0.3f);
		// TODO Auto-generated constructor stub
	}

}
