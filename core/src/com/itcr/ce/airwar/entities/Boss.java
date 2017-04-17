package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Arturo on 1/4/2017.
 */
public class Boss extends Enemy {
    /**
     * Constructor
     * @param texturePath Textura que va a tener el enemigo
     * @param scale Escala deseada para la textura
     */
    public Boss(String texturePath, float scale, int life){
        super(texturePath, scale, life);
        this.speed = 0.15f; //Velocidad baja
    }
}