package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Arturo on 25/3/2017.
 */
public class FighterBomber extends Enemy {
    /**
     * Constructor
     */
    public FighterBomber(){
        super("airplane/shipred0000.png", 0.45f);
        this.score = 10;
        this.life = 2; //Muere de dos disparos del jugador
        this.damage = 3; //Mata al jugador de un disparo;
        this.speed = 0.15f; //Velocidad media
    }
}
