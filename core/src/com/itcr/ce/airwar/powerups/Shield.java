package com.itcr.ce.airwar.powerups;

import com.itcr.ce.airwar.Player;

/**
 * Created by Paz on 14/04/2017.
 */
public class Shield extends PowerUp {
    /**
     * Constructor

     */
    public Shield() {
        super("bullets/shield.png",0.1f);
        this.speed = 0.15f;
    }

    public void usePowerUp(Player player) {
        player.setShieldLife(2);  // Salva al jugador ante un disparo o choque

    }

}


