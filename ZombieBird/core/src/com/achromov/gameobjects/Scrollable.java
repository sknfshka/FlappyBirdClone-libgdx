package com.achromov.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by User on 05.05.2017.
 */
public class Scrollable extends GameObject{
    boolean isScrolledLeft;
    float scrollSpeed;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height);
        velocity = new Vector2(scrollSpeed, 0);
        this.scrollSpeed = scrollSpeed;
        isScrolledLeft = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        // Если объект Scrollable более не виден:
        if (position.x < - width) {
            isScrolledLeft = true;
        }
    }

    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
    }

    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    public float getTailX() {
        return position.x + width;
    }

    public void stop() {
        velocity.x = 0;
    }

    public boolean collides(Bird bird) {
        return false;
    }

    public boolean isPassed(Bird bird) {
        return false;
    }

    public void onRestart(float x) {
        this.reset(x);
        velocity.x = scrollSpeed;
        isScrolledLeft = false;
    }

}
