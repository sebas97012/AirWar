package com.itcr.ce.airwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.entities.PlayerShip;

/**
 * Created by Arturo on 3/4/2017.
 */
public class BulletPlayer {
    private Texture texture;
    private Sprite sprite;
    private Sound sound;
    private Vector2 BulletPVector = new Vector2();
    private boolean dispose = false;
    private int damage;

    /**
     * Constructor
     * @param texturePath Imagen correspondiente al tipo de bala
     * @param scale Escala
     */
    public BulletPlayer(String texturePath, String soundPath, float scale, float speed, int damage){
        this.texture = new Texture(Gdx.files.internal(texturePath)); //Se carga la textura
        this.sprite = new Sprite(this.texture); //Se crea el sprite
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight()); //Se le coloca la escala
        //this.sprite.setPosition(xPosition, ); //Se coloca el sprite en la posicion inicial correspondiente
        this.sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
        this.damage = damage;

        BulletPVector.x = PlayerShip.getPlaneLocation().x + PlayerShip.getSubjectSprite().getWidth() / 2 - 10;
        BulletPVector.y = PlayerShip.getPlaneLocation().y + PlayerShip.getSubjectSprite().getHeight();

        this.sprite.setPosition(BulletPVector.x,BulletPVector.y); //Se coloca el sprite en la posicion inicial correspondiente
    }

    public float getY(){
        return this.BulletPVector.y;
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public Sound getSound(){
        return this.sound;
    }

    public int getDamage() {
        return damage;
    }


    /**
     * Metodo que se encarga de renderizar la bala
     * @param batch batch en el que dibujar
     */
    public void render(SpriteBatch batch) {
        BulletPVector.y += 15;      //Componente y del vector

        sprite.setPosition(BulletPVector.x, BulletPVector.y); //Se le coloca en la posicion "x" y "y" correspondiente
        sprite.draw(batch);     //Se dibuja en el batch
    }

    /**
     * Verifica si el sprite ha colisionado contra otro
     * @param rectangle Rectangulo del otro objeto
     * @return True si los dos objetos han chocado
     */
    public boolean checkOverlap(Rectangle rectangle){
        return !(BulletPVector.x > rectangle.x + rectangle.width || BulletPVector.x + sprite.getWidth() < rectangle.x || BulletPVector.y > rectangle.y + rectangle.height || BulletPVector.y + sprite.getHeight() < rectangle.y);
    }

    public void dispose(){
        texture.dispose();
    }
}