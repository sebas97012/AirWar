package com.itcr.ce.airwar.levels;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level5 extends Level {
    public Level5(){
        this.nextLevel = new Level6();
        this.backgroundTexturePath = "ground/backgroundlevel5.jpg"; //Fondo del nivel
        this.numLevel = 5; //Numero de nivel
        this.bossHealt = 40;
        this.fighterBomberHealt = 5;
        this.jetHealth = 4;
        this.kamikazeHealth = 4;
        this.missileTowerHealth = 5;
        this.towerHealth = 5;
        this.numberOfEnemies = 50;
    }
}