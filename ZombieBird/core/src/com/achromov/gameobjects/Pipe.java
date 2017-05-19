package com.achromov.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by User on 05.05.2017.
 */
public class Pipe extends Scrollable {
    private Random random = new Random();

    private Rectangle skullUp = new Rectangle()
                    , skullDown = new Rectangle()
                    , barUp = new Rectangle()
                    , barDown = new Rectangle();

    public static final int VERTICAL_GAP = 45;
    public static final int SKULL_WIDTH = 24;
    public static final int SKULL_HEIGHT = 14;
    private float groundY;
    public boolean isScored;

    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        this.groundY = groundY;
        isScored = false;
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        isScored = false;
        height = random.nextInt(75) + 15;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
                groundY - (position.y + height + VERTICAL_GAP));

        skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
                - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
        skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
                SKULL_WIDTH, SKULL_HEIGHT);
    }

    public Rectangle getSkullUp() {
        return skullUp;
    }

    public Rectangle getSkullDown() {
        return skullDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }

    @Override
    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
                    || Intersector.overlaps(bird.getBoundingCircle(), barDown)
                    || Intersector.overlaps(bird.getBoundingCircle(), skullUp)
                    || Intersector.overlaps(bird.getBoundingCircle(), skullDown)
            );
        }
        return false;
    }

    @Override
    public boolean isPassed(Bird bird) {
        if ((position.x + width < bird.getX() + (bird.getWidth() / 2)) && !isScored) {
            isScored = true;
            return true;
        }
        return false;
    }
}
