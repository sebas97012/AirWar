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

import java.net.InetAddress;
import static com.badlogic.gdx.Gdx.app;


/**
 * Created by Adrian on 10/4/2017.
 */

public class MenuScreen implements Screen{
    final MyGdxGame game;
    private Player player;
    private OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton buttonStart;
    private TextButton buttonCredits;
    private TextButton buttonStatistics;
    private TextButton buttonExit;
    private Label ip;
    private String ipText;
    private Music backGroundMusic;

    /**
     * Constructor
     * @param game Es el objeto al que se le asignan las pantallas
     * @param player Jugador
     */

    public MenuScreen(MyGdxGame game, Player player){
        this.game = game;
        this.player = player;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.appWidth, MyGdxGame.appHeight);
        camera.translate(0, 0);
        ipConfig();
        this.backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/background.wav"));
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
        Drawable drawable = new SpriteDrawable(new Sprite(new Texture("ground/menuBG.jpg")));
        this.table.setBackground(drawable);
        this.buttonStart = new TextButton("Start Game", this.skin);
        this.buttonExit = new TextButton("Exit", this.skin);
        this.buttonCredits = new TextButton("Credits", this.skin);
        this.buttonStatistics = new TextButton("Statistics",this.skin);
        this.table.setFillParent(true);
        this.table.setBounds(0, 0, this.stage.getWidth(), this.stage.getHeight());
        this.stage.addActor(table);
        Gdx.input.setInputProcessor(this.stage);
        this.ip = new Label("IP: " + ipText, this.skin);
        this.buttonStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new IntroScreen(game, player));
                dispose();
            }
        });

        this.buttonCredits.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new CreditsScreen(game, player));
                dispose();
            }
        });


        this.buttonExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                app.exit();
                dispose();
            }
        });

        this.buttonStatistics.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StadisticsScreen(game, player));
                dispose();
            }
        });

        this.table.add(this.ip).pad(0,-70,0,300);
        this.table.row();
        this.table.add(this.buttonExit).width(200).height(50).pad(450,250,200,10);
        this.table.add(this.buttonCredits).width(200).height(50).pad(450,0,200,10);
        this.table.add(this.buttonStatistics).width(200).height(50).pad(450,0,200,10);
        this.table.add(this.buttonStart).width(200).height(50).pad(450,0,200,150);


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

    private void ipConfig (){
        try
        {
            String thisIp = InetAddress.getLocalHost().getHostAddress();
            this.ipText = thisIp;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            this.ipText = "---";
        }
    }
}