package com.itcr.ce.airwar.levels;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level3 extends Level {
    public Level3(){
        this.nextLevel = new Level4();
        this.backgroundTexturePath = "ground/backgroundlevel3.png"; //Fondo del nivel
        this.numLevel = 3; //Numero de nivel
        this.bossHealt = 30;
        this.fighterBomberHealt = 3;
        this.jetHealth = 2;
        this.kamikazeHealth = 2;
        this.missileTowerHealth = 3;
        this.towerHealth = 3;
        this.numberOfEnemies = 40;
    }
}