package com.itcr.ce.airwar.enemies;

/**
 * Created by Arturo on 25/3/2017.
 */
public class MissileTower extends Tower {
    public MissileTower(int x, int y){
        super(x, y);
        this.damage = 1; //Matan al jugador de un disparo
    }
}
