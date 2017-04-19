package com.itcr.ce.airwar.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.itcr.ce.airwar.Player;


/**
 * Created by Paz on 14/04/2017.
 */
public class Missile extends PowerUp {
    /**
     * Constructor

     */
    public Missile() {
        super("bullets/missile.png",0.6f);
        this.speed = 0.15f;

    }

    @Override

    public void usePowerUp(Player player) {
        player.setMunitionType("misil"); //Se establece un misil como tipo de municion
        player.setMunition(20);
    }
}