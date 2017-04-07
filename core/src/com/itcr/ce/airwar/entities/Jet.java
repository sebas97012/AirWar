package com.itcr.ce.airwar.entities;

/**
 * Created by Arturo on 25/3/2017.
 */
public class Jet extends Enemy {
    /**
     * Constructor
     * @param scale Escala deseada para la textura
     * @param xPosition Posicion en el eje x para spawnearlo
     * @param yPosition Posicion en el eje y para spawnearlo
     */
    public Jet(float scale, int xPosition, int yPosition){
        super("airplane/shipblue0020.png", scale, xPosition, yPosition);
        this.score = 5;
        this.life = 3; //Muere de tres disparos
        this.damage = 1; //Mata al jugador de tres disparos
        this.speed = 0.15f; //Alta velocidad
    }
}
