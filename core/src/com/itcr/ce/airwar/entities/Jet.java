package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Arturo on 25/3/2017.
 */
public class Jet extends Enemy {
    /**
     * Constructor
     */
    public Jet(){
        super("airplane/shipblue0020.png", 0.45f);
        this.score = 5;
        this.life = 3; //Muere de tres disparos
        this.damage = 1; //Mata al jugador de tres disparos
        this.speed = 0.15f; //Alta velocidad
    }
}
