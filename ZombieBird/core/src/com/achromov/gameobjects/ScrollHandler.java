package com.achromov.gameobjects;

import com.badlogic.gdx.math.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 05.05.2017.
 */
public class ScrollHandler {
    ScrollableBar pipesBar;
    ScrollableBar grassBar;

    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 70;
    private float yGrassPos;

    public ScrollHandler(float yPos) {
        yGrassPos = yPos;
        Grass frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        Grass backGrass = new Grass(143, yPos, 143, 11,
                SCROLL_SPEED);

        Pipe pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
        Pipe pipe2 = new Pipe(232 + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        Pipe pipe3 = new Pipe(254 + 2 * PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);

        List<Scrollable> pipes = new ArrayList();
        List<Scrollable> grasses = new ArrayList();

        pipes.add(pipe1);
        pipes.add(pipe2);
        pipes.add(pipe3);

        grasses.add(frontGrass);
        grasses.add(backGrass);

        pipesBar = new ScrollableBar(PIPE_GAP, pipes);
        grassBar = new ScrollableBar(0, grasses);
    }

    public void update(float delta) {
        pipesBar.update(delta);
        grassBar.update(delta);
    }

    public ScrollableBar getPipesBar() {
        return pipesBar;
    }

    public ScrollableBar getGrassBar() {
        return grassBar;
    }

    public void stop() {
        grassBar.stop();
        pipesBar.stop();
    }
    // вернуть True если какая-нибудь из труб коснулась птицы
    public boolean collides(Bird bird) {

        Circle birdBoundingCircle = bird.getBoundingCircle();
        return pipesBar.collides(bird) ||  birdBoundingCircle.radius + birdBoundingCircle.y > yGrassPos;
    }

    public boolean isPipePassed(Bird bird) {
        return pipesBar.isSomeObjectPassed(bird);
    }

    public void onRestart() {
        int[] xPipeCoord = {210, 232 + PIPE_GAP, 254 + 2 * PIPE_GAP};
        int[] xGrassCoord = {0, 143};
        pipesBar.onRestart(xPipeCoord);
        grassBar.onRestart(xGrassCoord);
    }
}
