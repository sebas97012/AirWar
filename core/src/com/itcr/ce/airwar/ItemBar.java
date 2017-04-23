package com.itcr.ce.airwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.itcr.ce.airwar.powerups.Missile;
import com.itcr.ce.airwar.powerups.PowerUp;
import com.itcr.ce.airwar.powerups.Shield;
import com.itcr.ce.data.Stack;

/**
 * Created by Arturo on 23/4/2017.
 */
public class ItemBar {
    private Sprite sprite;
    private Texture shieldTexture;
    private Texture missileTexture;
    private Texture laserTexture;
    private int xItemPosition = MyGdxGame.appWidth - 50 + 6;
    private int yItemLarge = 40;
    private float yInitialPosition;

    /**
     * Constructor
     */
    public ItemBar(){
        this.sprite = new Sprite(new Texture(Gdx.files.internal("itembar/item_bar.png")));
        this.sprite.setX(MyGdxGame.appWidth - 50);
        this.sprite.setY(150);
        this.shieldTexture = new Texture(Gdx.files.internal("itembar/shielditembar.png")); //Se carga la textura del escudo
        this.missileTexture = new Texture(Gdx.files.internal("itembar/missileitembar.png")); //Se carga la textura del misil
        this.laserTexture = new Texture(Gdx.files.internal("itembar/laseritembar.png")); //Se carga la textura del laser
        this.yInitialPosition = 150 + this.sprite.getHeight();
    }

    /**
     * Metodo para dibujar la itembar en el Batch
     * @param batch Batch donde dibujar
     */
    public void draw(Batch batch){
        this.sprite.draw(batch);
    }

    /**
     * Metodo que actualiza el itembar
     * @param powerUpStack Pila de power ups
     * @param batch Batch donde dibujar
     */
    public void update(Stack powerUpStack, Batch batch){
        for(int i = 0; i < powerUpStack.getSize(); i++){
            PowerUp powerUp = (PowerUp) powerUpStack.getElement(i).getDataT(); //Se obtiene el power up
            Sprite sprite = null; //Se inicializa el sprite

            if(powerUp.getClass() == Shield.class){
                sprite = new Sprite(shieldTexture);
            } else if(powerUp.getClass() == Missile.class){
                sprite = new Sprite(missileTexture);
            } else{
                sprite = new Sprite(laserTexture);
            }

            sprite.setX(xItemPosition); //Se coloca en la posicion x correspondiente
            sprite.setY(this.yInitialPosition - (this.yItemLarge * (i + 1))); //Se coloca el power up en la casilla correspondiente
            sprite.draw(batch); //Se dibuja
        }
    }
}
