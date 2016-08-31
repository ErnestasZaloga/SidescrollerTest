package lt.ernyz.sidescroller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class SidescrollerTest extends ApplicationAdapter {
//	private final float GRAVITY = -9.8f;
	private final float GRAVITY = -6f;
	
	private Player player;
	private Array<Entity> tiles = new Array<Entity>();
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
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
//		player.getVelocity().y += GRAVITY;
		player.getVelocity().y = GRAVITY;
		
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
	}
	
	private void setupLevel() {
		player = new Player(new Texture("player.png"), 100, 50);
		for(int i = 0; i < 15; i++) {
			tiles.add(new Entity(new Texture("grass.png"), i*32, 0));
		}
	}
	
	private void checkCollisions() {
		for(Entity t : tiles) {
			float intersectionW = 0f;
			float intersectionH = 0f;
			
			/*if (RectA.Left < RectB.Right && RectA.Right > RectB.Left &&
     RectA.Top < RectB.Bottom && RectA.Bottom > RectB.Top ) */
			if(player.getX() < t.getX()+t.getWidth() && player.getX()+player.getWidth() > t.getX()
					&& player.getY() < t.getY()+t.getHeight() && player.getY()+player.getHeight() > t.getY()) {

				if(player.getY() > t.getY()) {
					intersectionH = t.getY() - player.getY()+player.getHeight();
				} else {
					intersectionH = player.getY() - t.getY()+t.getHeight();
				}
				System.out.println(intersectionH);
				
				player.getVelocity().y = 0f;
				player.setY(t.getY()+intersectionH);
				
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
