package com.itcr.ce.airwar.enemies;

import com.itcr.ce.airwar.*;

/**
 * Created by Arturo on 1/4/2017.
 */
public class Boss extends Enemy{
    public Boss(String texturePath, float scale, int xPosition, int yPosition){
        super(texturePath, scale, xPosition, yPosition);
        this.life = 40; //El boss muere de 40 disparos del jugador
        this.damage = 3; //Mata al jugador de un disparo
        this.speed = 0.15f; ; //Velocidad baja
    }
}
