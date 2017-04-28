package com.itcr.ce.airwar;

import com.itcr.ce.airwar.entities.PlayerShip;
import com.itcr.ce.airwar.levels.Level;
import com.itcr.ce.airwar.powerups.PowerUp;
import com.itcr.ce.data.Stack;

/**
 * Created by Arturo on 28/4/2017.
 */
public class PlayerData {
    private int lifes;
    private int numLevel;
    private int score;
    private int scoreCounter;
    private String name;
    private int maxScore;
    private int maxLevel;
    private int timePlayed;

    /**
     * Constructor
     * @param name Nombre del jugador
     */
    public PlayerData(String name){
        this.lifes = 5;
        this.numLevel = 1;
        this.score = 0;
        this.scoreCounter = 0;
        this.name = name;
    }

    /**
     * Metodo encargado de actualizar las estadisticas
     * @param lifes Vidas que posee el jugador
     * @param numLevel Numero de nivel en el que se encuentra el jugador
     * @param score Score de la partida actual
     * @param scoreCounter Contador de score para añadir vidas
     * @param name Nombre del jugador
     * @param maxScore Maxima puntuacion obtenida por el jugador
     * @param maxLevel Maximo nivel alcanzado por el jugador
     * @param timePlayed Tiempo de juego
     */
    public void updatePlayerData(int lifes, int numLevel, int score, int scoreCounter, String name, int maxScore, int maxLevel, int timePlayed) {
        this.lifes = lifes;
        this.numLevel = numLevel;
        this.score = score;
        this.scoreCounter = scoreCounter;
        this.name = name;
        this.maxScore = maxScore;
        this.maxLevel = maxLevel;
        this.timePlayed = timePlayed;
    }

    public int getLifes() {
        return lifes;
    }

    public int getNumLevel() {
        return numLevel;
    }

    public int getScore() {
        return score;
    }

    public int getScoreCounter() {
        return scoreCounter;
    }

    public String getName() {
        return name;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getTimePlayed() {
        return timePlayed;
    }
}
