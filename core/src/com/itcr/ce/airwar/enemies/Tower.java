package com.itcr.ce.airwar.enemies;

/**
 * Created by Arturo on 25/3/2017.
 */
public class Tower {
    protected int score = 15;
    protected int life = 5; //Mueren de 5 disparos
    protected int damage = 2; //Matan al jugador de dos disparos
    protected int xPosition;
    protected int yPosition;

    public Tower(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
    }
}
