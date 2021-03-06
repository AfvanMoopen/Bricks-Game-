package com.Hale.bricks.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.Hale.bricks.Objects.Constants.SCREEN_WIDTH;
import static com.Hale.bricks.Objects.ResourceManager.getTexture;



public class Score extends Actor {

    BitmapFont font;
    Texture ball;

    public int score = 0;
    public int lives = 3;
    public int level = 1;
    float textWidth;
    float x,y;
    GlyphLayout layout;
    public Score(float x, float y){
        this.x = x;
        this.y = y;

        ball = getTexture("ball.png");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15; // font size
        font = generator.generateFont(parameter);
        generator.dispose(); // avoid memory leaks, important*/
        layout = new GlyphLayout();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        layout.setText(font,String.valueOf(score));
        textWidth = layout.width;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch,String.valueOf(score),SCREEN_WIDTH/2 - (textWidth/2),y);
        batch.draw(ball,360,755,25,25);
        font.draw(batch,"x" + String.valueOf(lives), 390,770);
        font.draw(batch,"Level " + String.valueOf(level), 20,770);



    }
}
