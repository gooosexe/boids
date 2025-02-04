package io.github.boids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer renderer;
    private BitmapFont font;
    private SpriteBatch batch;
    private Flock flock;

    @Override
    public void create() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        flock = new Flock(500, 0.5f, 0.1f, 0.03f, 50, 2);
        Gdx.graphics.setForegroundFPS(120); // Cap at 120 FPS
    }

    @Override
    public void render() {
        //ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);
        batch.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(1, 1, 1, 1);
        flock.update();
        flock.render(renderer);
        renderer.end();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
