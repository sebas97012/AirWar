package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.BulletEnemy;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.airwar.Random;
import com.itcr.ce.airwar.powerups.Laser;
import com.itcr.ce.airwar.powerups.Missile;
import com.itcr.ce.airwar.powerups.PowerUp;
import com.itcr.ce.airwar.powerups.Shield;

/**
 * Created by Arturo on 25/3/2017.
 */
public abstract class Enemy {
    protected Sound hitSound;
    //Atributos que tienen que ver con la parte grafica
    protected String bulletTexturePath;
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
    protected float elapsedTime; //Tiempo de disparo transcurrido
    protected PowerUp powerUp;

    //Atributos que tienen que ver con la parte logica
    protected int score;
    protected int life;
    protected float speed;

    /**
     * Constructor
     * @param texturePath Ruta de la textura del enemigo
     * @param scale Escala
     */
    public Enemy(String texturePath, float scale, int life){
        this.life = life;
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight());
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.bulletTexturePath = "bullets/defaultBullet.png"; //Ruta de la textura de la bala
        this.hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav")); //Se crea el sonido de cuando golpean un enemigo
        this.powerUp = createPowerUp(); //Se crea el powerup
    }

    public Sound getHitSound() {
        return hitSound;
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }




    public PowerUp createPowerUp() {
        PowerUp powerUp = null;
        Integer GoOrNot = Math.round((float) Math.random());
        if (GoOrNot == 1) {
            int power = Random.getRandomNumber(0 , 2); //
            if (power == 0) {
                powerUp = new Shield();
            } else {
                if (power == 1) {
                    powerUp = new Laser();
                } else {
                    powerUp = new Missile();
                }
            }
        }   return powerUp;

    }

    public void setDispose(){
        this.dispose = true;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public Vector2 getOut(){
        return this.out;
    }

    /**
     * Metodo que crea una bala no teledirigida
     * @return La bala enemiga
     */
    public BulletEnemy shoot(){
        float xStart = this.sprite.getX() + (this.sprite.getWidth() / 2); //Se obtiene la posicion en x inicial de la bala
        float yStart = this.sprite.getY(); //Se obtiene la posicion inicial en y de la bala
        BulletEnemy bullet = null;

        if ((this.lastShoot + 0.60f) < this.elapsedTime) {
            bullet = new BulletEnemy(this.bulletTexturePath, 1.2f, xStart, yStart - 20, 0.50f, 1); //Se crea la bala
            bullet.initialPath(xStart, yStart, xStart, (-2 * this.sprite.getHeight())); //Se establece el camino que va a seguir
            this.lastShoot = this.elapsedTime;
        }
        this.elapsedTime = this.elapsedTime + Gdx.graphics.getDeltaTime();

        return bullet;
    }

    /**
     * Metodo que crea una bala teledirigida
     * @param xEnd Posicion en x a donde dirigirse
     * @param yEnd Posicion en y a donde dirigirse
     * @return La bala enemiga
     */
    public BulletEnemy shoot(float xEnd, float yEnd){
        float xStart = this.sprite.getX() + (this.sprite.getWidth() / 2); //Se obtiene la posicion en x inicial de la bala
        float yStart = this.sprite.getY(); //Se obtiene la posicion inicial en y de la bala
        BulletEnemy bullet = null;

        if ((this.lastShoot + 3.0f) < this.elapsedTime) {
            float scale = 0;
            float speed = 0;

            if(this.bulletTexturePath == "bullets/defaultBullet.png") { //En caso de usar la textura por defecto
                scale = 1.2f; //Escala de la bala por defecto
                speed = 0.50f; //Velocidad de la bala por defecto
            }

            if (this.bulletTexturePath == "bullets/missile.png") { //Caso en el que corresponde a un misil
                scale = 0.6f; //Escala para los misiles
                speed = 0.40f; //Velocidad para los misiles
            }

            bullet = new BulletEnemy(this.bulletTexturePath, scale, xStart, yStart - 20, speed, 1); //Se crea la bala
            bullet.initialPath(xStart, yStart, xEnd, yEnd);
            this.lastShoot = this.elapsedTime;
        }
        this.elapsedTime = this.elapsedTime + Gdx.graphics.getDeltaTime();

        return bullet;
    }

    public void updateLife(int damage){
        this.life -= damage;
    }

    /**
     * Metodo que se encarga de crear una ruta para el enemigo
     */
    public void initialPath() {
        float xStart = Random.getRandomNumber(0 + sprite.getWidth(), MyGdxGame.appWidth - sprite.getWidth()); //Punto inicial x
        float xEnd = Random.getRandomNumber(0 + sprite.getWidth(), MyGdxGame.appWidth - sprite.getWidth()); //Punto final x
        float yEnd = Random.getRandomNumber(0, MyGdxGame.appHeight); //Punto final en y

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