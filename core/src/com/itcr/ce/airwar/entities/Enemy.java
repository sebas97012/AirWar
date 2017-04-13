package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.airwar.Random;

/**
 * Created by Arturo on 25/3/2017.
 */
public abstract class Enemy {
    //Atributos que tienen que ver con la parte grafica
    protected Texture texture;
    protected Sprite sprite;
    protected Vector2 out = new Vector2();
    protected Vector2[] dataSet = new Vector2[4];
    protected CatmullRomSpline<Vector2> path;
    protected float current = 0;
    protected float x;
    protected float y;
    protected boolean dispose = false;
    protected float lastShoot;
    protected float elapsedTime;

    //Atributos que tienen que ver con la parte logica
    protected int score;
    protected int life;
    protected int damage;
    //protected PowerUp powerUp;
    protected float speed;

    /**
     * Constructor
     * @param scale Escala segun la pantalla
     * @param xPosition Posicion en x
     * @param yPosition Posicion en y
     */
    public Enemy(String texture, float scale, int xPosition, int yPosition){
        this.texture = new Texture(Gdx.files.internal(texture));
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight());
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.sprite.setPosition(xPosition, yPosition);
    }

    public float getElapsetTime(){
        return this.elapsedTime;
    }

    public void setElapsedTime(float elapsedTime){
        this.elapsedTime = elapsedTime;
    }

    public void setLastShoot(float lastShoot){
        this.lastShoot = lastShoot;
    }

    public float getLastShoot(){
        return this.lastShoot;
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }
    /*
        public PowerUp getPowerUp() {
            return powerUp;
        }

        public void setPowerUp(PowerUp powerUp) {
            this.powerUp = powerUp;
        }
    */
    public void setDispose(){
        this.dispose = true;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public Vector2 getOut(){
        return this.out;
    }

    public CatmullRomSpline getPath(){
        return this.path;
    }

    /**
     * Metodo que se encarga de crear una ruta para el enemigo
     */
    public void initialPath() {
        float xStart = Random.getRandomNumber(0 + sprite.getWidth(), MyGdxGame.appWidth - sprite.getWidth()); //Punto inicial x
        float xEnd = Random.getRandomNumber(0 + sprite.getWidth(), MyGdxGame.appWidth - sprite.getWidth()); //Punto final x
        float yEnd = Random.getRandomNumber(0, MyGdxGame.appHeight);

        //Componentes del ControlPoint1
        float controlPoint1X = Random.getRandomNumber(0 + sprite.getWidth(), MyGdxGame.appWidth - sprite.getWidth());
        float controlPoint1Y = Random.getRandomNumber(0 + sprite.getHeight(), MyGdxGame.appHeight - sprite.getHeight());

        //Componentes del ControlPoint2
        float controlPoint2X = Random.getRandomNumber(0 + sprite.getWidth(), MyGdxGame.appWidth - sprite.getWidth());
        float controlPoint2Y = Random.getRandomNumber(0, MyGdxGame.appHeight - sprite.getHeight());//controlPoint1Y);

        //Se crean los vectores
        Vector2 start = new Vector2(xStart, MyGdxGame.appHeight);
        Vector2 end = new Vector2(xEnd, yEnd);
        Vector2 controlPoint1 = new Vector2(controlPoint1X, controlPoint1Y);
        Vector2 controlPoint2 = new Vector2(controlPoint2X, controlPoint2Y);

        dataSet[0] = start;
        dataSet[1] = controlPoint2;
        dataSet[2] = controlPoint1;
        dataSet[3] = end;

        //Ruta del enemigo
        path = new CatmullRomSpline<Vector2>(dataSet, true);
    }

    /**
     * Metodo que se encarga de renderizar el enemigo
     * @param batch batch
     */
    public void render(SpriteBatch batch)
    {
        current += Gdx.graphics.getDeltaTime() * speed;
        if(current >= 1)
            current -= 1;
        path.valueAt(out, current); //Se calculan las componentes x y y del vector segun el deltaTime
        sprite.setRotation((float)calcRotationAngle(x, y, out.x, out.y)-180); //Se obtiene el angulo de rotacion correspondiente
        x = out.x;
        y = out.y;
        sprite.setPosition(x, y); //Se coloca en la posicion
        sprite.draw(batch); //Se dibuja
    }

    /**
     * Metodo que calcula en angulo de rotacion
     * @return El angulo de rotacion
     */
    public double calcRotationAngle(float cX, float cY, float tX, float tY){
        double theta = Math.atan2(tY - cY, tX - cX); //Calcula el angulo theta (en radianes)
        // de los valores de delta y y delta x
        theta += Math.PI/2.0; //Gira el angulo theta 90 grados en direccion horaria

        double angle = Math.toDegrees(theta); //Convierte a grados
        if(angle < 0){ //Si es angulo es negativo lo pasamos a positivo
            angle += 360;
        }

        return angle;
    }

    /**
     * Metodo que verifica si el enemigo a chocado contra algun objeto
     * @param rectangle
     * @return
     */
    public boolean checkOverlap(Rectangle rectangle){
        return !(x > rectangle.x + rectangle.width || x + sprite.getWidth() < rectangle.x || y > rectangle.y + rectangle.height || y + sprite.getHeight() < rectangle.y);
    }

    public void dispose(){
        texture.dispose();
    }
}