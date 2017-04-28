package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.Gdx;

/**
 * Created by Arturo on 16/4/2017.
 */
public class Level9 extends Level {
    public Level9(){
        this.nextLevel = new Level10();
        this.backgroundTexturePath = "ground/backgroundlevel9.png"; //Fondo del nivel
        this.music =  Gdx.audio.newMusic(Gdx.files.internal("music/level9music.wav"));
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