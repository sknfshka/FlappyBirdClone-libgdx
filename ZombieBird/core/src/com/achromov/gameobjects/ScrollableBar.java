package com.achromov.gameobjects;

import java.util.List;

/**
 * Created by User on 05.05.2017.
 */
public class ScrollableBar {
    List<Scrollable> scrollableObjects;
    int gap;
    int count;

    ScrollableBar(int gap, List<Scrollable> objects) {
        this.gap = gap;
        scrollableObjects = objects;
        count = objects.size();
    }

    public void update(float delta) {

        for(Scrollable object : scrollableObjects) {
            object.update(delta);
        }

        for(Scrollable object : scrollableObjects) {
            if (object.isScrolledLeft())
                object.reset(object.getTailX() + count * gap + (count - 1) * object.getWidth());
        }
    }

    public List<Scrollable> getScrollableObjectsObjects() {
        return scrollableObjects;
    }

    public int getGap() {
        return gap;
    }

    public void stop() {
        for(Scrollable object : scrollableObjects) {
            object.stop();
        }
    }

    public boolean collides(Bird bird) {
        for(Scrollable object : scrollableObjects) {
            if (object.collides(bird))
                return true;
        }

        return false;
    }

    public boolean isSomeObjectPassed(Bird bird) {
        for(Scrollable object : scrollableObjects) {
            if (object.isPassed(bird))
                return true;
        }

        return false;
    }

    public void onRestart(int[] xCoord) {
        int i = 0;
        for(Scrollable object : scrollableObjects) {
            object.onRestart(xCoord[i]);
            i++;
        }
    }
}

