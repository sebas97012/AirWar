package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.BulletEnemy;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.data.Random;


/**
 * Created by Arturo on 25/3/2017.
 */
public class Kamikaze extends Enemy {
    private float xPosPlayer;
    private float yPosPlayer;

    /**
     * Constructor
     */
    public Kamikaze(int life){
        super("ships/kamikazeship.gif", 1.3f, life);
        this.score = 20;
        this.speed = 0.3f;
        this.dataSet = new Vector2[3];
    }

    /**
     * Se establece la posicion en la que se encuentra el jugador
     * @param xPos Posicion en el eje x del jugador
     * @param yPos Posicion en el eje y del jugador
     */
    public void setPositionPlayer(float xPos, float yPos){
        this.xPosPlayer = xPos;
        this.yPosPlayer = yPos;
    }

    @Override
    public BulletEnemy shoot(){
        return null;
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

        this.path = new CatmullRomSpline<Vector2>(dataSet, true);
    }
}