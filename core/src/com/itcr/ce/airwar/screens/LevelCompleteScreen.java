package com.itcr.ce.airwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.airwar.Player;

/**
 * Created by Adrian on 15/04/2017.
 */
public class LevelCompleteScreen implements Screen {

    private MyGdxGame game;
    private Player player;
    private OrthographicCamera camera;
    private Stage stage;
    private Table table;
    private TextButton buttonNext;
    private TextButton buttonAgain;
    private TextButton buttonBack;
    private Label score;
    private Skin skin;



    public LevelCompleteScreen(MyGdxGame game, Player player){
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
        Drawable drawable = new SpriteDrawable(new Sprite(new Texture(this.player.getLevel().getBackgroundTexturePath())));
        this.table.setBackground(drawable);
        this.table.setFillParent(true);
        this.table.setBounds(0, 0, this.stage.getWidth(), this.stage.getHeight());
        this.stage.addActor(table);
        Gdx.input.setInputProcessor(this.stage);

        this.buttonAgain = new TextButton("Try Again", this.skin);
        this.buttonNext = new TextButton("Next Level", this.skin);
        this.buttonBack = new TextButton("Back Menu", this.skin);

        this.score = new Label ("Your score: " + this.player.getScore(), this.skin);
        this.score.setFontScale(2,1);
        this.score.setColor(0,0,128,1);

        this.buttonAgain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    player.getShip().getPlaneLocation().x = 0;
                    player.getShip().getPlaneLocation().y = 0;
                    game.setScreen(new GameScreen(game, player));
                    dispose();
            }
        });

        this.buttonNext.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(player.getLevel().getNumLevel() != 10) {
                    player.setLevel(player.getLevel().getNextLevel());
                    player.getShip().getPlaneLocation().x = 0;
                    player.getShip().getPlaneLocation().y = 0;
                    game.setScreen(new GameScreen(game, player));
                }else{
                    game.setScreen(new MenuScreen(game));
                }
                dispose();
            }
        });

        this.buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });

        table.row();
        this.table.add(this.score).colspan(5);
        this.table.row();
        this.table.add(" ");
        this.table.row();
        this.table.add(this.buttonBack).width(200).height(50).uniform();
        this.table.add("   ");
        this.table.add(this.buttonAgain).width(200).height(50).uniform();
        this.table.add("   ");
        this.table.add(this.buttonNext).width(200).height(50).uniform();
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
