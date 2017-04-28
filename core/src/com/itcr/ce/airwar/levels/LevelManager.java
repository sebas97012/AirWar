package com.itcr.ce.airwar.levels;

import com.badlogic.gdx.utils.Timer;
import com.itcr.ce.airwar.entities.*;
import com.itcr.ce.airwar.Player;
import com.itcr.ce.airwar.entities.PlayerShip;
import com.itcr.ce.airwar.powerups.PowerUp;
import com.itcr.ce.data.*;
import com.itcr.ce.airwar.*;
import com.itcr.ce.airwar.Random;

/**
 * Created by Arturo on 4/4/2017.
 */
public class LevelManager {
    private Player player;
    private PlayerShip playerShip;
    private Level level;
    private LinkedList<BulletPlayer> bulletPlayerCollection = new LinkedList<BulletPlayer>();
    private LinkedList<Enemy> enemyCollection = new LinkedList<Enemy>();
    private LinkedList<BulletEnemy> bulletEnemyCollection = new LinkedList<BulletEnemy>();
    private LinkedList<Explosion> explosionCollection = new LinkedList<Explosion>();
    private LinkedList<PowerUp> powerUpCollection = new LinkedList<PowerUp>();
    private Queue<Enemy> enemyQueue = new Queue<Enemy>();

    /**
     * Constructor
     *
     * @param player Jugador
     */
    public LevelManager(Player player) {
        this.player = player;
        this.playerShip = player.getShip();
        this.level = player.getLevel();

        this.createQueueEnemies();
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

    public LinkedList<Explosion> getExplosionCollection() {
        return explosionCollection;
    }

    public LinkedList<PowerUp> getPowerUpCollection() {
        return powerUpCollection;
    }


    public Queue<Enemy> getEnemyQueue() {
        return enemyQueue;
    }

    /**
     * Metodo que se encarga de crear una bala del jugador
     */
    public void createPlayerBullet() {
        float x = playerShip.getSubjectSprite().getX() + (playerShip.getTexture().getWidth() / 2); //Se obtiene la posicion del jugador en el eje x
        float y = playerShip.getSubjectSprite().getY() + 50 + (playerShip.getTexture().getHeight() / 4); //Se obtiene la posicion del jugador en el eje y

        BulletPlayer bullet = null;

        if (player.getMunition() > 0) { //Si el jugador tiene municion de algun power up
            if (player.getMunitionType() == "laser") { //Power up laser
                bullet = new BulletPlayer("bullets/laser2.png", "sounds/space-laser.wav", 0.6f, 0.75f, 3); //Se la bala tipo laser
            }
            if (player.getMunitionType() == "misil") { //Power up misil
                bullet = new BulletPlayer("bullets/missile.png", "sounds/pew.wav", 0.6f, 0.75f, 2); //Se la bala tipo misil
            }
            player.setMunition(player.getMunition() - 1); //Se elimina la municion utilizada
        } else {
            bullet = new BulletPlayer("bullets/defaultBullet.png", "sounds/pew.wav", 1.2f, 0.75f, 1); //Se crea la bala por defecto
        }

        bullet.getSound().play(0.03f);
        bulletPlayerCollection.insertAtEnd(bullet); //Se añade a la lista de balas
    }

    /**
     * Metodo que se encarga de hacer que los enemigos disparen
     */
    public void enemiesShoot() {
        for (int i = 0; i < this.enemyCollection.getSize(); i++) {
            Enemy enemy = (Enemy) this.enemyCollection.getElement(i).getDataT();
            BulletEnemy bullet = null;

            if (enemy.getClass() == Tower.class || enemy.getClass() == MissileTower.class) { //Caso en el que el enemigo es una torre o una torre de misiles
                float xEnd = playerShip.getPlaneLocation().x; //Se obtiene la posicion del jugador en x
                float yEnd = playerShip.getPlaneLocation().y;//Se obtiene la posicion del jugador en y
                bullet = enemy.shoot(xEnd, yEnd); //Se crea una bala teledirigida
            }else{                 //Caso en que el enemigo no es una torre o una torre de misiles
                bullet = enemy.shoot();
            }

            if(bullet != null){
                bulletEnemyCollection.insertAtEnd(bullet); //Se inserta en la lista
                bullet.getSound().play(); //Se reproduce el sonido de disparo
            }
        }
    }

    /**
     * Método que crea las explosiones
     * @param xPos Posicion en x de la explosion
     * @param yPos Posicion en y de la explosion
     * @param width Anchura del objeto que ha sido destruido
     * @param height Altura del objeto que ha sido destruido
     */
    public void createExplosion(float xPos, float yPos, float width, float height) {
        Explosion explosion = new Explosion(xPos, yPos, width, height); //Se crea la explosion
        explosion.getSound().play(0.10f); //Se reproduce el sonido
        this.explosionCollection.insertAtEnd(explosion); //Se agrega a la lista
    }

    /**
     * Metodo que se encarga de spawnear los enemigos de la cola
     */
    public void spawnEnemies() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                insertToEnemyCollection();
            }
        }, 0, 1.0f);
    }

    /**
     * Metodo que toma un enemigo de la cola y lo inserta en la lista de enemigos para renderizar
     */
    public void insertToEnemyCollection() {
        Integer GoOrNot = Math.round((float) Math.random());
        if (GoOrNot == 1) {
            if(enemyQueue.getSize() > 0) {
                Enemy enemy = (Enemy) enemyQueue.dequeue().getDataT();  //Se obtiene el enemigo de la cola
                this.enemyCollection.insertAtEnd(enemy);                //Se inserta el enemigo en la lista

                if(enemy.getClass() == Kamikaze.class) {                //Si el enemigo es un kamikaze se le asigna la posicion del jugador
                    Kamikaze kamikaze = (Kamikaze) enemy;
                    //////////// ^ ¿Quitar?
                }
            }
        }
    }

    /**
     * Metodo que se encarga de crear una cola de enemigos
     */
    public void createQueueEnemies(){
        Enemy enemyBoss;
        for(int i = 0; i < this.level.getNumberOfEnemies(); i++){
            Enemy enemy;

            int randomEnemy = Random.getRandomNumber(-1, 4);
            if (randomEnemy == 0){
                enemy = new FighterBomber(this.level.getFighterBomberHealt(), randomEnemy);
            } else {
                if (randomEnemy == -1 || randomEnemy == 1) {
                    enemy = new Jet(this.level.getJetHealth(), randomEnemy);
                } else {
                    if (randomEnemy == 2){
                        enemy = new Kamikaze(this.level.getKamikazeHealth(), randomEnemy);
                    } else {
                        if (randomEnemy == 3){
                            enemy = new Tower(this.level.getTowerHealth(), randomEnemy);
                        } else {
                            enemy = new MissileTower(this.level.getMissileTowerHealth(), randomEnemy);
                        }
                    }
                }
            }
            enemyQueue.enqueue(enemy);
        }
        enemyBoss = new Boss(this.level.numLevel,5);
        enemyQueue.enqueue(enemyBoss);
    }
}
