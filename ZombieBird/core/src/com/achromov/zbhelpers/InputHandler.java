package com.achromov.zbhelpers;

import com.achromov.gameobjects.Bird;
import com.achromov.gameworld.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by User on 04.05.2017.
 */
public class InputHandler implements InputProcessor {
    private Bird bird;
    private GameWorld world;

    public InputHandler(GameWorld world) {
        this.bird = world.getBird();
        this.world = world;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (world.isReady()) {
            world.start();
        }

        bird.onClick();

        if (world.isGameOver() || world.isHighScore()) {
            // Обнулим все перменные, перейдем в GameState.READ
            world.restart();
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
