package com.itcr.ce.airwar.entities;

/**
 * Created by Arturo on 1/4/2017.
 */
public class Boss extends Enemy {
    /**
     * Constructor
     * @param texturePath Textura que va a tener el enemigo
     * @param scale Escala deseada para la textura
     * @param xPosition Posicion x en la que se desea spawnear
     * @param yPosition Posicion y en la que se desea spawnear
     */
    public Boss(String texturePath, float scale, int xPosition, int yPosition){
        super(texturePath, scale, xPosition, yPosition);
        this.life = 40; //El boss muere de 40 disparos del jugador
        this.damage = 3; //Mata al jugador de un disparo
        this.speed = 0.15f; ; //Velocidad baja
    }
}
