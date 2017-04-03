package com.itcr.ce.airwar.enemies;

/**
 * Created by Arturo on 25/3/2017.
 */
public class Jet extends Enemy {
    public Jet(String texturePath, float scale, int xPosition, int yPosition){
        super(texturePath, scale, xPosition, yPosition);
        this.score = 5;
        this.life = 3; //Muere de tres disparos
        this.damage = 1; //Mata al jugador de tres disparos
        this.speed = 0.15f; //Alta velocidad
    }
}
