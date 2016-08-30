package lt.ernyz.sidescroller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class SidescrollerTest extends ApplicationAdapter {
	private Player player;
	private Array<Entity> tiles = new Array<Entity>();
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setupLevel();
	}

	@Override
	public void render () {
		//Update logic
		//...
		
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
		player = new Player(new Texture("player.png"), 100, 100);
		for(int i = 0; i < 15; i++) {
			tiles.add(new Entity(new Texture("grass.png"), i*32, 0));
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
