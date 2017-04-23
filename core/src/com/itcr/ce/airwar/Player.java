package com.itcr.ce.airwar;

import com.itcr.ce.airwar.entities.PlayerShip;
import com.itcr.ce.airwar.levels.*;
import com.itcr.ce.airwar.powerups.PowerUp;
import com.itcr.ce.data.Stack;

/**
 * Created by Arturo on 4/4/2017.
 */
public class Player {
    private int lifes;
    private String munitionType;
    private int munition = 0;
    private PlayerShip ship;
    private Level level;
    private int score;
    private int scoreCounter;
    private MyInputProcessor inputProcessor;
    private boolean invincibility = false;
    private Stack <PowerUp> powerUpStack = new Stack<PowerUp>();
    private int shieldLife = 0;

    /**
     * Constructor
     */
    public Player(){
        this.ship = new PlayerShip();
        this.level = new Level1();
        this.inputProcessor = new MyInputProcessor(this);
        this.lifes = 5;
        this.score = 0;
    }

    /**
     * Metodo que actualiza el score; y cada vez que obtiene 200 puntos agrega una vida
     * @param enemyScore
     */
    public void updateScore(int enemyScore){
        this.score += enemyScore; //Se suma el score del enemigo
        this.scoreCounter += enemyScore; //Se suma el score del enemigo al contador

        if(this.scoreCounter > 200){ //Si ya se han obtenido mas de 200 puntos
            this.lifes += 1; //Se añade una vida

            if((this.scoreCounter - 200) > 0) this.scoreCounter = this.scoreCounter - 200;
        }
    }

    /**
     * Metodo que actualiza las vidas
     */
    public void updateLifes(){
        if(invincibility != true){ //Si el usuario no tiene invencibilidad
            if (shieldLife == 0) { //Si el usuario no tiene escudo
                this.lifes -= 1; //Se reduce una vida
                this.powerUpStack.cleanList();
                this.munition = 0;
            }else{
                if(this.shieldLife > 0) this.shieldLife -= 1;
            }
        }

    }

    /**
     * Metodo que cambia el estado de la invencibilidad
     */
    public void updateInvincibility() {
        if (this.invincibility == false) {
            this.invincibility = true;
        } else {
            this.invincibility = false;
        }
    }

    public void usePowerUp() {

        if (powerUpStack.getSize() > 0) {
            PowerUp powerUp = (PowerUp) powerUpStack.extract().getDataT();
            powerUp.usePowerUp(this);
            powerUp.getSoundUsed().play();
        }
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isInvincibility() {
        return invincibility;
    }

    public Stack<PowerUp> getPowerUpStack() {
        return powerUpStack;
    }

    public void setShieldLife(int shieldLife) {
        this.shieldLife = shieldLife;
    }

    public int getShieldLife() {
        return shieldLife;
    }
}