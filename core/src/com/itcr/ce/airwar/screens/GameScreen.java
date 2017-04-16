package com.itcr.ce.airwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.itcr.ce.airwar.*;
import com.itcr.ce.airwar.entities.PlayerShip;
import com.itcr.ce.airwar.levels.Level;
import com.itcr.ce.airwar.entities.Enemy;

/**
 * Created by Arturo on 5/4/2017.
 */
public class GameScreen implements Screen {
    final MyGdxGame game;
    private OrthographicCamera camera;
    private Level level;
    private Player player;
    private PlayerShip playerShip;
    private MyInputProcessor inputProcessor;
    private Ground ground;
    private boolean paused = false;
    private boolean levelComplete = false;
    private int score = 0;

    /**
     * Constructor
     * @param game Es el objeto al que se le asignan las pantallas
     * @param player Jugador
     */
    public GameScreen(MyGdxGame game, Player player) {
        this.game = game;
        this.player = player;
        this.playerShip = player.getShip();
        this.level = player.getLevel();
        this.inputProcessor = player.getInputProcessor();
        this.camera = new OrthographicCamera(MyGdxGame.appWidth, MyGdxGame.appHeight); //Se crea una nueva camara
        this.ground = new Ground(level.getTexture(), MyGdxGame.appWidth, MyGdxGame.appHeight, 90.0f); //Se crea el fondo
        camera.translate(0, 0); //Se coloca la camara en la posicion correcta

        level.spawnEnemies(); //Generacion de enemigos
    }

    @Override
    public void show() {

    }

    /**
     * Metodo encargado de renderizar todos los objetos
     * @param delta El tiempo entre el fotograma anterior y el actual
     */
    @Override
    public void render(float delta) {
        camera.update();
        game.batch.begin(); //Se empieza a correr la zona donde renderizar
        this.ground.render(game.batch); //Se renderiza el fondo
        game.font.draw(game.batch, "Lifes: " + this.player.getLifes(), 20, MyGdxGame.appHeight - 30);
        game.font.draw(game.batch, "Score: " + this.score, 20, MyGdxGame.appHeight - 60);
        game.font.draw(game.batch, "Remaining enemies: " + level.getEnemyQueue().getSize(), 20, MyGdxGame.appHeight - 90);

        if (paused == true) {
            game.font.draw(game.batch, "Game Paused", (MyGdxGame.appWidth / 2) - 50, (MyGdxGame.appHeight / 2) + 30);
            game.font.draw(game.batch, "Press ESC to Continue", (MyGdxGame.appWidth / 2) - 50, (MyGdxGame.appHeight / 2) - 30);
        } else {
            if (player.getLifes() < 1) { //Caso en el que el jugador ha perdido todas las vidas
                game.setScreen(new DeathScreen(game, player));
                this.dispose();
            } else {
                if (levelComplete == true) { //Caso en el que el jugador ha completado el nivel
                    player.setLevel(player.getLevel().getNextLevel());
                    //game.setScreen(new LevelCompleteScreen(game, gameState, actionResolver));
                    //dispose();
                } else {
                    //Renderizado de objetos en el mapa
                    this.updateBulletPlayer();
                    this.updateEnemies();
                    this.updatePlayerShip();
                    this.updateBulletEnemies();
                    this.updateBulletColisions();
                    this.updateExplosions();
                    level.enemiesShoot();
                }
            }
            inputProcessor.checkInput(Gdx.graphics.getDeltaTime()); //Se asigna el procesador de entradas
            game.batch.end();
        }
    }

    /**
     * Metodo que se encarga de verificar si hay una colision entre las balas del jugador y las balas del enemigo
     */
    private void updateBulletColisions(){
        for(int x = 0; x < level.getBulletEnemyCollection().getSize(); x++){
            BulletEnemy bulletEnemy = (BulletEnemy) level.getBulletEnemyCollection().getElement(x).getDataT();
            boolean collision = false;
            int posEnemyBullet = -1;
            int posPlayerBullet = -1;

            for(int i = 0; i < level.getBulletPlayerCollection().getSize(); i++){
                BulletPlayer bulletPlayer = (BulletPlayer) level.getBulletPlayerCollection().getElement(i).getDataT();

                if(bulletEnemy.checkOverlap(bulletPlayer.getSprite().getBoundingRectangle())){
                    collision = true;
                    posEnemyBullet = x;
                    posPlayerBullet = i;
                }
            }

            if(collision == true){ //Si la bala del enemigo choco contra alguna del jugador
                level.getBulletEnemyCollection().deleteElement(posEnemyBullet); //Se elimina de la lista
                level.getBulletPlayerCollection().deleteElement(posPlayerBullet); //Se elimina de la lista
            }
        }
    }

    /**
     * Metodo que se encarga de renderizar la nave del jugador
     */
    private void updatePlayerShip(){
        playerShip.getSubjectSprite().setPosition(playerShip.getPlaneLocation().x, playerShip.getPlaneLocation().y); //Se coloca la nave del jugador
        playerShip.getSubjectSprite().draw(game.batch); //Se dibuja la nave en el batch                              //en la posicion correspondiente

    }

