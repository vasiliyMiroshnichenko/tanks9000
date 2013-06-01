package mva.andengine.scene;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.entity.text.ChangeableText;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 22.05.13
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
public class Hud extends HUD {
    public Hud(ChangeableText health,ChangeableText score){
        super();
        attachChild(health);
        attachChild(score);
    }
}
