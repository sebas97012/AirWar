package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Arturo on 1/4/2017.
 */
public class Boss extends Enemy {
    /**
     * Constructor
     */
    public Boss(int level,int tipo){
        super("bosses/jefe_" + level + ".png", 1.0f, 25*level, tipo);
    }
}