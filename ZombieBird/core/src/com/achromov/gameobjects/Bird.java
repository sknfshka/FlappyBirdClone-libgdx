package com.achromov.gameobjects;

import com.achromov.zbhelpers.Asset;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by User on 04.05.2017.
 */
public class Bird extends GameObject{
    private Vector2 acceleration;

    private float rotation; // Для обработки поворота птицы

    private boolean isAlive;
    private Circle boundingCircle = new Circle();

    public static final int MAX_SPEED = 200;

    public Bird(float x, float y, int width, int height) {
        super(x, y, width, height);
        acceleration = new Vector2(0, 460);
        isAlive = true;
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }

    public boolean shouldntFlap() {
        return velocity.y > 70 || !isAlive;
    }

    public void update(float delta) {
        if(!isAlive)
            return;

        // множетель перемещения ( зависит от частоты )Ы
        if (velocity.y < MAX_SPEED) {
            velocity.add(acceleration.cpy().scl(delta));
        }

        if(position.y < -10) {
            position.y = -10;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));


        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

        // повернуть против часовой стрелки
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Повернуть по часовой стрелке
        if (isFalling()) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }

        }
    }

    public void onClick() {
        if (isAlive) {
            velocity.y = -140;
            Asset.flap.play();
        }
    }

    public float getRotation() {
        return rotation;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void killBird() {
        isAlive = false;
        velocity.y = 0;
    }

    public void decelerate() {
        acceleration.y = 0;
    }
}
