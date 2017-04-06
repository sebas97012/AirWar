package com.itcr.ce.airwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.itcr.ce.airwar.entities.PlayerPlane;
import com.itcr.ce.airwar.levels.*;

/**
 * Created by Arturo on 4/4/2017.
 */
public class MyInputProcessor implements InputProcessor{
    private boolean directionKeyReleased;
    private float elapsedTime;
    private float lastInputCheck;
    private Player player;
    private PlayerPlane playerPlane;
    private Level level;

    /**
     * Constructor
     * @param player Jugador
     */
    public MyInputProcessor(Player player){
        this.player = player;
        this.playerPlane = player.getPlane();
        this.level = player.getLevel();
    }

    /**
     * Metodo que escuchas las entradas del teclado
     * @param deltaTime Tiempo transcurrido entre el frame anterior y el actual
     */
    public void checkInput(float deltaTime) {
        if ((lastInputCheck + 0.1f) < (elapsedTime)) { //Restriccion para la cadencia de disparo
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                player.getLevel().createPlayerBullet();
                lastInputCheck = elapsedTime;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            player.getGameScreen().pause();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            playerPlane.moveShipX(-1); //Direccion negativa en x
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            playerPlane.moveShipX(1); //Direccion positiva en x
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            playerPlane.moveShipY(-1); //Direccion negativa en y
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            playerPlane.moveShipY(1); //Direccion positiva en y
        }
        elapsedTime += deltaTime;
        elapsedTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
