package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.audio.Music;

/**
 * Created by Arturo on 16/4/2017.
 */
public abstract class Level {
    protected Level nextLevel; //Nivel siguiente
    protected String backgroundTexturePath; //Fondo del nivel
    protected int numLevel; //Numero de nivel
    protected Music music;

    //Atributos que almacenan la vida que va a tener cada enemigo según el nivel correspondiente
    protected int bossHealt;
    protected int fighterBomberHealt;
    protected int jetHealth;
    protected int kamikazeHealth;
    protected int missileTowerHealth;
    protected int towerHealth;
    protected int numberOfEnemies;

    public Level getNextLevel() {
        return nextLevel;
    }

    public String getBackgroundTexturePath() {
        return backgroundTexturePath;
    }

    public int getNumLevel() {
        return numLevel;
    }

    public int getBossHealt() {
        return bossHealt;
    }

    public int getFighterBomberHealt() {
        return fighterBomberHealt;
    }

    public int getJetHealth() {
        return jetHealth;
    }

    public int getKamikazeHealth() {
        return kamikazeHealth;
    }

    public int getMissileTowerHealth() {
        return missileTowerHealth;
    }

    public int getTowerHealth() {
        return towerHealth;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public Music getMusic() {
        return music;
    }

    public static Level createLevel(int numLevel){
        Level level = null;

        if(numLevel == 1) level = new Level1();
        else if(numLevel == 2) level = new Level2();
        else if(numLevel == 3) level = new Level3();
        else if(numLevel == 4) level = new Level4();
        else if(numLevel == 5) level = new Level5();
        else if(numLevel == 6) level = new Level6();
        else if(numLevel == 7) level = new Level7();
        else if(numLevel == 8) level = new Level8();
        else if(numLevel == 9) level = new Level9();
        else if(numLevel == 10) level = new Level10();

        return level;
    }
}