package com.itcr.ce.airwar.powerups;


import com.itcr.ce.airwar.Player;


/**
 * Created by Paz on 14/04/2017.
 */

public class Laser extends PowerUp {
    /**
     * Constructor
     *
     */
    public Laser() {
        super("bullets/laser.png",1.2f);
        this.speed = 0.15f;
    }

    @Override

    public void usePowerUp(Player player){
        player.setMunitionType("laser"); //Se establece un laser como tipo de municion
        player.setMunition(10);
    }
}
