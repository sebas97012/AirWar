package com.itcr.ce.airwar.enemies;

/**
 * Created by Arturo on 25/3/2017.
 */
public class Kamikaze extends Enemy {
    public Kamikaze(String texturePath, float scale, int xPosition, int yPosition){
        super(texturePath, scale, xPosition, yPosition);
        this.score = 20;
        this.speed = 0.15f; //Velocidad alta
    }
}
