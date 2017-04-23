package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.Gdx;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level7 extends Level {
    public Level7(){
        this.nextLevel = new Level8();
        this.backgroundTexturePath = "ground/backgroundlevel7.png"; //Fondo del nivel
        this.music =  Gdx.audio.newMusic(Gdx.files.internal("music/level7music.wav"));
        this.numLevel = 7; //Numero de nivel
        this.bossHealt = 40;
        this.fighterBomberHealt = 7;
        this.jetHealth = 6;
        this.kamikazeHealth = 6;
        this.missileTowerHealth = 7;
        this.towerHealth = 7;
        this.numberOfEnemies = 35;
    }
}