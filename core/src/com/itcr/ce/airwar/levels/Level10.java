package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.Gdx;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level10 extends Level {
    public Level10(){
        this.nextLevel = null;
        this.backgroundTexturePath = "ground/backgroundlevel10.png"; //Fondo del nivel
        this.music =  Gdx.audio.newMusic(Gdx.files.internal("music/level10music.mp3"));
        this.numLevel = 10; //Numero de nivel
        this.bossHealt = 40;
        this.fighterBomberHealt = 9;
        this.jetHealth = 8;
        this.kamikazeHealth = 8;
        this.missileTowerHealth = 9;
        this.towerHealth = 9;
        this.numberOfEnemies = 25;
    }
}