package com.Hale.bricks.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.TimeUtils;

import static com.Hale.bricks.Objects.Constants.PADDLE_HEIGHT;
import static com.Hale.bricks.Objects.Constants.PADDLE_WIDITH;
import static com.Hale.bricks.Objects.Constants.SCREEN_WIDTH;
import static com.Hale.bricks.Objects.ResourceManager.getTexture;



public class Paddle extends Actor {

    Texture tPaddle;
    public Vector2 position;
    public float width = 130;
    public float height = 40;

    public boolean size_changed;


    public Paddle(float x,float y){
        tPaddle = getTexture("paddle.png");

        width = PADDLE_WIDITH;
        height = PADDLE_HEIGHT;

        position = new Vector2(x,y);

        setBounds(x,y,PADDLE_WIDITH,PADDLE_HEIGHT);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {

            }
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);

                float screenW = Gdx.graphics.getWidth();
                float screenH = Gdx.graphics.getHeight();


                float xx = Gdx.input.getX();
                float yy = Gdx.input.getY();

                float yR = screenH / (yy); // the y ratio
                float y1 = screenH / yR;

                y1 = (screenH - yy) * (800 / screenH);


                float xR = screenW / (xx); // the x ratio
                float x1 = screenW / xR;

                x1 = (xx) * (450/ screenW);

                setPosition(x1,getY());
                position.x = x1;
            }

        });

    }
    public Rectangle getBounds(){
        return new Rectangle(position.x,position.y,width,height);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        position.x = x;
        position.y = y;
        if(position.x < 0){
            position.x = 0;
        }
        if(position.x + 130 > 450){
            position.x = SCREEN_WIDTH - 130;
        }
        if(size_changed){
            long diffInMillis = TimeUtils.timeSinceMillis(start);
            if(diffInMillis > 5000){
                size_changed =  false;
                width = PADDLE_WIDITH;
                height = PADDLE_HEIGHT;
            }
        }
    }


        long start;


    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(tPaddle,position.x,position.y,width,height);
    }

    public void setSize(float widith, float height){
        this.width = widith;
        this.height = height;
        size_changed = true;
        start = TimeUtils.millis();
    }
}
