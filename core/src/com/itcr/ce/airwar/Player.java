package com.itcr.ce.airwar;

import com.itcr.ce.airwar.entities.PlayerPlane;
import com.itcr.ce.airwar.levels.*;
import com.itcr.ce.airwar.screens.GameScreen;

/**
 * Created by Arturo on 4/4/2017.
 */
public class Player {
    private int health;
    private String munitionType;
    private int munition = 0;
    private PlayerPlane plane;
    private Level level;
    private MyInputProcessor inputProcessor;
    private GameScreen gameScreen;

    /**
     * Constructor
     */
    public Player(){
        this.plane = new PlayerPlane();
        this.level = new Level(this);
        this.inputProcessor = new MyInputProcessor(this);
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

    public int getHealth(){
        return this.health;
    }

    public PlayerPlane getPlane(){
        return this.plane;
    }

    public MyInputProcessor getInputProcessor(){
        return this.inputProcessor;
    }
}

