package com.itcr.ce.airwar;

import com.itcr.ce.airwar.entities.PlayerShip;
import com.itcr.ce.airwar.levels.*;
import com.itcr.ce.airwar.screens.GameScreen;

/**
 * Created by Arturo on 4/4/2017.
 */
public class Player {
    private int lifes;
    private String munitionType;
    private int munition = 0;
    private PlayerShip plane;
    private Level level;
    private MyInputProcessor inputProcessor;
    private GameScreen gameScreen;

    /**
     * Constructor
     */
    public Player(){
        this.plane = new PlayerShip();
        this.level = new Level(this);
        this.inputProcessor = new MyInputProcessor(this);
        this.lifes = 5;
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public Level getLevel(){
        return this.level;
    }

    public String getMunitionType() {
        return munitionType;
    }

    public void setMunitionType(String munitionType) {
        this.munitionType = munitionType;
    }

    public int getMunition() {
        return munition;
    }

    public void setMunition(int munition) {
        this.munition = munition;
    }

    public int getLifes(){
        return this.lifes;
    }

    public void setLifes(int lifes){
        this.lifes = lifes;
    }

    public PlayerShip getPlane(){
        return this.plane;
    }

    public MyInputProcessor getInputProcessor(){
        return this.inputProcessor;
    }
}

