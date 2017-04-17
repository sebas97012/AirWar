package com.itcr.ce.airwar.levels;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level10 extends Level {
    public Level10(){
        this.nextLevel = null;
        this.backgroundTexturePath = "ground/backgroundlevel10.png"; //Fondo del nivel
        this.numLevel = 9; //Numero de nivel
        this.bossHealt = 40;
        this.fighterBomberHealt = 9;
        this.jetHealth = 8;
        this.kamikazeHealth = 8;
        this.missileTowerHealth = 9;
        this.towerHealth = 9;
        this.numberOfEnemies = 25;
    }
}
