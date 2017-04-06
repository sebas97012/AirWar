package com.itcr.ce.airwar.entities;

/**
 * Created by Arturo on 25/3/2017.
 */
public class Kamikaze extends Enemy {
    private float xPosEnd;
    private float yPosEnd;

    /**
     * Constructor
     * @param texturePath Textura deseada para el enemigo
     * @param scale Escala deseada para la textura
     * @param xPosStart Posicion en el eje x para spawnearlo
     * @param yPosEnd Posicion en el eje y para spawnearlo
     */
    public Kamikaze(String texturePath, float scale, int xPosStart, int yPosStart, int xPosEnd, int yPosEnd){
        super(texturePath, scale, xPosStart, yPosStart);
        this.score = 20;
        this.speed = 0.15f; //Velocidad alta
        this.xPosEnd = xPosEnd;
        this.yPosEnd = yPosEnd;
    }
    /*
    @Override
    public void initialPath(int appWidth, int appHeight){
        //IMPLEMENTAR LOGICA
    }
    */
}

