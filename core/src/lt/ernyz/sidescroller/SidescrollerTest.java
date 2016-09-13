package lt.ernyz.sidescroller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class SidescrollerTest extends ApplicationAdapter {
	
	float x5 = 0;
	float y5 = 0;
	float x6 = 0;
	float y6 = 0;
	
	private final float GRAVITY = -9.8f;
//	private final float GRAVITY = -6f;
	
	private Player player;
	private Array<Entity> tiles = new Array<Entity>();
	private SpriteBatch batch;
	
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		setupLevel();
	}

	@Override
	public void render() {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		float delta = Gdx.graphics.getDeltaTime();
		/*Update world*/
		//Apply gravity
		player.getVelocity().y += GRAVITY;
		
		if(Gdx.input.isKeyPressed(Keys.A)) {
			player.getVelocity().x = -100;
		} else if(Gdx.input.isKeyPressed(Keys.D)) {
			player.getVelocity().x = 100;
		} else {
			player.getVelocity().x = 0;
		}
//		if(Gdx.input.isKeyPressed(Keys.S)) {
//			player.y -= 1;
//		}
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			player.getVelocity().y = 200;
		}
//		if(Gdx.input.isKeyPressed(Keys.W)) {
//			player.y += 1;
//		}
		
		//Update entities
		player.update(delta);
		
		//Check collisions
		checkCollisions(delta);
		
		//Render
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for(Entity t : tiles) {
			batch.draw(t.getTexture(), t.getX(), t.getY());
		}
		batch.draw(player.getTexture(), player.getX(), player.getY());
		batch.end();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.CYAN);
		shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
		
		shapeRenderer.setColor(Color.GOLD);
		for(Entity t : tiles) {
			shapeRenderer.rect(t.getX(), t.getY(), t.getWidth(), t.getHeight());
		}
		
		shapeRenderer.setColor(Color.RED);
		if(x5 != 0 && y5 != 0 && x6 != 0 && y6 != 0) {
			shapeRenderer.rect(x5, y5, x6-x5, y6-y5);
			shapeRenderer.circle(x5, y5, 5);
			shapeRenderer.circle(x6, y6, 5);
		}
		shapeRenderer.end();
	}
	
	private void setupLevel() {
		player = new Player(new Texture("player.png"), 100, 40);
		tiles.add(new Entity(new Texture("grass.png"), 100, 100));
		for(int i = 0; i < 15; i++) {
			tiles.add(new Entity(new Texture("grass.png"), i*32, 0));
		}
		
		Entity t = new Entity(new Texture("grass.png"), 200, 40);
		t.setHeight(5f); t.setWidth(100f);
		tiles.add(t);
		
	}
	
	private void checkCollisions(float delta) {
		float lastX = player.getX();
		float lastY = player.getY();
		float dx = player.getVelocity().x * delta;
		float dy = player.getVelocity().y * delta;
		
		int numSteps = 5;
		float stepX = dx / numSteps;
		float stepY = dy / numSteps;
		
		for(int i = 0; i < numSteps; i++) {
			//X axis
			player.setX(player.getX() + stepX);
			for(Entity t : tiles) {
				if(player.getX() < t.getX()+t.getWidth() && player.getX()+player.getWidth() > t.getX()
						&& player.getY() < t.getY()+t.getHeight() && player.getY()+player.getHeight() > t.getY()) {
					player.setX(lastX);
					break;
				}
			}
			
			//Y axis
			player.setY(player.getY() + stepY);
			for(Entity t : tiles) {
				if(player.getX() < t.getX()+t.getWidth() && player.getX()+player.getWidth() > t.getX()
						&& player.getY() < t.getY()+t.getHeight() && player.getY()+player.getHeight() > t.getY()) {
					player.setY(lastY);
					player.getVelocity().y = 0;
					break;
				}
			}
			
			lastX = player.getX();
			lastY = player.getY();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
