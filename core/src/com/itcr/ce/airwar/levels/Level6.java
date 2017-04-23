package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.Gdx;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level6 extends Level {
    public Level6(){
        this.nextLevel = new Level7();
        this.backgroundTexturePath = "ground/backgroundlevel6.png"; //Fondo del nivel
        this.music =  Gdx.audio.newMusic(Gdx.files.internal("music/level6music.mp3"));
        this.numLevel = 6; //Numero de nivel
        this.bossHealt = 40;
        this.fighterBomberHealt = 6;
        this.jetHealth = 5;
        this.kamikazeHealth = 5;
        this.missileTowerHealth = 6;
        this.towerHealth = 6;
        this.numberOfEnemies = 40;
    }
}