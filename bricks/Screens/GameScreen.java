package com.Hale.bricks.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.Hale.bricks.BrickBreaker;
import com.Hale.bricks.Objects.Background;
import com.Hale.bricks.Objects.Ball;
import com.Hale.bricks.Objects.Brick;
import com.Hale.bricks.Objects.Paddle;
import com.Hale.bricks.Objects.PowerUp;
import com.Hale.bricks.Objects.Score;

import java.util.ArrayList;

import static com.Hale.bricks.Objects.Constants.BALL_SPEED;
import static com.Hale.bricks.Objects.Constants.BALL_WIDTH;
import static com.Hale.bricks.Objects.Constants.SCREEN_WIDTH;
import static com.Hale.bricks.Objects.Constants.powerUp_AddOneBall;
import static com.Hale.bricks.Objects.Constants.powerUp_FireBall;
import static com.Hale.bricks.Objects.Constants.powerUp_SpeedUp;
import static com.Hale.bricks.Objects.Constants.powerUp_paddleSize;
import static com.Hale.bricks.Objects.Constants.powerUp_speedLow;
import static com.Hale.bricks.Objects.SoundManager.loadSounds;
import static com.Hale.bricks.Objects.SoundManager.playBrickSound;
import static com.Hale.bricks.Objects.SoundManager.playMusicBackground;
import static com.Hale.bricks.Objects.SoundManager.stopMusicBackground;
import static java.lang.Math.abs;


public class GameScreen extends GameClass {

    private OrthographicCamera camera;
    private Stage stage;
    private Background background;

    private Paddle paddle;

    private Score score;

    private ArrayList<Brick> bricks = new ArrayList<Brick>();
    private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    int[][] level;
    int[][] level1;
    int[][] level2;

    private boolean selectPosition;
    int brickN = 0;


