package com.achromov.zbhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

/**
 * Created by User on 07.05.2017.
 */
public class Asset {
    private static final AssetManager manager = new AssetManager();

    public static Sprite bg, grass;
    public static Sprite bird;
    public static Sprite skullUp, skullDown;
    public static TiledDrawable tile;
    public static Animation birdAnimation;

    public static Sound dead;
    public static Sound flap;
    public static Sound coin;

    public static BitmapFont font, shadow;

    public static Preferences prefs;

    public static void load() {
        manager.load("data/bird.pack", TextureAtlas.class);
        manager.load("data/dead.wav", Sound.class);
        manager.load("data/flap.wav", Sound.class);
        manager.load("data/coin.wav", Sound.class);
        manager.load("data/text.fnt", BitmapFont.class);
        manager.load("data/shadow.fnt", BitmapFont.class);

        prefs = Gdx.app.getPreferences("FlappyBird");
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    public static void finishLoading(){
        manager.finishLoading();
    }

    public static void init(){
        // шрифты
        font = manager.get("data/text.fnt", BitmapFont.class);
        font.getData().setScale(.25f, -.25f);
        shadow = manager.get("data/shadow.fnt", BitmapFont.class);
        shadow.getData().setScale(.25f, -.25f);

        //  звук
        dead = manager.get("data/dead.wav", Sound.class);
        flap = manager.get("data/flap.wav", Sound.class);
        coin = manager.get("data/coin.wav", Sound.class);

        //текстуты
        TextureAtlas atlas = manager.get("data/bird.pack", TextureAtlas.class);
        bg = atlas.createSprite("bg");
        bg.flip(false, true);

        grass = atlas.createSprite("grass");
        grass.flip(false, true);

        Sprite birdDown = atlas.createSprite("bird-1");
        birdDown.flip(false, true);

        bird = atlas.createSprite("bird-2");
        bird.flip(false, true);

        Sprite birdUp = atlas.createSprite("bird-3");
        birdUp.flip(false, true);

        Sprite[] birds = { birdDown, bird, birdUp };
        birdAnimation = new Animation(0.1f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skullUp = atlas.createSprite("pipeTop");
        skullDown = new Sprite(skullUp);
        skullDown.flip(false, true);

        Sprite bar = atlas.createSprite("pipe");
        tile = new TiledDrawable(bar);
    }

    public static void dispose(){
        manager.dispose();
        dead.dispose();
        flap.dispose();
        coin.dispose();
        font.dispose();
        shadow.dispose();
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }
}
