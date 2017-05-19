package com.achromov.zombiebird;
import com.achromov.zbhelpers.Asset;
import com.achromov.zbhelpers.AssetLoader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class ZBGame extends Game {

	@Override
	public void create() {
		Gdx.app.log("ZBGame", "created");
		Asset.load();
		Asset.finishLoading();
		Asset.init();
        setScreen(new GameScreen());
	}


	@Override
	public void dispose() {
		super.dispose();
		Asset.dispose();
	}
}
