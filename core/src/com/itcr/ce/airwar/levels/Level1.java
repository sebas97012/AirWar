package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.Gdx;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level1 extends Level{
    public Level1(){
        this.nextLevel = new Level2();
        this.backgroundTexturePath = "ground/backgroundlevel1.jpg"; //Fondo del nivel
        this.music =  Gdx.audio.newMusic(Gdx.files.internal("music/level1music.mp3"));
        this.numLevel = 1; //Numero de nivel
        this.bossHealt = 10;
        this.fighterBomberHealt = 1;
        this.jetHealth = 1;
        this.kamikazeHealth = 1;
        this.missileTowerHealth = 1;
        this.towerHealth = 1;
        this.numberOfEnemies = 20;
    }
}