package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.BulletEnemy;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.data.Random;


/**
 * Created by Arturo on 25/3/2017.
 */
public class Kamikaze extends Enemy {

    /**
     * Constructor
     */
    public Kamikaze(int life, int tipo){
        super("ships/kamikazeship.gif", 1.3f, life, tipo);
        this.score = 20;
    }

    @Override
    public BulletEnemy shoot(){
        return null;
    }

}
