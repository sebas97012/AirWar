package com.itcr.ce.airwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.airwar.Player;
import com.itcr.ce.airwar.levels.Level;

/**
 * Created by Arturo on 5/4/2017.
 */
public class DeathScreen implements Screen {
    private MyGdxGame game;
    private Player player;
    private OrthographicCamera camera;
    private Stage stage;
    private Table table;
    private TextButton buttonPlay;
    private Label gameOver;
    private Skin skin;

    public DeathScreen(MyGdxGame game, Player player){
        this.game = game;
        this.player = player;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.appWidth, MyGdxGame.appHeight);
        camera.translate(0, 0);
    }

    @Override
    public void show() {
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage();
        this.table = new Table(this.skin);
        this.buttonPlay = new TextButton("Try Again", this.skin);
        this.stage.addActor(table);
        this.table.setBounds(0, 0, this.stage.getWidth(), this.stage.getHeight());
        Gdx.input.setInputProcessor(this.stage);

        this.gameOver = new Label("Game Over", this.skin);
        this.buttonPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.setLifes(5);
                if(player.getNumLevel() == 1){
                    player.setLevel(new Level(player, 30));}
                player.getShip().getPlaneLocation().x = 0;
                player.getShip().getPlaneLocation().y = 0;
                game.setScreen(new GameScreen(game, player));
                dispose();
            }
        });

        this.table.add(this.gameOver).padBottom(30);
        this.table.row();
        this.table.add(this.buttonPlay).padBottom(30).width(200).height(35);
        table.row();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, MyGdxGame.appWidth, MyGdxGame.appHeight);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.begin();

        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.setBounds(0, 0, stage.getWidth(), stage.getHeight());

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
