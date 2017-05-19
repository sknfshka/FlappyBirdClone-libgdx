package com.achromov.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by User on 05.05.2017.
 */
public class GameObject {
    Vector2 position;
    Vector2 velocity;

    int width;
    int height;

    public GameObject(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