    /**
     * Metodo que se encarga de renderizar las balas de los enemigos
     */
    private void updateBulletEnemies(){
        for (int x = 0; x < level.getBulletEnemyCollection().getSize(); x++) {
            BulletEnemy bullet = (BulletEnemy) level.getBulletEnemyCollection().getElement(x).getDataT();
            bullet.render(game.batch);
        }

        for (int x = 0; x < level.getBulletEnemyCollection().getSize(); x++) {
            BulletEnemy bullet = (BulletEnemy) level.getBulletEnemyCollection().getElement(x).getDataT();

            if(bullet.checkOverlap(playerShip.getSubjectSprite().getBoundingRectangle())){
                float width = playerShip.getSubjectSprite().getWidth();
                float height = playerShip.getSubjectSprite().getHeight();
                level.createExplosion(playerShip.getPlaneLocation().x, playerShip.getPlaneLocation().y, width, height);
                player.setLifes(player.getLifes() - 1);
                bullet.dispose();
                level.getBulletEnemyCollection().deleteElement(x);
            }
            if (bullet.getY() < -bullet.getSprite().getHeight()) {
                bullet.dispose();
                level.getBulletEnemyCollection().deleteElement(x);
            }
        }
    }

    /**
     * Metodo que se encarga de renderizar los enemigos
     */
    private void updateEnemies(){
        for (int x = 0; x < level.getEnemyCollection().getSize(); x++) {
            Enemy enemy = (Enemy) level.getEnemyCollection().getElement(x).getDataT();
            enemy.render(game.batch);
            if (enemy.checkOverlap(playerShip.getSubjectSprite().getBoundingRectangle())) {
                //Explosion del enemigo
                float widthE = enemy.getSprite().getWidth();
                float heightE = enemy.getSprite().getHeight();
                level.createExplosion(enemy.getSprite().getX(), enemy.getSprite().getY(), widthE, heightE);

                //Explosion del jugador
                float widthP = playerShip.getSubjectSprite().getWidth();
                float heightP = playerShip.getSubjectSprite().getHeight();
                level.createExplosion(playerShip.getPlaneLocation().x, playerShip.getPlaneLocation().y, widthP, heightP);

                player.setLifes(player.getLifes() - 1);
                enemy.setDispose();
                level.getEnemyCollection().deleteElement(x); //Se elimina el enemigo
            }
            if(enemy.getSprite().getY() < -100){
                enemy.dispose();
                level.getEnemyCollection().deleteElement(x);
            }
        }

    }

    /**
     * Metodo que se encarga de renderizar las balas del jugador
     */
    private void updateBulletPlayer(){
        for (int x = 0; x < level.getBulletPlayerCollection().getSize(); x++) {
            BulletPlayer bullet = (BulletPlayer) level.getBulletPlayerCollection().getElement(x).getDataT();
            bullet.render(game.batch);
        }

        for (int x = 0; x < level.getBulletPlayerCollection().getSize(); x++) {
            BulletPlayer bulletPlayer = (BulletPlayer) level.getBulletPlayerCollection().getElement(x).getDataT();
            if (bulletPlayer.getY() > MyGdxGame.appHeight - (bulletPlayer.getSprite()).getWidth()) { //Si la bala ha salido de la pantalla
                bulletPlayer.dispose();
                level.getBulletPlayerCollection().deleteElement(x);
            }
        }

        //Se verifica si alguna bala a impactado contra un enemigo
        for (int x = 0; x < level.getEnemyCollection().getSize(); x++) {
            Enemy enemy = (Enemy) level.getEnemyCollection().getElement(x).getDataT();
            boolean eliminate = false; //Determina si se ha impactado algun enemigo
            int posEnemy = -1;
            int posBullet = -1;

            for (int i = 0; i < level.getBulletPlayerCollection().getSize(); i++) {
                BulletPlayer bullet = (BulletPlayer) level.getBulletPlayerCollection().getElement(i).getDataT();

                if (enemy.checkOverlap(bullet.getSprite().getBoundingRectangle())) { //Si la bala actual esta en colision con el enemigo actual
                    eliminate = true; //Hay que eliminar la bala y el enemigo
                    posEnemy = x; //Se guarda la posicion del enemigo en especifico
                    posBullet = i; //Se guarda la posicion de la bala especifica

                    //Explosion del enemigo
                    float widthE = enemy.getSprite().getWidth();
                    float heightE = enemy.getSprite().getHeight();
                    level.createExplosion(enemy.getSprite().getX(), enemy.getSprite().getY(), widthE, heightE);
                }
            }
            //Se elimina el enemigo si hubo impacto
            if (eliminate == true) {
                this.score += enemy.getScore();
                level.getEnemyCollection().deleteElement(posEnemy); //Se elimina el enemigo
                level.getBulletPlayerCollection().deleteElement(posBullet); //Se elimina la bala
            }
        }
    }

    /**
     * Metodo que se encarga de actualizar las explosiones
     */
    private void updateExplosions(){
        for(int x = 0; x < level.getExplosionCollection().getSize(); x++){
            Explosion explosion = (Explosion) level.getExplosionCollection().getElement(x).getDataT();
            explosion.render(game.batch); //Se dibuja en el SpriteBatch
        }

        //Se verifica si la animacion ya ha terminado
        for (int i = 0; i < level.getExplosionCollection().getSize(); i++) {
            Explosion explosion = (Explosion) level.getExplosionCollection().getElement(i).getDataT();
            if(explosion.getElapsedTime() > 1.6/2f){
                explosion.dispose();
                level.getExplosionCollection().deleteElement(i); //Si ya termino, se elimina de la lista
            }
        }
    }

    public boolean isPaused(){
        return this.paused;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if(this.paused == true) {
            this.paused = false;
        }

        if(this.paused == false){
            this.paused = true;
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
