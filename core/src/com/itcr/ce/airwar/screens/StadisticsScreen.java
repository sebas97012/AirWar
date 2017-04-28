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
public class StadisticsScreen implements Screen {


    final MyGdxGame game;
    private OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton buttonBack;
    private Label stadisticsText;
    private Label titleText;
    private Label nameText;
    private Label maxScoreText;
    private Label timeText;
    private Music backGroundMusic;
    private Player player;

    /**
     * Constructor
     * @param game Es el objeto al que se le asignan las pantallas
     * @param player Jugador
     */

    public StadisticsScreen(MyGdxGame game, Player player) {
        this.game = game;
        this.player = player;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.appWidth, MyGdxGame.appHeight);
        camera.translate(0, 0);
        this.backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/creditosmusic.mp3"));
        this.backGroundMusic.setLooping(true);
        this.backGroundMusic.play();
    }

    @Override
    public void show() {
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage();
        this.table = new Table(this.skin);
        Drawable drawable = new SpriteDrawable(new Sprite(new Texture("ground/backgroundlevel8.png")));
        this.table.setBackground(drawable);
        this.buttonBack = new TextButton("Back Menu", this.skin);
        this.stadisticsText = new Label ("Stadistics", this.skin);
        this.titleText = new Label("Name                Max score               Time", this.skin);
        this.nameText = new Label(this.player.getName(),this.skin );
        this.maxScoreText = new Label("" + this.player.getMaxScore(),this.skin );
        this.timeText = new Label("" + this.player.getTime(),this.skin );

        this.stadisticsText.setFontScale(3,2);
        this.stadisticsText.setColor(255,255,0,1);

        this.table.setFillParent(true);
        this.table.setBounds(0, 0, this.stage.getWidth(), this.stage.getHeight());
        this.stage.addActor(table);
        Gdx.input.setInputProcessor(this.stage);

        this.buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game,player));
                dispose();
            }
        });

        this.table.add(stadisticsText).pad(-400,0,0,0);
        this.table.row();
        this.table.add(titleText).pad(-300,0,0,0);
        this.table.row();
        this.table.add(nameText).pad(-200,-200,0,10);
        this.table.add(maxScoreText).pad(-200,-250,0,10);
        this.table.add(timeText).pad(-200,-100,0,0);
        this.table.row();
        this.table.add(buttonBack).width(100).height(35).pad(0,0,0,0);
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
