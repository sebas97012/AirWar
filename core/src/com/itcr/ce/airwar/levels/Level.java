package com.itcr.ce.airwar.levels;

/**
 * Created by Arturo on 16/4/2017.
 */
public abstract class Level {
    protected Level nextLevel; //Nivel siguiente
    protected String backgroundTexturePath; //Fondo del nivel
    protected int numLevel; //Numero de nivel

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
}