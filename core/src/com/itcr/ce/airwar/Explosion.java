package com.itcr.ce.airwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Arturo on 7/4/2017.
 */
public class Explosion {
    private Texture texture;
    private Animation animation;
    private Sound sound;
    private float xPos;
    private float yPos;
    private float width;
    private float height;
    private float elapsedTime = 0.0f;

    /**
     * Constructor
     * @param xPos Posicion x
     * @param yPos Posicion y
     * @param width Anchura de la entidad
     * @param height Altura de la entidad
     */
    public Explosion(float xPos, float yPos, float width, float height) {
        this.texture = new Texture(Gdx.files.internal("explosion/explosion.png")); //Se obtiene la textura
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion-02.wav")); //Se obtiene el sonido
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 5, texture.getHeight() / 5); //Dividimos la textura
        TextureRegion[] walkFrames = new TextureRegion[5 * 5];
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                walkFrames[index++] = tmp[i][j]; //Lo convertimos en una sola fila
            }
        }
        animation = new Animation<TextureRegion>(1/30f, walkFrames);
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public Sound getSound() {
        return sound;
    }

    public void dispose()
    {
        texture.dispose();
    }

    /**
     * Metodo que se encarga de renderizar la animacion
     * @param batch
     */
    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) animation.getKeyFrame(elapsedTime, false); //Se obtiene el cuadro
        batch.draw(currentFrame, xPos, yPos, width, height);                                    //correspondiente al tiempo transcurrido
        elapsedTime += Gdx.graphics.getDeltaTime();
    }
}