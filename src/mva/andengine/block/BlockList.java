package mva.andengine.block;

import java.util.ArrayList;

import org.anddev.andengine.entity.scene.Scene;

public class BlockList extends ArrayList<Block> {
	public synchronized void Check(Scene gameScene){
		for(Block brk : this){
			if(!brk.isAlive()){
				brk.getSprite().setVisible(false);
			}
		}
	}
}
