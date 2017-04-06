package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.utils.Timer;
import com.itcr.ce.airwar.entities.*;
import com.itcr.ce.airwar.Player;
import com.itcr.ce.airwar.entities.PlayerPlane;
import com.itcr.ce.data.*;
import com.itcr.ce.airwar.*;
import com.itcr.ce.airwar.Random;
import com.itcr.ce.screens.GameScreen;

/**
 * Created by Arturo on 4/4/2017.
 */
public class Level {
    private Level nextLevel;
    private Player player;
    private PlayerPlane playerPlane;
    private String texture = "ground/space-2.png";//"airplane/woods.png";
    private LinkedList<BulletPlayer> bulletPlayerCollection = new LinkedList<BulletPlayer>();
    private LinkedList<Enemy> enemyCollection = new LinkedList<Enemy>();
    private LinkedList<BulletEnemy> bulletEnemyCollection = new LinkedList<BulletEnemy>();

    /**
     * Constructor
     * @param player Jugador
     */
    public Level(Player player) {
        this.player = player;
        this.playerPlane = player.getPlane();
    }

    public String getTexture(){
        return this.texture;
    }

    public Level getNextLevel() {
        return this.nextLevel;
    }

    public LinkedList<BulletPlayer> getBulletPlayerCollection() {
        return bulletPlayerCollection;
    }

    public LinkedList<Enemy> getEnemyCollection() {
        return enemyCollection;
    }

    public LinkedList<BulletEnemy> getBulletEnemyCollection() {
        return bulletEnemyCollection;
    }

    /**
     * Metodo que se encarga de spawnear una bala del jugador
     */
    public void createPlayerBullet() {
        float x = playerPlane.getSubjectSprite().getX() + (playerPlane.getTexture().getWidth() / 2); //Se obtiene la posicion del jugador en el eje x
        float y = playerPlane.getSubjectSprite().getY() + 50 + (playerPlane.getTexture().getHeight() / 4); //Se obtiene la posicion del jugador en el eje y

        BulletPlayer bullet = null;

        if(player.getMunition() > 0){ //Si el jugador tiene municion de algun power up
            if(player.getMunitionType() == "laser"){ //Power up laser
                bullet = new BulletPlayer("airplane/B_2.png", 1.2f, (int) x, (int) y, 0.75f, 1); //Se la bala tipo laser
            }
            if(player.getMunitionType() == "misiles"){ //Power up misil
                bullet = new BulletPlayer("airplane/B_2.png", 1.2f, (int) x, (int) y, 0.75f, 1); //Se la bala tipo misil
            }
            player.setMunition(player.getMunition() - 1); //Se elimina la municion utilizada
        } else {
            bullet = new BulletPlayer("airplane/B_2.png", 1.2f, (int) x, (int) y, 0.75f, 1); //Se crea la bala por defecto
        }
        bullet.initialPath((int) x, (int) y, MyGdxGame.appHeight); //Se el asigna la trayectoria

        bulletPlayerCollection.insertAtEnd(bullet); //Se añade a la lista de balas
    }

    public void scheduleEnemies(){
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(player.getGameScreen().isPaused() == false) {
                    EnemiesAndClouds();
                }
            }
        }, 0, 1.0f);
    }

    public void EnemiesAndClouds()
    {
        Integer GoOrNot = Math.round((float)Math.random());
        if(GoOrNot == 1){
            Enemy enemy;

            int randomEnemy = Random.getRandomNumber(0, 2);
            if(randomEnemy  == 1) {
                enemy = new Jet("airplane/shipblue0020.png", 0.45f, MyGdxGame.appWidth / 2, MyGdxGame.appHeight / 2);
            }else {
                if (randomEnemy == 0) {
                    enemy = new Kamikaze("airplane/shipgreen0001.gif", 1.25f, MyGdxGame.appWidth / 2, MyGdxGame.appHeight / 2, MyGdxGame.appWidth / 2, MyGdxGame.appHeight / 2);
                }else {
                    enemy = new FighterBomber("airplane/shipred0000.png", 0.45f, MyGdxGame.appWidth / 2, MyGdxGame.appHeight / 2);

                }
            }
            enemy.initialPath(MyGdxGame.appWidth, MyGdxGame.appHeight);
            enemyCollection.insertAtEnd(enemy);
        }
    }
}
