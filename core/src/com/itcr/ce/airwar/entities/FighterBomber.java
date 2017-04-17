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
    public FighterBomber(int life){
        super("ships/fighterbombership.png", 0.45f, life);
        this.score = 10;
        this.speed = 0.09f; //Velocidad media
    }
}