    public GameScreen(BrickBreaker brickBreaker){
        super(brickBreaker);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        loadSounds();

        camera = new OrthographicCamera();
        stage.getViewport().setCamera(camera);
        camera.setToOrtho(false, SCREEN_WIDTH, 800);
        background = new Background(0,0);
        paddle = new Paddle(SCREEN_WIDTH/2 - 130/2 ,50);
        score = new Score(200,770);
        stage.addActor(background);
        stage.addActor(paddle);
        stage.addActor(score);
        selectPosition = true;

        playMusicBackground();
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {

                selectPosition = false;
                balls.get(0).pause = false;
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

                x1 = (xx) * (SCREEN_WIDTH / screenW);

                paddle.setPosition(x1 - 130/2, paddle.getY());

                if(selectPosition) {
                    balls.get(0).setPosition(paddle.position.x + 130/2 - 30/2, balls.get(0).position.y);
                }
            }
        });

        level1 = new int[][]{
                {0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0,},
                {1, 1, 1, 1, 11, 1,},
                {2, 2, 12, 2, 2, 2,},
                {3, 3, 3, 13, 3, 3,},
                {4, 4, 4, 4, 14, 4,},
                {5, 5, 15, 5, 5, 5,},
                {6, 6, 6, 6, 6, 6,},
                {0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0,},

        };
        level2 = new int[][]{
                {0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0,},
                {2, 2, 2, 2, 2, 2,},
                {2, 5, 12, 2, 5, 2,},
                {2, 2, 2, 2, 2, 2,},
                {2, 2, 2, 2, 12, 2,},
                {2, 5, 2, 12, 5, 2,},
                {2, 5, 15, 5, 5, 2,},
                {12, 2, 2, 2, 2, 2,},
        };

        level = level1;
        loadLevel();

    }

    public void clearLevel(){
        clearBricks();
        clearBalls();
        clearPowerUps();
    }

    public void resetPosition(){
        balls.add(new Ball(SCREEN_WIDTH / 2 - 30 / 2, 100));
        Ball ballx = balls.get(balls.size() - 1);
        ballx.pause = true;
        stage.addActor(ballx);
        selectPosition = true;
        paddle.setPosition(SCREEN_WIDTH / 2 - 130 / 2, paddle.getY());
    }

    public void loadLevel(){
        addNewBall(true);
        selectPosition = true;
        paddle.setPosition(SCREEN_WIDTH / 2 - 130 / 2, paddle.getY());

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 6; x++) {
                if (level[y][x] != 0) {
                    bricks.add(new Brick(level[y][x],x * 75,800 - (y * 30)-30));
                    level[y][x] = 0;
                }
            }
        }

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                if(brickN < bricks.size()) {
                    stage.addActor(bricks.get(brickN));
                    brickN++;
                }
            }
        }, 0,0.03f);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            stopGame();
            game.setScreen(new MenuScreen(game));
        }

        for(int i = 0; i < balls.size(); i++){
            Ball ball = balls.get(i);

        //CHECK IF LEVEL IS COMPLETE
        if(bricks.size() <= 0){
            clearLevel();
            selectPosition = true;
            paddle.setPosition(



                    SCREEN_WIDTH/2 - 130/2,paddle.getY());
            level = level2;
            score.level = score.level + 1;
            brickN = 0;
            loadLevel();
        }
        //LEFT
        if (ball.position.x <= 0) {
            ball.position.x = 0;
            ball.speedX = -ball.speedX;
        }

        // RIGHT
        if ((ball.position.x + BALL_WIDTH) >= SCREEN_WIDTH) {
            ball.position.x = SCREEN_WIDTH - BALL_WIDTH;
            ball.speedX = -ball.speedX;
        }

        // UP
        if ((ball.position.y + BALL_WIDTH) >= 800) {
            ball.position.y = 800 - BALL_WIDTH;
            ball.speedY = -ball.speedY;
        }

        //Down
        if (ball.position.y < 0) {
            ball.remove();
            balls.remove(i);
            if (score.lives > 0) {
                if(balls.size() < 1) {
                    clearBalls();
                    clearPowerUps();
                    resetPosition();
                    score.lives = score.lives - 1;
                }
            }
            else{
                stopGame();
                game.setScreen(new GameOverScreen(game,score.score));
            }
        }

        if (paddle.getBounds().overlaps(ball.getBounds())) {

            if (ball.speedX > 0)
                ball.speedX = BALL_SPEED;
            else
                ball.speedX = -BALL_SPEED;

            ball.position.y = paddle.position.y + 40;
            ball.speedY = -ball.speedY;

        }


        for (int ix = 0; ix < bricks.size(); ix++) {
            Brick brick = bricks.get(ix);
            if (brick.getBounds().overlaps(ball.getBounds()) && brick.live) {
                Rectangle insect = intersect(brick.getBounds(), ball.getBounds());

                boolean vertical = false;
                boolean horizontal = false;
                boolean isLeft = false;
                boolean isTop = false;

                if (insect.x == brick.x) {
                    horizontal = true;
                    isLeft = true;
                } else if (insect.x + insect.width == brick.x + brick.width) {
                    horizontal = true;
                }
                if (insect.y == brick.y) {
                    vertical = true;
                    isTop = true;
                } else if (insect.y + insect.height == brick.y + brick.height) {
                    vertical = true;
                }

                if (horizontal && vertical) {
                    if (insect.width > insect.height) {
                        horizontal = false;
                    } else {
                        vertical = false;
                    }
                }

                if(!ball.state_fire) {
                    if (horizontal) {
                        if (isLeft) {
                            ball.speedX = -ball.speedX;
                        } else {
                            ball.speedX = -ball.speedX;
                        }
                    } else if (vertical) {
                        if (isTop) {
                            ball.speedY = -ball.speedY;
                        } else {
                            ball.speedY = -ball.speedY;
                        }
                    }
                }
                playBrickSound();
                brick.remove();
                bricks.remove(ix);
                brick.live = false;
                score.score = score.score + 100;
                if(brick.type >= 11){
                    powerUps.add(new PowerUp(brick.type, brick.x + brick.width/2 - 15, brick.y));
                    stage.addActor(powerUps.get(powerUps.size() - 1));
                }


                break;
            }
            }
        }



        for(PowerUp powerUp : powerUps){
            if(paddle.getBounds().overlaps(powerUp.getBounds())){
                if(powerUp.live) {
                    switch (powerUp.type) {
                        case powerUp_AddOneBall:
                            addNewBall(false);
                            powerUp.remove();
                            powerUp.live = false;
                            break;
                        case powerUp_paddleSize:
                            setPaddlesize(80,30);
                            powerUp.remove();
                            powerUp.live = false;
                            break;
                        case powerUp_FireBall:
                            setFireBall();
                            powerUp.remove();
                            powerUp.live = false;
                            break;
                        case powerUp_speedLow:
                            setVelocityBall(500f);
                            powerUp.remove();
                            powerUp.live = false;
                            break;
                        case powerUp_SpeedUp:
                            setVelocityBall(100f);
                            powerUp.remove();
                            powerUp.live = false;
                            break;
                    }
                }
            }
        }
    }

    public void stopGame(){
        stopMusicBackground();

    }

    public void clearBalls(){
        for(Ball ball : balls){
            ball.remove();
        }
        balls.clear();
    }

    public void clearBricks(){
        for(Brick brick : bricks){
            brick.remove();
        }
        bricks.clear();
    }

    public void clearPowerUps(){
        for(PowerUp powerUp: powerUps){
            powerUp.remove();
        }
        powerUps.clear();
    }

    public void setFireBall(){
        for(Ball ball : balls){
            ball.setFireBall();
        }
    }

    public void setPaddlesize(float w, float h){
       paddle.setSize(w,h);
    }

    public void setVelocityBall(float speed){
        for(Ball ball : balls){
            ball.setVelocity(speed);
        }
    }

    public void addNewBall(boolean paused){
        balls.add(new Ball(SCREEN_WIDTH / 2 - BALL_WIDTH / 2, 100));
        Ball ball = balls.get(balls.size() - 1);
        if(paused) {
            ball.pause = true;
        }
        stage.addActor(ball);
    }

    public Rectangle intersect(Rectangle rectangle1, Rectangle rectangle2) {
        Rectangle intersection = new Rectangle();
        intersection.x = Math.max(rectangle1.x, rectangle2.x);
        intersection.width = Math.min(rectangle1.x + rectangle1.width, rectangle2.x + rectangle2.width) - intersection.x;
        intersection.y = Math.max(rectangle1.y, rectangle2.y);
        intersection.height = Math.min(rectangle1.y + rectangle1.height, rectangle2.y + rectangle2.height) - intersection.y;

        return intersection;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
