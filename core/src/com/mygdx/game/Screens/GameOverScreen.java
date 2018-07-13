package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.awt.Color;

import jdk.nashorn.internal.ir.Labels;

public class GameOverScreen implements Screen {
private Viewport viewport;
private Stage stage;

private Game game;

public GameOverScreen(Game game){
    this.game = game;
    viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport, ((MyGdxGame)game).batch);

    Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE);

    Table table = new Table();
    table.center();
    table.setFillParent(true);

    Label gameOverLabel = new Label("Game Over", font);
    Label playAgainLabel = new Label("Click To Play Again", font);

    table.add(gameOverLabel).expand();
    table.row();
    table.add(playAgainLabel).expand().padTop(10f);

    stage.addActor(table);
}
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    if(Gdx.input.justTouched()){
        game.setScreen(new PlayScreen((MyGdxGame)game));
        dispose();
    }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.draw();
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
    stage.dispose();
    }
}
