package com.itcr.ce.airwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.airwar.Player;
import com.itcr.ce.airwar.levels.Level;

/**
 * Created by Arturo on 5/4/2017.
 */
public class MenuScreen implements Screen{
    final MyGdxGame game;
    private OrthographicCamera camera;


    public MenuScreen(MyGdxGame game){
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.appWidth, MyGdxGame.appHeight);
        camera.translate(0, 0);
    }

    @Override
    public void show() {
        /*
        MyAssetManager.manager.clear();
        MyAssetManager.loadAssets();
        */
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, MyGdxGame.appWidth, MyGdxGame.appHeight);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.end();

        GameScreen gameScreen = new GameScreen(game, MyGdxGame.player);
        game.setScreen(gameScreen);
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
