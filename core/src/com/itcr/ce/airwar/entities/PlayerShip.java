package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.MyGdxGame;

/**
 * Created by Arturo on 4/4/2017.
 */
public class PlayerShip {
    private Texture texture;
    private Sprite subjectSprite;
    private float planeScale = 1.2f;
    private Vector2 planeLocation = new Vector2(0, 0);

    /**
     * Constructor
     */
    public PlayerShip() {
        this.texture = new Texture(Gdx.files.internal("ships/playership.gif"));
        subjectSprite = new Sprite(texture); //Se crea el sprite
        subjectSprite.setSize(planeScale * texture.getWidth(), planeScale * texture.getHeight()); //Se coloca la escala al sprite
    }

    /**
     * Metodo para mover la nave en el eje x
     *
     * @param xDirection Negativa si es a la izquierda, positiva si es a la derecha
     */
    public void moveShipX(int xDirection) {
        float newX = this.planeLocation.x + (xDirection * 10); //Se obtiene la nueva posicion

        //Limites en el eje x
        if (newX > MyGdxGame.appWidth - subjectSprite.getWidth()) { //Caso en el que la nave ha llegado al borde derecho
            newX = MyGdxGame.appWidth - subjectSprite.getWidth(); //de la pantalla
        }
        if (newX < 0) { //Caso en el que la nave ha llegado al borde izquierdo
            newX = 0; //de la pantalla
        }

        this.planeLocation.x = newX; //Se aplica el cambio
    }

    /**
     * Metodo que mueve la nave en el eje y
     *
     * @param yDirection Negativa si es hacia abajo, positiva si es hacia arriba
     */
    public void moveShipY(int yDirection) {
        float newY = this.planeLocation.y + (yDirection * 10); //Se obtiene la nueva posicion

        if (newY > MyGdxGame.appHeight - subjectSprite.getHeight()) { //Caso en el que la nave ha llegado al borde
            newY = MyGdxGame.appHeight - subjectSprite.getHeight(); //superior de la pantalla
        }
        if (newY < 0) { //Caso en el que la nave a llegado al borde
            newY = 0; //inferior de la pantalla
        }

        this.planeLocation.y = newY; //Se aplica el cambio
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSubjectSprite() {
        return subjectSprite;
    }

    public Vector2 getPlaneLocation() {
        return planeLocation;
    }
}