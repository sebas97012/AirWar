package com.itcr.ce.airwar.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.Player;

/**
 * Created by Paz on 14/04/2017.
 */
public abstract class PowerUp {
    //Atributos gráficos
    protected Texture texture;
    protected Sprite sprite;
    protected Vector2 out = new Vector2();
    protected Vector2[] dataSet = new Vector2[2];
    protected CatmullRomSpline<Vector2> path;
    protected float current = 0;
    protected float x;
    protected float y;
    protected boolean dispose = false;
    protected float lastShoot;
    protected float elapsedTime;
    protected float speed;
    protected Sound soundUsed;
    protected Sound soundCollected;

    /**
     * Constructor
     * @param scale Escala segun la pantalla

     */
    public PowerUp(String texture, float scale){
        this.texture = new Texture(Gdx.files.internal(texture));
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(scale * this.texture.getWidth(), scale * this.texture.getHeight());
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.speed = 0f;
        this.soundUsed = Gdx.audio.newSound(Gdx.files.internal("sounds/powerupsound.wav"));
        this.soundCollected = Gdx.audio.newSound(Gdx.files.internal("sounds/powerupcollected.wav"));
    }


    /**
     * Metodo que se encarga de establecer el camino que va a seguir el powerup
     */
    public void initialPath(float xPosition, float yPosition){

        Vector2 start = new Vector2(xPosition,yPosition);
        Vector2 end = new Vector2(xPosition, (-2 * this.texture.getHeight()));

        this.dataSet[0] = start;
        this.dataSet[1] = end;

        this.path = new CatmullRomSpline<Vector2>(dataSet, true); //Ruta del powerup
    }


    /**
     * Metodo que se encarga de renderizar powerups
     * @param batch batch
     */
    public void render(SpriteBatch batch) {

        float deltaTime = Gdx.graphics.getDeltaTime();


        current += deltaTime * speed;
        if (current >= 1)
            current -= 1;
        path.valueAt(out, current); //Se calculan las componentes x y y del vector segun el deltaTime
        x = out.x;
        y = out.y;
        sprite.setPosition(x, y); //Se coloca en la posicion
        sprite.draw(batch); //Se dibuja
    }

    public float getLastShoot(){
        return this.lastShoot;
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

    public CatmullRomSpline getPath(){
        return this.path;
    }

    public Sound getSoundUsed() {
        return soundUsed;
    }

    public Sound getSoundCollected() {
        return soundCollected;
    }

    /**
     * Metodo que verifica si un jugador capturó un power up
     * @param rectangle
     * @return
     */
    public boolean checkOverlap(Rectangle rectangle){
        return !(x > rectangle.x + rectangle.width || x + sprite.getWidth() < rectangle.x || y > rectangle.y + rectangle.height || y + sprite.getHeight() < rectangle.y);
    }

    public void dispose(){
        texture.dispose();
    }

    public void usePowerUp(Player player){

    }

}