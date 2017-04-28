package com.itcr.ce.airwar;

import com.itcr.ce.airwar.entities.PlayerShip;
import com.itcr.ce.airwar.levels.*;
import com.itcr.ce.airwar.powerups.PowerUp;
import com.itcr.ce.data.LinkedList;
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
    private String name;
    private int maxScore;
    private int maxLevel;
    private int timePlayed;


    /**
     * Constructor
     */
    public Player(PlayerData playerData){
        this.ship = new PlayerShip();
        this.level = new Level1();
        this.inputProcessor = new MyInputProcessor(this);
        this.lifes = playerData.getLifes();
        this.level = Level.createLevel(playerData.getNumLevel());
        this.score = playerData.getScore();
        this.scoreCounter = playerData.getScoreCounter();
        this.name = playerData.getName();
        this.maxScore = playerData.getMaxScore();
        this.maxLevel = playerData.getMaxLevel();
        this.timePlayed = playerData.getTimePlayed();
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

    public void updateStadistics() {
        if (this.maxLevel < this.level.getNumLevel()) {
            this.maxLevel = this.level.getNumLevel();
        }
        if (this.score > this.maxScore) {
            this.maxScore = this.score;
        }


        LinkedList<PlayerData> playerDataList = (LinkedList<PlayerData>) FileXMLManager.getContent("playerdata.xml"); //Se obtiene la lista de estadisticas
        PlayerData playerData2 = (PlayerData) FileXMLManager.getContent("name.xml");
        String name = playerData2.getName();

        for (int i = 0; i < playerDataList.getSize(); i++) {
            PlayerData current = (PlayerData) playerDataList.getElement(i).getDataT(); //Se obtiene el elemento
            if (current.getName() == name) { //Si el nombre corresponde con el nombre ingresado por el usuario se cargan esas estadisticas
                current.updatePlayerData(this.lifes, this.level.getNumLevel(), this.score, this.scoreCounter,
                        this.name, this.maxScore, this.maxLevel, this.timePlayed); //Se actualizan las estadisticas;
            }
        }
        FileXMLManager.writeContent(playerDataList, "playerdata.xml"); //Se vuelve a guardar la lista actualizada
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getTime() {
        return this.timePlayed;
    }
}