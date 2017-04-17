package com.itcr.ce.airwar.levels;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level8 extends Level {
    public Level8(){
        this.nextLevel = new Level9();
        this.backgroundTexturePath = "ground/backgroundlevel8.png"; //Fondo del nivel
        this.numLevel = 8; //Numero de nivel
        this.bossHealt = 40;
        this.fighterBomberHealt = 8;
        this.jetHealth = 7;
        this.kamikazeHealth = 7;
        this.missileTowerHealth = 8;
        this.towerHealth = 8;
        this.numberOfEnemies = 30;
    }

}
