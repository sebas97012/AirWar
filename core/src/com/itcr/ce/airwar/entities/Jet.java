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
    public Jet(int life, int tipo){
        super("ships/jetship.png", 0.45f, life, tipo);
        this.score = 5;
    }
}