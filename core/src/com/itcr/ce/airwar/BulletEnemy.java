package com.itcr.ce.airwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Arturo on 25/3/2017.
 */
public class BulletEnemy {
    //Atributos que tienen que ver con la parte grafica
    private Texture texture;
    private Sprite sprite;
    private Vector2 out = new Vector2();
    private Vector2[] dataSet = new Vector2[2];
    private CatmullRomSpline<Vector2> catmullRomSpline;
    private float current = 0;
    private float x;
    private float y;
    private boolean dispose = false;
    private float speed;
    private int damage;

    /**
     * Constructor
     * @param texturePath Imagen correspondiente al tipo de enemigo
     * @param scale Escala deseada para la textura
     * @param xPosition Posicion en x
     * @param yPosition Posicion en y
     */
    public BulletEnemy(String texturePath, float scale, int xPosition, int yPosition, float speed, int damage){
        this.texture = new Texture(Gdx.files.internal(texturePath)); //Se carga la textura
        this.sprite = new Sprite(this.texture); //Se crea un sprite con la textura
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight()); //Se le aplica la escala
        //this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.sprite.setPosition(xPosition, yPosition); ///Se coloca el sprite en el punto deseado
        this.speed = speed;
        this.damage = damage;
    }

    public void setDispose(){
        this.dispose = true;
    }

    /**
     * Metodo que crea la ruta que va a seguir la bala del enemigo
     * @param xStart Posicion inicial en x
     * @param yStart Posicion inicial en y
     * @param xEnd Posicion final en x
     * @param yEnd Posicion final en y
     */
    public void initialPath(float xStart, float yStart, float xEnd, float yEnd) {
        Vector2 start = new Vector2(xStart, yStart); //Se crea el vector con la posicion inicial
        Vector2 end = new Vector2(xEnd, yEnd); //Se crea el vector con la posicion final

        dataSet[0] = start;
        dataSet[1] = end;

        catmullRomSpline = new CatmullRomSpline<Vector2>(dataSet, true); //Ruta de la bala
    }

    /**
     * Metodo encargado de renderizar la bala en la posicion correspondiente
     * @param batch
     */
    public void render(SpriteBatch batch) {
        current += Gdx.graphics.getDeltaTime() * speed;
        if(current >= 1)
            current -= 1;
        catmullRomSpline.valueAt(out, current); //Segun el tiempo actual, se calcula la posicion en la que deberia estar el vector
        x = out.x; //Se obtiene la componente x
        y = out.y; //Se obtiene la componenete y
        sprite.setPosition(x, y); //Se coloca el sprite en la posicion indicada
        sprite.draw(batch); //Se dibuja
    }

    /**
     * Metodo que verifica si ha impactado contra el jugador
     * @param rectangle Rectangulo del jugador
     * @return Si ha colisionado o no
     */
    public boolean checkOverlap(Rectangle rectangle){
        return !(x > rectangle.x + rectangle.width || x + sprite.getWidth() < rectangle.x || y > rectangle.y + rectangle.height || y + sprite.getHeight() < rectangle.y);
    }

    public void dispose(){
        texture.dispose();
    }
}
