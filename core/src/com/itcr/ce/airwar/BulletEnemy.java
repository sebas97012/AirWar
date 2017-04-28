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
 * Created by Arturo on 25/3/2017.
 */
public class BulletEnemy {
    private Texture texture;
    private Sprite sprite;
    private Sound sound;
    private Vector2 BulletEVector = new Vector2();
    private boolean dispose = false;
    private int damage;
    private int enemyType;

    private float xStart;
    private float yStart;
    private float xEnd;
    private float yEnd;

    private int BulletDirection = 0;

    /**
     * Constructor
     * @param texturePath Imagen correspondiente al tipo de bala
     * @param scale Escala
     * @param xPosition Posicion inicial en x
     * @param yPosition Posicion inicial en y
     */
    public BulletEnemy(String texturePath, float scale, float xPosition, float yPosition, float speed, int damage, int enemyType){
        this.texture = new Texture(Gdx.files.internal(texturePath)); //Se carga la textura
        this.sprite = new Sprite(this.texture); //Se crea el sprite
        this.sprite.rotate(180);
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight()); //Se le coloca la escala
        this.sprite.setPosition(xPosition, yPosition); //Se coloca el sprite en la posicion inicial correspondiente
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/enemyShoot.wav"));
        this.damage = damage;
        this.enemyType = enemyType;

        BulletEVector.x = (float) xPosition;
        BulletEVector.y = (float) yPosition;

        this.xStart = xPosition;                            //Posición inicial de la bala
        this.yStart = yPosition;
        this.xEnd = PlayerShip.getPlaneLocation().x;        //Hacia donde se dirije la bala
        this.yEnd = PlayerShip.getPlaneLocation().y;
    }

    //Balas de jefe
    public BulletEnemy(String texturePath, float scale, float xPosition, float yPosition, float bossWidth, float speed, int damage, int enemyType){
        this.texture = new Texture(Gdx.files.internal(texturePath)); //Se carga la textura
        this.sprite = new Sprite(this.texture); //Se crea el sprite
        this.sprite.rotate(180);
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight()); //Se le coloca la escala
        this.sprite.setPosition(xPosition, yPosition); //Se coloca el sprite en la posicion inicial correspondiente
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/enemyShoot.wav"));
        this.damage = damage;
        this.enemyType = enemyType;

        int randomPosBullet = Random.getRandomNumber((int) xPosition, (int) xPosition + (int) bossWidth);
        BulletEVector.x = randomPosBullet;
        BulletEVector.y = yPosition;

        BulletDirection = Random.getRandomNumber(-1, 1);
    }

    public float getY(){
        return this.BulletEVector.y;
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public Sound getSound(){
        return this.sound;
    }

    public int getDamage(){
        return this.damage;
    }

    /**
     * Metodo que se encarga de renderizar la bala
     * @param batch batch en el que dibujar
     */
    public void render(SpriteBatch batch){
        if (this.enemyType == 0 || this.enemyType == 1 || this.enemyType == -1) {
            BulletEVector.y -= 5;
        }
        if (this.enemyType == 3){                      //Tower
            if (this.xStart < this.xEnd){
                BulletEVector.x += 3;
                BulletEVector.y += (yEnd - yStart) / (xEnd - xStart) * 2;   //Supuestamente una recta, para que siga esa trayectoria
            }
            if (this.xStart > this.xEnd){
                BulletEVector.x -= 3;
                BulletEVector.y -= (yEnd - yStart) / (xEnd - xStart) * 2;
            }
        }else if (this.enemyType == 4){             //MissileTower
            if (BulletEVector.y < PlayerShip.getPlaneLocation().y) {
                BulletEVector.y += 3;
            }
            if (BulletEVector.y > PlayerShip.getPlaneLocation().y) {
                BulletEVector.y -= 3;
            }
            if (xStart < xEnd){
                BulletEVector.x += 3;
            }if (xStart > xEnd){
                BulletEVector.x -= 3;
            }
        }else{                                      //Boss
            BulletEVector.x += 3 * BulletDirection;     //Dirección aleatoria
            BulletEVector.y -= 2;
        }
    sprite.setPosition(BulletEVector.x, BulletEVector.y); //Se le coloca en la posicion "x" y "y" correspondiente
    sprite.draw(batch);  //Se dibuja en el batch
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
     * Verifica si el sprite ha colisionado contra otro
     * @param rectangle Rectangulo del otro objeto
     * @return True si los dos objetos han chocado
     */
    public boolean checkOverlap(Rectangle rectangle){
        return !(BulletEVector.x > rectangle.x + rectangle.width || BulletEVector.x + sprite.getWidth() < rectangle.x || BulletEVector.y > rectangle.y + rectangle.height || BulletEVector.y + sprite.getHeight() < rectangle.y);
    }

    public void dispose(){
        texture.dispose();
    }
}
