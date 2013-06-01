package mva.andengine;

import java.util.LinkedList;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import mva.andengine.bot.Bot;

public class EnemyList extends LinkedList<Bot> {

    private int releasedBots = 0;

	public void update(Battlefield battlefield) {
        if(this.size() < battlefield.BOT_SAME_TIME_COUNT && releasedBots < battlefield.BOT_COUNT){

            releasedBots++;
            add(battlefield.takeOrCreateBot());
        }
        else if(this.size() == 0 && releasedBots == battlefield.BOT_COUNT){
            battlefield.gameScene.nextLevel();
        }

		for (Bot bot : this) {
			bot.update();
		}
	}

	public LinkedList<AnimatedSprite> getSprites() {
		LinkedList<AnimatedSprite> ls = new LinkedList<AnimatedSprite>();
		for (Bot bh : this) {
			ls.add(bh.getSprite());
		}
		return ls;
	}

	@Override
	public boolean add(Bot object) {
		object.setRootList(this);
        object.getSprite().setVisible(true);
        object.RandomPosition();
        object.getSprite().setIgnoreUpdate(false);
		return super.add(object);
	}
}
