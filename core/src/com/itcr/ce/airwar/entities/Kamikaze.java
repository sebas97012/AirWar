package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.data.LinkedList;
import com.itcr.ce.data.Random;


/**
 * Created by Arturo on 25/3/2017.
 */
public class Kamikaze extends Enemy {
    private float xPosPlayer;
    private float yPosPlayer;

    /**
     * Constructor
     * @param scale Escala deseada para la textura del kamikaze
     * @param xPosStart Posicion inicial en x
     * @param yPosStart Posicion inicial en y
     * @param xPosPlayer Posicion en x en la que se encuentra el jugador
     * @param yPosPlayer Posicion en y en la que se encuentra el jugador
     */
    public Kamikaze(float scale, int xPosStart, int yPosStart, float xPosPlayer, float yPosPlayer){
        super("airplane/shipgreen0001.gif", scale, xPosStart, yPosStart);
        this.score = 20;
        this.speed = 0.3f;
        this.xPosPlayer = xPosPlayer;
        this.yPosPlayer = yPosPlayer;
        this.dataSet = new Vector2[3];
    }

    /**
     * Creacion de la ruta que va a tomar el kamikaze
     */
    @Override
    public void initialPath(){
        Vector2 start = new Vector2(Random.getRamdomNumber(0, MyGdxGame.appWidth), MyGdxGame.appHeight); //Posicion iniclal
        Vector2 playerPosition = new Vector2(this.xPosPlayer, this.yPosPlayer); //Posicion en la que esta el jugador
        Vector2 end = new Vector2(this.xPosPlayer, 0 + -(2 * this.sprite.getHeight())); //Posicion final

        dataSet[0] = start;
        dataSet[1] = playerPosition;
        dataSet[2] = end;

        this.catmullRomSpline = new CatmullRomSpline<Vector2>(dataSet, true);
    }
}