package lt.ernyz.sidescroller;

import com.badlogic.gdx.graphics.Texture;

public class Entity {
	private Texture texture;
	protected float x;
	protected float y;
	protected float width;
	protected float height;

	public Entity(Texture texture, float x, float y) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
}
