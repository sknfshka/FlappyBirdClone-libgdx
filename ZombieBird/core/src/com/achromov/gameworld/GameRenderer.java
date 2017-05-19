package com.achromov.gameworld;

import com.achromov.gameobjects.*;
import com.achromov.zbhelpers.Asset;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

import java.util.List;

import static com.achromov.gameworld.GameWorld.GameState.GAMEOVER;
import static com.achromov.gameworld.GameWorld.GameState.READY;


/**
 * Created by User on 04.05.2017.
 */
public class GameRenderer {

    private OrthographicCamera camera = new OrthographicCamera();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private SpriteBatch batcher = new SpriteBatch();

    private int midPointY;
    private int gameHeight;

    private GameWorld world;

    // Game Objects
    private Bird bird;
    private ScrollableBar grassBar;
    private ScrollableBar pipeBar;

    // Game Assets
    private Sprite bg, grass;
    private Animation birdAnimation;
    private Sprite birdMid;
    private Sprite skullUp, skullDown;
    private static TiledDrawable tile;

    private void initGameObjects() {
        bird = world.getBird();
        ScrollHandler scroller = world.getScrollHandler();
        grassBar = scroller.getGrassBar();
        pipeBar = scroller.getPipesBar();
    }

    private void initAssets() {
        bg = Asset.bg;
        grass = Asset.grass;
        birdAnimation = Asset.birdAnimation;
        birdMid = Asset.bird;
        skullUp = Asset.skullUp;
        skullDown = Asset.skullDown;
        tile = Asset.tile;
    }

    private void drawGrass() {
        List<Scrollable> grasses = grassBar.getScrollableObjectsObjects();

        for (Scrollable grassObject : grasses) {
            batcher.draw(grass, grassObject.getX(), grassObject.getY(),
                    grassObject.getWidth(), grassObject.getHeight());
        }
    }

    private void drawSkulls() {
        List<Scrollable> pipes = pipeBar.getScrollableObjectsObjects();

        for (Scrollable pipeObject : pipes) {
            batcher.draw(skullUp, pipeObject.getX() - 1,
                    pipeObject.getY() + pipeObject.getHeight() - Pipe.SKULL_HEIGHT, Pipe.SKULL_WIDTH, Pipe.SKULL_HEIGHT);
            batcher.draw(skullDown, pipeObject.getX() - 1,
                    pipeObject.getY() + pipeObject.getHeight() + Pipe.VERTICAL_GAP, Pipe.SKULL_WIDTH, Pipe.SKULL_HEIGHT);
        }
    }

    private void drawPipes() {
        List<Scrollable> pipes = pipeBar.getScrollableObjectsObjects();

        for (Scrollable pipeObject : pipes) {
            tile.draw(batcher, pipeObject.getX(), pipeObject.getY(), pipeObject.getWidth(), pipeObject.getHeight());
            tile.draw(batcher, pipeObject.getX(), pipeObject.getY() + pipeObject.getHeight() + Pipe.VERTICAL_GAP,
                    pipeObject.getWidth(), midPointY + 66 - (pipeObject.getHeight() + Pipe.VERTICAL_GAP));
        }
    }

    public GameRenderer(GameWorld gameWorld, int gameHeight, int midPointY) {

        this.midPointY = midPointY;
        this.gameHeight = gameHeight;
        world = gameWorld;

        camera.setToOrtho(true, 136, gameHeight);

        batcher.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        initGameObjects();
        initAssets();
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);

        // Заливаем задний фон
        shapeRenderer.setColor(153 / 255.0f, 211 / 255.0f, 255 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Рисуем Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Рисуем Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        shapeRenderer.end();

        batcher.begin();

        batcher.disableBlending();
        batcher.draw(bg, 0, midPointY + 23, 136, 43);

        drawGrass();
        drawPipes();

        batcher.enableBlending();
        drawSkulls();


        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

        } else {
            batcher.draw((TextureRegion) birdAnimation.getKeyFrame(runTime), bird.getX(),
                    bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }


        String score = world.getScore() + "";

        switch (world.getCurrentState()) {
            case READY:
                Asset.shadow.draw(batcher, "Touch me", (136 / 2)
                        - (42), 76);
                Asset.font.draw(batcher, "Touch me", (136 / 2)
                        - (42 - 1), 75);
                break;

            case GAMEOVER:
                Asset.shadow.draw(batcher, "Game Over", 25, 56);
                Asset.font.draw(batcher, "Game Over", 24, 55);

                Asset.shadow.draw(batcher, "Try again?", 23, 76);
                Asset.font.draw(batcher, "Try again?", 24, 75);

                Asset.shadow.draw(batcher, "High Score:", 23, 106);
                Asset.font.draw(batcher, "High Score:", 22, 105);

                String highScore = Asset.getHighScore() + "";

                Asset.shadow.draw(batcher, highScore, (136 / 2)
                        - (3 * highScore.length()), 128);
                Asset.font.draw(batcher, highScore, (136 / 2)
                        - (3 * highScore.length() - 1), 127);


                score = world.getScore() + "";

                Asset.shadow.draw(batcher, "" + world.getScore(), 12, 12);
                Asset.font.draw(batcher, "" + world.getScore(), 13, 11);
                break;

            case HIGHSCORE:
                Asset.shadow.draw(batcher, "High Score!", 19, 56);
                Asset.font.draw(batcher, "High Score!", 18, 55);

                score = world.getScore() + "";

                Asset.shadow.draw(batcher, "" + world.getScore(), 12, 12);
                Asset.font.draw(batcher, "" + world.getScore(), 13, 11);
                break;

            default:
                score = world.getScore() + "";

                Asset.shadow.draw(batcher, "" + world.getScore(), 12, 12);
                Asset.font.draw(batcher, "" + world.getScore(), 13, 11);
                break;
        }

        batcher.end();
    }
}
