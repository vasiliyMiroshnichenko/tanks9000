package mva.andengine.bullets;

import java.util.LinkedList;

public class BulletPool extends LinkedList<Bullet> {

	@Override
	public Bullet getFirst() {
		if (isEmpty()) {
			Bullet bullet = new Bullet(0, 0, Bullet.TOP_DIRECTION);
			return bullet;
		} else return super.getFirst();
	}
}
