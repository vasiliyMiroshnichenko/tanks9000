package mva.andengine;

import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Color;
import android.util.Log;

public class GfxAssets {

	// public static TextureRegion sprite;
	// public static Font font;
    public static Font mainMenuFont;
    public static Font finalMenuFont;
    public static Font healthFont;

    public static TiledTextureRegion tank;
    public static TiledTextureRegion playerTank;
	// public static TiledTextureRegion brick;

	private static TextureRegion greenField;
	private static TextureRegion sandField;

	public static TextureRegion bullet;

	public static TextureRegion brick100;
	public static TextureRegion brick50;
    public static TextureRegion brick1;
    public static TextureRegion stone;
    public static TextureRegion eagle;
    public static TextureRegion water;

	public static TextureRegion button;
    public static TextureRegion blow ;
	public static Music engine;
	public static Music shot;
	public static Music idling;

	public static TextureRegion mainMenuBackground;

	public static Font levelFont;
	public static TextureRegion lvlbutton;

	public static void loadTextures() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		final BitmapTextureAtlas Texture1 = new BitmapTextureAtlas(512, 256, TextureOptions.NEAREST_PREMULTIPLYALPHA);
		final BitmapTextureAtlas TextureField = new BitmapTextureAtlas(2048, 1024, TextureOptions.DEFAULT);
		final BitmapTextureAtlas MainMenuTextureField = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);

		tank = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(Texture1, Refer._this, "tank.png", 0, 0, 2,
				1);
        playerTank = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(Texture1, Refer._this, "maintank.png", 400, 0, 2,1);
		// brick =menuBg.jpg
		// BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(Texture1,
		// Refer._this, "brick.png", 0, 150, 3, 1);

		brick100 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "brick100.png", 0, 50);
		brick50 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "brick50.png", 50, 50);
        brick1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "brick1.png", 100, 50);

        eagle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "eagle.png", 300, 0);
        stone = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "stone.png", 350, 0);
        water = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "water.png", 350, 50);

		bullet = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "bullet.png", 50, 100);
		button = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "button.png", 0, 100);
        lvlbutton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "lvlButton.png", 0,
                200);
        blow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture1, Refer._this, "blow.png", 250, 0);

		greenField = BitmapTextureAtlasTextureRegionFactory.createFromAsset(TextureField, Refer._this, "bf.jpg", 0, 0);
		sandField = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(TextureField, Refer._this, "desert.jpg", 1024, 0);

		mainMenuBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MainMenuTextureField, Refer._this,
				"menuBg.jpg", 0, 0);

		Refer._this.mEngine.getTextureManager().loadTextures(Texture1, TextureField, MainMenuTextureField);
	}

	public static void loadFonts() {
		// BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("fonts/");

        final BitmapTextureAtlas Texture2 = new BitmapTextureAtlas(512, 512, TextureOptions.DEFAULT);
        final BitmapTextureAtlas Texture4 = new BitmapTextureAtlas(256, 512, TextureOptions.DEFAULT);
        final BitmapTextureAtlas Texture5 = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);
		final BitmapTextureAtlas Texture3 = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);

		// font = FontFactory.createFromAsset(Texture2, Refer._this,
		// "fonts/MeteoritDeco.ttf", 16, true, Color.WHITE);
		mainMenuFont = FontFactory.createFromAsset(Texture2, Refer._this, "fonts/PIONEERN.TTF", 40, true,
				Color.rgb(255, 124, 0));
		levelFont = FontFactory.createFromAsset(Texture3, Refer._this, "fonts/PIONEERN.TTF", 22, true,
				Color.rgb(255, 144, 0));
        finalMenuFont = FontFactory.createFromAsset(Texture4, Refer._this, "fonts/B52.ttf", 60, true,
                Color.rgb(0, 0, 0));
        healthFont  = FontFactory.createFromAsset(Texture5, Refer._this, "fonts/B52.ttf", 30, true,
                Color.rgb(245, 0, 0));
		Refer._this.mEngine.getTextureManager().loadTextures(Texture2, Texture3, Texture4,Texture5);
		Refer._this.mEngine.getFontManager().loadFonts(mainMenuFont, levelFont,finalMenuFont,healthFont);
	}

	public static void loadMusic() {
		MusicFactory.setAssetBasePath("mfx/");
		try {
			idling = MusicFactory.createMusicFromAsset(Refer._this.getMusicManager(), Refer._this, "idling.wav");
			shot = MusicFactory.createMusicFromAsset(Refer._this.getMusicManager(), Refer._this, "shot.wav");
			engine = MusicFactory.createMusicFromAsset(Refer._this.getMusicManager(), Refer._this, "engine.wav"); // Dodge
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static TextureRegion getField(int num) {		
		switch (num) {
		case 1:
			return greenField;
		case 2:
			return sandField;
		default:
			Log.v("pzdc", "GFXAssets");
			return greenField;
		}
	}
}
