package com.itcr.ce.airwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Arturo on 3/4/2017.
 */
public class BulletPlayer {
    private Texture texture;
    private Sprite sprite;
    private Sound sound;
    private Vector2 out = new Vector2();
    private Vector2[] dataSet = new Vector2[2];
    private CatmullRomSpline<Vector2> path;
    private float current = 0;
    private float x;
    private float y;
    private boolean dispose = false;
    private float speed;
    private int damage;

    /**
     * Constructor
     * @param texturePath Imagen correspondiente al tipo de bala
     * @param scale Escala
     * @param xPosition Posicion inicial en x
     * @param yPosition Posicion inicial en y
     */
    public BulletPlayer(String texturePath, String soundPath, float scale, int xPosition, int yPosition, float speed, int damage){
        this.texture = new Texture(Gdx.files.internal(texturePath)); //Se carga la textura
        this.sprite = new Sprite(this.texture); //Se crea el sprite
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight()); //Se le coloca la escala
        this.sprite.setPosition(xPosition, yPosition); //Se coloca el sprite en la posicion inicial correspondiente
        this.sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
        this.speed = speed;
        this.damage = damage;
    }

    public float getY(){
        return this.y;
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public Sound getSound(){
        return this.sound;
    }

    /**
     * Calcula el camino que va a seguir la bala
     * @param x Posicion inicial en el eje x
     * @param y Posicion inicial en el eje y
     * @param appHeight Altura de la ventana de la aplicacion
     */
    public void initialPath(float x, float y, float appHeight) {
        Vector2 start = new Vector2(x, y); //Vector inicial
        Vector2 end = new Vector2(x, appHeight); //Vector final

        dataSet[0] = start;
        dataSet[1] = end;

        path = new CatmullRomSpline<Vector2>(dataSet, true); //Trayecto que va a seguir la bala
    }

    /**
     * Metodo que se encarga de renderizar la bala
     * @param batch batch en el que dibujar
     */
    public void render(SpriteBatch batch) {
        current += Gdx.graphics.getDeltaTime() * speed;
        if(current >= 1)
            current -= 1;

        path.valueAt(out, current); //Se calcula el vector segun el deltaTime
        x = out.x; //Componente x del vector
        y = out.y; //Componente y del vector

        sprite.setPosition(x, y); //Se le coloca en la posicion "x" y "y" correspondiente
        sprite.draw(batch);  //Se dibuja en el batch
    }

    /**
     * Verifica si el sprite ha colisionado contra otro
     * @param rectangle Rectangulo del otro objeto
     * @return True si los dos objetos han chocado
     */
    public boolean checkOverlap(Rectangle rectangle){
        return !(x > rectangle.x + rectangle.width || x + sprite.getWidth() < rectangle.x || y > rectangle.y + rectangle.height || y + sprite.getHeight() < rectangle.y);
    }

    public void dispose(){
        texture.dispose();
    }
}

