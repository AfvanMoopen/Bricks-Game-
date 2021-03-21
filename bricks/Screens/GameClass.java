package com.Hale.bricks.Screens;

import com.badlogic.gdx.Screen;
import com.Hale.bricks.BrickBreaker;

public abstract class GameClass implements Screen {

	public BrickBreaker game;

	public GameClass(BrickBreaker game){
		this.game = game;
	}


}
