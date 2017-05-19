package com.achromov.gameworld;

import com.achromov.gameobjects.Bird;
import com.achromov.gameobjects.ScrollHandler;
import com.achromov.zbhelpers.Asset;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by User on 04.05.2017.
 */
public class GameWorld {

    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    private GameState currentState;
    private Bird bird;
    private ScrollHandler scrollHandler;
    private int midPointY;
    private int score = 0;

    public GameWorld(int midPointY) {
        this.midPointY = midPointY;
        bird = new Bird(33,midPointY - 5, 17,12);
        scrollHandler = new ScrollHandler(midPointY + 66);
        currentState = GameState.READY;
    }

    public void update(float delta) {

        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case GAMEOVER:
            default:
                updateRunning(delta);
                break;
        }

    }

    private void updateReady(float delta) {
        // Пока что ничего не делаем
    }

    public void updateRunning(float delta) {
        bird.update(delta);
        scrollHandler.update(delta);

        if (scrollHandler.collides(bird) && bird.isAlive()) {
            scrollHandler.stop();
            bird.killBird();
            Asset.dead.play();
            currentState = GameState.GAMEOVER;

            if (score > Asset.getHighScore()) {
                Asset.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }

        if (scrollHandler.isPipePassed(bird) && bird.isAlive()) {
            score++;
            //Gdx.app.log("score: ", score + "");
            Asset.coin.play();
        }
    }

    public Bird getBird() {
        return bird;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public int getScore() {
        return score;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        bird.onRestart(midPointY - 5);
        scrollHandler.onRestart();
        currentState = GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
}
