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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.airwar.Player;

/**
 * Created by Adrian on 20/04/2017.
 */

public class IntroScreen implements Screen {

    final MyGdxGame game;
    private Player player;
    private OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton buttonSkip;
    private Label introText;
    private Music backGroundMusic;

    /**
     * Constructor
     * @param game Es el objeto al que se le asignan las pantallas
     * @param player Jugador
     */

    public IntroScreen(MyGdxGame game, Player player){
        this.game = game;
        this.player = player;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.appWidth, MyGdxGame.appHeight);
        camera.translate(0, 0);
        this.backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menuprincipalmusic.mp3"));
        this.backGroundMusic.setLooping(true);
        this.backGroundMusic.play();
    }

    /**
     * Metodo encargado de crear todos los objetos que se van a renderizar
     */

    @Override
    public void show() {
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage();
        this.table = new Table(this.skin);
        Drawable drawable = new SpriteDrawable(new Sprite(new Texture("ground/backgroundlevel1.jpg")));
        this.table.setBackground(drawable);
        this.buttonSkip = new TextButton("Skip", this.skin);
        this.introText = new Label ("En el 2045 nos atacaron los aliens, \n" +
                "una raza superior con tecnologia avanzada \n" +
                "que amenazaba con destruir nuestro planeta, \n" +
                "solo estaba en las manos de un heroe \n" +
                "con la capacidad de derrotar a los jefes \n" +
                "que comandan las fuerzas enemigas.", this.skin);

        this.introText.setFontScale(2,2);
        this.table.setFillParent(true);
        this.table.setBounds(0, 0, this.stage.getWidth(), this.stage.getHeight());
        this.stage.addActor(table);
        Gdx.input.setInputProcessor(this.stage);


        this.buttonSkip.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, player));
                dispose();
            }
        });

        this.table.add(introText).pad(200,100,0,0);
        this.table.row();
        this.table.add(buttonSkip).width(100).height(35).pad(100,650,200,200);
    }

    /**
     * Metodo encargado de renderizar todos los objetos
     * @param delta El tiempo entre el fotograma anterior y el actual
     */
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
        this.backGroundMusic.dispose();
    }
}
