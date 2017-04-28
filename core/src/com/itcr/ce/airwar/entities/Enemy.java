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
import com.itcr.ce.airwar.levels.LevelManager;
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
    protected Vector2 EnemyVector = new Vector2();

    protected boolean dispose = false;
    protected float lastShoot;
    protected float elapsedTime; //Tiempo de disparo transcurrido
    protected PowerUp powerUp;

    //Atributos que tienen que ver con la parte logica
    protected int score;
    protected int life;

    protected int tipo = -1;

    /**
     * Constructor
     * @param texturePath Ruta de la textura del enemigo
     * @param scale Escala
     */
    public Enemy(String texturePath, float scale, int life, int tipo){
        this.life = life;
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight());
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.bulletTexturePath = "bullets/defaultBullet.png"; //Ruta de la textura de la bala
        this.hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav")); //Se crea el sonido de cuando golpean un enemigo
        this.powerUp = createPowerUp(); //Se crea el powerup

        EnemyVector.x = Random.getRandomNumber(0,MyGdxGame.appWidth - (int ) sprite.getWidth());    //Aparece en una posición en x aleatoria
        EnemyVector.y = MyGdxGame.appHeight;                                //Aparece arriba de la pantalla

        this.tipo = tipo;

        if (this.tipo == 5){        //Jefe
            EnemyVector.x = MyGdxGame.appWidth/2 - sprite.getWidth();
        }
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

    public Vector2 getEnemyVector(){
        return this.EnemyVector;
    }

    /**
     * Metodo que crea una bala no teledirigida
     * @return La bala enemiga
     */
    public BulletEnemy shoot(){
        float xStart;
        if (this.tipo != 5) {
            xStart = this.sprite.getX() + (this.sprite.getWidth() / 2); //Se obtiene la posicion en x inicial de la bala
        }else{      //Si jefe
            xStart = EnemyVector.x;       //Si es un jefe, las balas pueden aparecer en cualquier parte abajo de éste
        }
        float yStart = this.sprite.getY();  //Se obtiene la posicion inicial en y de la bala

        BulletEnemy bullet = null;
        if ((this.lastShoot + 0.60f) < this.elapsedTime){
            if (this.tipo == 5){        //Balas jefes
                bullet = new BulletEnemy(this.bulletTexturePath, 1, xStart, yStart, sprite.getWidth() - 20, 1, 1, this.tipo);
            }else{
                bullet = new BulletEnemy(this.bulletTexturePath, 1.2f, xStart, yStart - 20, 0.50f, 1, this.tipo); //Se crea la bala
            }
            this.lastShoot = this.elapsedTime;
        }
        this.elapsedTime = this.elapsedTime + Gdx.graphics.getDeltaTime();

        return bullet;
    }

    /**
     * Metodo que crea una bala teledirigida
     * @param xEnd Posicion en x a donde dirigirse
     * @param xEnd Posicion en x a donde dirigirse
     * @param yEnd Posicion en y a donde dirigirse
     * @return La bala enemiga
     */
    public BulletEnemy shoot(float xEnd, float yEnd){
        float xStart = this.sprite.getX() + (this.sprite.getWidth() / 2); //Se obtiene la posicion en x inicial de la bala
        float yStart = this.sprite.getY();      //Se obtiene la posicion inicial en y de la bala
        BulletEnemy bullet = null;

        if ((this.lastShoot + 3.0f) < this.elapsedTime) {
            float scale = 0;
            float speed = 0;

            if (this.bulletTexturePath == "bullets/defaultBullet.png") {    //En caso de usar la textura por defecto
                scale = 1.2f;       //Escala de la bala por defecto
            }

            if (this.bulletTexturePath == "bullets/missile.png") {          //Caso en el que corresponde a un misil
                scale = 0.6f;       //Escala para los misiles
            }else {
                bullet = new BulletEnemy(this.bulletTexturePath, scale, xStart, yStart - 20, speed, 1, this.tipo); //Se crea la bala
            }
            this.lastShoot = this.elapsedTime;
        }
        this.elapsedTime = this.elapsedTime + Gdx.graphics.getDeltaTime();

        return bullet;
    }

    public void updateLife(int damage){
        this.life -= damage;
    }

    /**
     * Metodo que se encarga de renderizar el enemigo
     * @param batch batch
     */
    public void render(SpriteBatch batch){
        if (this.tipo == 0) {                               //FighterBomber
            EnemyVector.y -= 1;
        }
        if (this.tipo == -1 || this.tipo == 1) {           //Jet
            if (EnemyVector.x <= 0 || EnemyVector.x >= MyGdxGame.appWidth - sprite.getWidth()) {    //Si llega a un borde lateral
                this.tipo *= -1;             //Cambia la dirección de x
            }

            EnemyVector.x -= this.tipo * 6;
            EnemyVector.y -= Math.abs(this.tipo) * 4;
        }
        if (this.tipo == 2){            //Kamikaze
            if (EnemyVector.x < (int) PlayerShip.getPlaneLocation().x){
                EnemyVector.x += 5;
            }
            if (EnemyVector.x > (int) PlayerShip.getPlaneLocation().x){
                EnemyVector.x -= 5;
            }                   //Si ya está en x igual que el jugador, no cambia
            EnemyVector.y -= 4;

        }if (this.tipo == 5){           //Jefe
            //EnemyVector.x += this.tipo*2;
            if (EnemyVector.y > MyGdxGame.appHeight-sprite.getHeight()){
                EnemyVector.y -= 2;
            }
            //}if (EnemyVector.x <= 0 || EnemyVector.x >= MyGdxGame.appWidth-sprite.getWidth()){
            //    this.tipo *= -1;
            //}

        }

        //sprite.setRotationAngle((float) calcRotationAngle(x,y,BulletEVector.x,BulletEVector.y)-180)

        sprite.setPosition(EnemyVector.x, EnemyVector.y);   //Se coloca en la posición
        sprite.draw(batch);                                 //Se dibuja
    }


    /**
     * Metodo que verifica si el enemigo a chocado contra algun objeto
     * @param rectangle
     * @return
     */
    public boolean checkOverlap(Rectangle rectangle){
        return !(EnemyVector.x > rectangle.x + rectangle.width || EnemyVector.x + sprite.getWidth() < rectangle.x || EnemyVector.y > rectangle.y + rectangle.height || EnemyVector.y + sprite.getHeight() < rectangle.y);
    }

    public void dispose(){
        texture.dispose();
    }

}