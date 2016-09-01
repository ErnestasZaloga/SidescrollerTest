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
//		player.getVelocity().y = GRAVITY;
		
		if(Gdx.input.isKeyPressed(Keys.A)) {
			player.x -= 1;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			player.x += 1;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
//			player.y -= 1;
		}
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			player.getVelocity().y = 200;
		}
		
		//Update entities
		player.update(delta);
		
		//Check collisions
		checkCollisions();
		
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
//		tiles.add(new Entity(new Texture("grass.png"), 100, 0));
//		tiles.add(new Entity(new Texture("grass.png"), 100, 100));
		for(int i = 0; i < 15; i++) {
			tiles.add(new Entity(new Texture("grass.png"), i*32, 0));
		}
	}
	
	private void checkCollisions() {
		x5 = 0;
		y5 = 0;
		x6 = 0;
		y6 = 0;
		for(Entity t : tiles) {
			float intersectionW = 0f;
			float intersectionH = 0f;
			
			if(player.getX() < t.getX()+t.getWidth() && player.getX()+player.getWidth() > t.getX()
					&& player.getY() < t.getY()+t.getHeight() && player.getY()+player.getHeight() > t.getY()) {

				float x1 = player.getX(); float x2 = player.getX()+player.getWidth();
				float y1 = player.getY(); float y2 = player.getY()+player.getHeight();
				
				float x3 = t.getX(); float x4 = t.getX()+t.getWidth();
				float y3 = t.getY(); float y4 = t.getY()+t.getHeight();
				
				x5 = Math.max(x1, x3);
				y5 = Math.max(y1, y3);
				x6 = Math.min(x2, x4);
				y6 = Math.min(y2, y4);
				
				intersectionW = x6-x5;
				intersectionH = y6-y5;
				
				if(intersectionW >= intersectionH) {
					if(player.getX() < t.getX() + t.getWidth()) {
//						player.setX(player.getX() + intersectionW);
					} else if(player.getX() + player.getWidth() > t.getX()) {
//						player.setX(player.getX() - intersectionW);
					}
					player.getVelocity().x = 0f;
				} else {
					if(player.getY() + player.getHeight() > t.getY()) {
						player.setY(player.getY() - intersectionH);
					} else if(player.getY() < t.getY() + t.getHeight()) {
						player.setY(player.getY() + intersectionH);
					}
					player.getVelocity().y = 0f;
				}
				
//				player.getVelocity().y = 0f;
//				player.setY(player.getY()+intersectionH);
			}
			
			/*if(((t.getX() < player.getX() && player.getX() < t.getX() + t.getWidth()) || (t.getX() < player.getX() + player.getWidth() && player.getX() + player.getWidth() < t.getX() + t. getWidth()))
					&& ((t.getY() + t.getHeight() < player.getY() && player.getY() + player.getHeight() < t.getY()) || (t.getY() + t.getHeight() < player.getY() && player.getY() < t.getY() + t.getHeight()))) {
				System.out.println("intersection!");
				if(player.getX() < t.getX() + t.getWidth()) {
					intersectionW = t.getX() + t.getWidth() - player.getX();
				} else if(player.getX() + player.getWidth() > t.getX()) {
					intersectionW = player.getX() + player.getWidth() - t.getX();
				}
				
				if(player.getY() + player.getHeight() > t.getY()) {
					intersectionH = player.getY() + player.getHeight() - t.getY();
				} else if(player.getY() < t.getY() + t.getHeight()) {
					intersectionH = t.getY() + t.getHeight() - player.getY();
				}
				
				if(intersectionW >= intersectionH) {
					if(player.getX() < t.getX() + t.getWidth()) {
						player.setX(player.getX() + intersectionW);
					} else if(player.getX() + player.getWidth() > t.getX()) {
						player.setX(player.getX() - intersectionW);
					}
				} else {
					if(player.getY() + player.getHeight() > t.getY()) {
						player.setY(player.getY() - intersectionH);
					} else if(player.getY() < t.getY() + t.getHeight()) {
						player.setY(player.getY() + intersectionH);
					}
					player.getVelocity().y = 0f;
				}
			}*/
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
