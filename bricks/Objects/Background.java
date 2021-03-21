package com.Hale.bricks.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.Hale.bricks.Objects.Constants.SCREEN_HEIGHT;
import static com.Hale.bricks.Objects.Constants.SCREEN_WIDTH;
import static com.Hale.bricks.Objects.ResourceManager.getTexture;



public class Background extends Actor {

    Texture background;
    float x;
    float y;

    public Background(float x, float y){
        this.x = x;
        this.y = y;
        background = getTexture("background.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(background,x,y,SCREEN_WIDTH,SCREEN_HEIGHT);
    }
}
