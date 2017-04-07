package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.itcr.ce.airwar.entities.*;
import com.itcr.ce.airwar.Player;
import com.itcr.ce.airwar.entities.PlayerShip;
import com.itcr.ce.data.*;
import com.itcr.ce.airwar.*;
import com.itcr.ce.airwar.Random;

/**
 * Created by Arturo on 4/4/2017.
 */
public class Level {
    private Level nextLevel;
    private Player player;
    private PlayerShip playerShip;
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
        this.playerShip = player.getPlane();
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
     * Metodo que se encarga de crear una bala del jugador
     */
    public void createPlayerBullet() {
        float x = playerShip.getSubjectSprite().getX() + (playerShip.getTexture().getWidth() / 2); //Se obtiene la posicion del jugador en el eje x
        float y = playerShip.getSubjectSprite().getY() + 50 + (playerShip.getTexture().getHeight() / 4); //Se obtiene la posicion del jugador en el eje y

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

    public void enemiesShoot(){
        for(int i = 0; i < this.enemyCollection.getSize(); i++){
            Enemy enemy = (Enemy) this.enemyCollection.getElement(i).getDataT();

            this.createBulletEnemy(enemy);
        }
    }

    /**
     * Metodo que se encarga de crear una bala enemiga
     */
    private void createBulletEnemy(Enemy enemy) {
        float xStart = enemy.getSprite().getX() + (enemy.getSprite().getWidth() / 2);
        float yStart = enemy.getSprite().getY();

        if (enemy.getClass() != Kamikaze.class) {
            if ((enemy.getLastShoot() + 0.60f) < enemy.getElapsetTime()) {
                BulletEnemy bullet = new BulletEnemy("airplane/B_2.png", 1.2f, xStart, yStart - 20, 0.50f, 1);
                bullet.initialPath(xStart, yStart);
                bulletEnemyCollection.insertAtEnd(bullet);
                enemy.setLastShoot(enemy.getElapsetTime());
            }

            enemy.setElapsedTime(enemy.getElapsetTime() + Gdx.graphics.getDeltaTime());
        }
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
                enemy = new Jet(0.45f, MyGdxGame.appWidth / 2, MyGdxGame.appHeight / 2);
            }else {
                if (randomEnemy == 0) {
                    float xPosEnd = this.playerShip.getPlaneLocation().x;
                    float yPosEnd = this.playerShip.getPlaneLocation().y;
                    enemy = new Kamikaze(1.3f, MyGdxGame.appWidth / 2, MyGdxGame.appHeight / 2, xPosEnd, yPosEnd);
                }else {
                    enemy = new FighterBomber(0.45f, MyGdxGame.appWidth / 2, MyGdxGame.appHeight / 2);

                }
            }
            enemy.initialPath();
            enemyCollection.insertAtEnd(enemy);
        }
    }
}
