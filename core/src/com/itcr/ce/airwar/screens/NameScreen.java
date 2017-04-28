package com.itcr.ce.airwar.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.airwar.Player;

/**
 * Created by Adrian on 20/04/2017.
 */
public class NameScreen implements Screen {

    final MyGdxGame game;
    private OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton buttonStart;
    private Label nameLabel;
    private TextField nameText;
    private Player player;

    public NameScreen(MyGdxGame game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.player = new Player();
        camera.setToOrtho(false, MyGdxGame.appWidth, MyGdxGame.appHeight);
        camera.translate(0, 0);
    }



    @Override
    public void show() {
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage();
        this.table = new Table(this.skin);
        Drawable drawable = new SpriteDrawable(new Sprite(new Texture("ground/menuBG.jpg")));
        this.table.setBackground(drawable);
        this.buttonStart = new TextButton("Enter", this.skin);
        this.table.setFillParent(true);
        this.table.setBounds(0, 0, this.stage.getWidth(), this.stage.getHeight());
        this.stage.addActor(table);
        Gdx.input.setInputProcessor(this.stage);
        this.nameLabel = new Label("Name: ", this.skin);
        this.nameText = new TextField("",this.skin);

        this.buttonStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.setName(nameText.getText());
                game.setScreen(new MenuScreen(game, player));
                dispose();
            }
        });

        this.table.add(this.nameLabel).pad(100,100,0,0);
        this.table.add(this.nameText).pad(100,0,0,800);
        this.table.row();
        this.table.add(this.buttonStart).width(100).height(35).pad(0,100,0,0);
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
