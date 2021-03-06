package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.Gdx;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level4 extends Level {
    public Level4(){
        this.nextLevel = new Level5();
        this.backgroundTexturePath = "ground/backgroundlevel4.png"; //Fondo del nivel
        this.music =  Gdx.audio.newMusic(Gdx.files.internal("music/level4music.mp3"));
        this.numLevel = 4; //Numero de nivel
        this.bossHealt = 40;
        this.fighterBomberHealt = 4;
        this.jetHealth = 3;
        this.kamikazeHealth = 3;
        this.missileTowerHealth = 4;
        this.towerHealth = 4;
        this.numberOfEnemies = 45;
    }
}