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
    protected Level nextLevel;
    protected int numEnemies;
    protected Player player;
    protected PlayerShip playerShip;
    protected String texture = "ground/space-2.png";
    protected LinkedList<BulletPlayer> bulletPlayerCollection = new LinkedList<BulletPlayer>();
    protected LinkedList<Enemy> enemyCollection = new LinkedList<Enemy>();
    protected LinkedList<BulletEnemy> bulletEnemyCollection = new LinkedList<BulletEnemy>();
    protected LinkedList<Explosion> explosionCollection = new LinkedList<Explosion>();
    protected Queue<Enemy> enemyQueue = new Queue<Enemy>();

    /**
     * Constructor
     *
     * @param player Jugador
     */
    public Level(Player player, int numEnemies) {
        this.player = player;
        this.playerShip = player.getShip();
        this.numEnemies = numEnemies;

        this.createQueueEnemies();
    }

    public String getTexture() {
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

    public LinkedList<Explosion> getExplosionCollection() {
        return explosionCollection;
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
                bullet = new BulletPlayer("bullets/defaultBullet.png", "sounds/pew.wav", 1.2f, (int) x, (int) y, 0.75f, 1); //Se la bala tipo laser
            }
            if (player.getMunitionType() == "misiles") { //Power up misil
                bullet = new BulletPlayer("bullets/defaultBullet.png", "sounds/pew.wav", 1.2f, (int) x, (int) y, 0.75f, 1); //Se la bala tipo misil
            }
            player.setMunition(player.getMunition() - 1); //Se elimina la municion utilizada
        } else {
            bullet = new BulletPlayer("bullets/defaultBullet.png", "sounds/pew.wav", 1.2f, (int) x, (int) y, 0.75f, 1); //Se crea la bala por defecto
        }
        bullet.initialPath((int) x, (int) y, MyGdxGame.appHeight); //Se el asigna la trayectoria

        bullet.getSound().play(0.03f);
        bulletPlayerCollection.insertAtEnd(bullet); //Se añade a la lista de balas
    }

    public void enemiesShoot() {
        for (int i = 0; i < this.enemyCollection.getSize(); i++) {
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

        //Caso en el que el enemigo es un Jet o Bombardero
        if (enemy.getClass() != Kamikaze.class && enemy.getClass() != Tower.class && enemy.getClass() != MissileTower.class) {
            if ((enemy.getLastShoot() + 0.60f) < enemy.getElapsetTime()) {
                BulletEnemy bullet = new BulletEnemy("bullets/defaultBullet.png", 1.2f, xStart, yStart - 20, 0.50f, 1); //Se crea la bala
                bullet.initialPath(xStart, yStart, xStart, (-2 * enemy.getSprite().getHeight()));
                bulletEnemyCollection.insertAtEnd(bullet); //Se agrega la bala a la lista
                bullet.getSound().play();
                enemy.setLastShoot(enemy.getElapsetTime());
            }
        }

        //Caso en el que el enemigo es una torre normal o de misiles
        if(enemy.getClass() == Tower.class || enemy.getClass() == MissileTower.class){
            if ((enemy.getLastShoot() + 3.0f) < enemy.getElapsetTime()) {
                float xEnd = playerShip.getPlaneLocation().x; //Se obtiene la posicion del jugador en x
                float yEnd = playerShip.getPlaneLocation().y;//Se obtiene la posicion del jugador en y

                String texturePath = "bullets/defaultBullet.png"; //Textura por defecto
                float scale = 1.2f; //Escala por defecto
                float speed = 0.50f; //Velocidad por defecto

                if (enemy.getClass() == MissileTower.class) { //Caso en el que la torre corresponde a una de misiles
                    texturePath = "bullets/missile.png";
                    scale = 0.6f;
                    speed = 0.40f;
                }

                BulletEnemy bullet = new BulletEnemy(texturePath, scale, xStart, yStart - 20, speed, 1); //Se crea la bala
                bullet.initialPath(xStart, yStart, xEnd, yEnd);
                bulletEnemyCollection.insertAtEnd(bullet); //Se inserta en la lista
                bullet.getSound().play();
                enemy.setLastShoot(enemy.getElapsetTime());
            }
        }
        enemy.setElapsedTime(enemy.getElapsetTime() + Gdx.graphics.getDeltaTime());
    }

    /**
     * Método que crea las explosiones
     * @param xPos Posicion en x de la explosion
     * @param yPos Posicion en y de la explosion
     * @param width Anchura del objeto que ha sido destruido
     * @param height Altura del objeto que ha sido destruido
     */
    public void createExplosion(float xPos, float yPos, float width, float height) {
        Explosion explosion = new Explosion(xPos, yPos, width, height);
        explosion.getSound().play(0.10f);
        this.explosionCollection.insertAtEnd(explosion);
    }

    /**
     * Metodo que se encarga de spawnear los enemigos
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
                Enemy enemy = (Enemy) enemyQueue.dequeue().getDataT();
                this.enemyCollection.insertAtEnd(enemy);

                if(enemy.getClass() == Kamikaze.class) {
                    Kamikaze kamikaze = (Kamikaze) enemy;
                    kamikaze.setPositionPlayer(this.playerShip.getPlaneLocation().x, this.playerShip.getPlaneLocation().y);
                }

                enemy.initialPath();
            }
        }
    }
    /**
     * Metodo que se encarga de crear una cola de enemigos
     */
    public void createQueueEnemies(){
        for(int i = 0; i < this.numEnemies; i++){
            Enemy enemy;

            int randomEnemy = Random.getRandomNumber(0, 4);
            if (randomEnemy == 0) {
                enemy = new Jet();
            } else {
                if (randomEnemy == 1) {
                    enemy = new Kamikaze();
                } else {
                    if (randomEnemy == 2) {
                        enemy = new Tower();
                    } else {
                        if (randomEnemy == 3) {
                            enemy = new MissileTower();
                        } else {
                            enemy = new FighterBomber();
                        }
                    }
                }
            }
            enemyQueue.enqueue(enemy);
        }
    }
}
