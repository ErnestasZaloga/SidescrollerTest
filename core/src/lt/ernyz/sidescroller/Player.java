package lt.ernyz.sidescroller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
	
	private Vector2 velocity = new Vector2(0f, 0f);

	public Player(Texture texture, float x, float y) {
		super(texture, x, y);
	}

	public void update(float delta) {
		this.x += velocity.x * delta;
		this.y += velocity.y * delta;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

}
