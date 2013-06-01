package mva.andengine.bullets;

import java.util.LinkedList;

public class BulletList extends LinkedList<Bullet> {
	private final BulletList list = this;

	@Override
	public boolean add(Bullet object) {
		object.setRootList(this);
		return super.add(object);
	}

	public void update() {
		for (int i = 0; i < this.size(); i++) {
			this.get(i).update();
		}
	}
}
