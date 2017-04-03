package com.itcr.ce.airwar.enemies;

/**
 * Created by Arturo on 25/3/2017.
 */
public class FighterBomber extends Enemy {
    public FighterBomber(String texturePath, float scale, int xPosition, int yPosition){
        super(texturePath, scale, xPosition, yPosition);
        this.score = 10;
        this.life = 2; //Muere de dos disparos del jugador
        this.damage = 3; //Mata al jugador de un disparo;
        this.speed = 0.15f; //Velocidad media
    }
}
