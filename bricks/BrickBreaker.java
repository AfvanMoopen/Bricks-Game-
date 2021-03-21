package com.Hale.bricks;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

import com.Hale.bricks.Screens.GameScreen;
import com.Hale.bricks.Screens.MenuScreen;

public class BrickBreaker extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
