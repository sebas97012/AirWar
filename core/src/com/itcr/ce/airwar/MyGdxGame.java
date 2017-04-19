package com.itcr.ce.airwar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.itcr.ce.airwar.screens.MenuScreen;
import com.itcr.ce.airwar.server.Bridge;


/**
 * Created by Arturo on 5/4/2017.
 */
public class MyGdxGame extends Game {
    public SpriteBatch batch; //Batch donde se renderizan los objetos
    public BitmapFont font;
    public static Player player;
    public static int appWidth;
    public static int appHeight;
    public static Bridge bridge;

    /**
     * Constructor
     * @param appWidth Anchura de la aplicacion
     * @param appHeight Altura de la aplicacion
     */
    public MyGdxGame(int appWidth, int appHeight){
        this.appWidth = appWidth;
        this.appHeight = appHeight;
    }

    /**
     * Metodo que crea el SpriteBatch y el BitmapFont
     */
    public void create() {
        bridge = new Bridge(); //se inicia servidor para la conexcion con el cliente android
        batch = new SpriteBatch(); //Se crea el batch
        font = new BitmapFont();
        this.player = new Player(); //Se crea el jugador
        this.setScreen(new MenuScreen(this)); //Se le asigna la pantalla a mostrar
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}