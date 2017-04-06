package com.itcr.ce.airwar.entities;

/**
 * Created by Arturo on 25/3/2017.
 */
public class FighterBomber extends Enemy {
    /**
     * Constructor
     * @param texturePath Textura deseada para el enemigo
     * @param scale Escala deseada para la textura
     * @param xPosition Posicion en el eje x para spawnearlo
     * @param yPosition Posicion en el eje y para spawnearlo
     */
    public FighterBomber(String texturePath, float scale, int xPosition, int yPosition){
        super(texturePath, scale, xPosition, yPosition);
        this.score = 10;
        this.life = 2; //Muere de dos disparos del jugador
        this.damage = 3; //Mata al jugador de un disparo;
        this.speed = 0.15f; //Velocidad media
    }
}
