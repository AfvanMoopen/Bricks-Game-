package com.Hale.bricks.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.Hale.bricks.BrickBreaker;
import com.Hale.bricks.Objects.Background;
import com.Hale.bricks.Objects.MenuButton;
import com.Hale.bricks.Objects.MenuText;

import static com.Hale.bricks.Objects.Constants.BUTTON_RESET;



public class GameOverScreen extends GameClass{
    private OrthographicCamera camera;
    private Stage stage;
    private Background background;
    private MenuButton menuButton;

    private MenuText titleText;
    private MenuText scoreText;
    private float score;

    public GameOverScreen(BrickBreaker brickBreaker, float score) {
        super(brickBreaker);
        this.score = score;
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        stage.getViewport().setCamera(camera);
        camera.setToOrtho(false, 450, 800);
        background = new Background(0,0);
        menuButton = new MenuButton(BUTTON_RESET,225 - 150,250,300,90);
        titleText = new MenuText("Game Over","font.ttf",10,600);
        scoreText = new MenuText("Score: " + "0","font20.ttf",300,500);
        stage.addActor(background);
        stage.addActor(menuButton);
        stage.addActor(titleText);
        stage.addActor(scoreText);
    }

    public float scoreI = 1;
    long start = 0;
    @Override
    public void render(float delta) {
        stage.draw();
        stage.act();

        if(scoreI < score){
            long diffInMillis = TimeUtils.timeSinceMillis(start);
            if(diffInMillis > 10){
                scoreI = scoreI + 5;
                scoreText.setText(String.valueOf(scoreI));
                start = 0;
            }

        }

        if(menuButton.touch){
            menuButton.touch = false;
            game.setScreen(new GameScreen(game));
        }
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
