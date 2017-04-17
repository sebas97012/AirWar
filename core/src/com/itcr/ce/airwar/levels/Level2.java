package com.itcr.ce.airwar.levels;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level2 extends Level{
    public Level2(){
        this.nextLevel = new Level3();
        this.backgroundTexturePath = "ground/backgroundlevel2.jpg"; //Fondo del nivel
        this.numLevel = 2; //Numero de nivel
        this.bossHealt = 20;
        this.fighterBomberHealt = 2;
        this.jetHealth = 1;
        this.kamikazeHealth = 1;
        this.missileTowerHealth = 2;
        this.towerHealth = 2;
        this.numberOfEnemies = 30;
    }
}