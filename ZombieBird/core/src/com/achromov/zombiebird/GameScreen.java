package com.achromov.zombiebird;

import com.achromov.gameworld.GameRenderer;
import com.achromov.gameworld.GameWorld;
import com.achromov.zbhelpers.InputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by User on 04.05.2017.
 */
public class GameScreen implements Screen{

    private float runTime = 0;
    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    public GameScreen() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);

        gameWorld = new GameWorld(midPointY);
        gameRenderer = new GameRenderer(gameWorld, (int)gameHeight, midPointY);

        Gdx.input.setInputProcessor(new InputHandler(gameWorld));
}

    @Override
    public void render(float delta) {
        runTime += delta;
        gameWorld.update(delta);
        gameRenderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
