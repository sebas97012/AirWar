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
    private PlayerShip ship;
    private Level level;
    private int numLevel;
    private MyInputProcessor inputProcessor;

    /**
     * Constructor
     */
    public Player(){
        this.ship = new PlayerShip();
        this.level = new Level(this, 30);
        this.numLevel = 1;
        this.inputProcessor = new MyInputProcessor(this);
        this.lifes = 5;
    }

    public int getNumLevel() {
        return numLevel;
    }

    public void setNumLevel(int numLevel) {
        this.numLevel = numLevel;
    }

    public void setLevel(Level level){
        this.level = level;
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

    public PlayerShip getShip(){
        return this.ship;
    }

    public MyInputProcessor getInputProcessor(){
        return this.inputProcessor;
    }
}

