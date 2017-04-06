package com.itcr.ce.airwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Arturo on 25/3/2017.
 */
public class Ground {
    Texture tex;
    Sprite ground;
    Sprite ground2;
    float _width;
    float _height;
    float _yVelocity;

    /**
     * Constructor
     * @param groundTexturePath Textura del fondo
     * @param appWidth Anchura de la app
     * @param appHeight Altura de la app
     * @param initalYVelocity Velocidad en Y del fondo
     */
    public Ground(String groundTexturePath, float appWidth, float appHeight, float initalYVelocity){
        _width = appWidth;
        _height = appHeight;
        _yVelocity = initalYVelocity;
        tex = new Texture(groundTexturePath);
        ground = new Sprite(tex);
        ground.setSize(_width, _height);

        ground2 = new Sprite(tex);
        ground2.setSize(_width, _height);
        ground2.setPosition(0.0f, ground.getHeight()+ground.getY());
    }

    /**
     * Metodo que se encarga de renderizar el fondo
     * @param batch Batch donde dibujarlo
     */
    public void render(SpriteBatch batch)
    {
        float deltaYV = -(Gdx.graphics.getDeltaTime() * _yVelocity);
        if (ground.getY() <= -_height){
            // off screen - move back up
            ground.setPosition(0, ground2.getY()+ground2.getHeight());
        }
        if (ground2.getY() <= -_height){
            // off screen - move back up
            ground2.setPosition(0, ground.getY()+ground.getHeight());
        }
        ground.translateY(Math.round(deltaYV));
        ground2.translateY(Math.round(deltaYV));
        //System.out.println("g1 pos: x-"+(int)(ground.getX())+" y:"+(int)(ground.getY()));
        //System.out.println("g2 pos: x-"+(int)(ground2.getX())+" y:"+(int)(ground2.getY()));
        ground.draw(batch);
        ground2.draw(batch);
       // elapsedTime = Gdx.graphics.getDeltaTime() * _yVelocity;
    }

    public void dispose(){
        tex.dispose();
    }
}
