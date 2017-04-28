package com.itcr.ce.airwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.itcr.ce.airwar.*;
import com.itcr.ce.airwar.entities.PlayerShip;
import com.itcr.ce.airwar.levels.LevelManager;
import com.itcr.ce.airwar.entities.Enemy;
import com.itcr.ce.airwar.powerups.PowerUp;
import com.itcr.ce.airwar.server.Bridge;

/**
 * Created by Arturo on 5/4/2017.
 */
public class GameScreen implements Screen {
    final MyGdxGame game;
    private OrthographicCamera camera;
    private LevelManager levelManager;
    private Player player;
    private PlayerShip playerShip;
    private MyInputProcessor inputProcessor;
    private Ground ground;
    private boolean paused = false;
    private Music music;
    private ItemBar itemBar;

    /**
     * Constructor
     * @param game Es el objeto al que se le asignan las pantallas
     * @param player Jugador
     */

    public GameScreen(MyGdxGame game, Player player) {
        this.game = game;
        this.player = player;
        this.playerShip = player.getShip();
        this.levelManager = new LevelManager(this.player);
        this.inputProcessor = player.getInputProcessor();
        this.inputProcessor.setLevelManager(this.levelManager);
        this.camera = new OrthographicCamera(MyGdxGame.appWidth, MyGdxGame.appHeight); //Se crea una nueva camara
        this.ground = new Ground(player.getLevel().getBackgroundTexturePath(), MyGdxGame.appWidth, MyGdxGame.appHeight, 90.0f); //Se crea el fondo
        camera.translate(0, 0); //Se coloca la camara en la posicion correcta
        this.music = player.getLevel().getMusic();
        this.music.setLooping(true);
        this.music.play();
        this.itemBar = new ItemBar();
        levelManager.spawnEnemies(); //Generacion de enemigos
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
        game.font.draw(game.batch, "Level: " + this.player.getLevel().getNumLevel(), 20, MyGdxGame.appHeight - 30);
        game.font.draw(game.batch, "Lifes: " + this.player.getLifes(), 20, MyGdxGame.appHeight - 60);
        game.font.draw(game.batch, "Score: " + player.getScore(), 20, MyGdxGame.appHeight - 90);
        game.font.draw(game.batch, "Remaining enemies: " + levelManager.getEnemyQueue().getSize(), 20, MyGdxGame.appHeight - 120);
        game.font.draw(game.batch, "Shield life: " + player.getShieldLife(), 20, MyGdxGame.appHeight - 150);
        game.font.draw(game.batch, "Special munition: " + player.getMunition(), 20, MyGdxGame.appHeight - 180);
        this.itemBar.draw(game.batch); //Se dibuja la barra de items

        if(player.isInvincibility() == true){ //Si la invencibilidad esta activada
            game.font.draw(game.batch, "Invincibility ON", MyGdxGame.appWidth - 110, MyGdxGame.appHeight - 30);
        }

        if (paused == true) {
            game.font.draw(game.batch, "Game Paused", (MyGdxGame.appWidth / 2) - 50, (MyGdxGame.appHeight / 2) + 30);
            game.font.draw(game.batch, "Press ESC to Continue", (MyGdxGame.appWidth / 2) - 50, (MyGdxGame.appHeight / 2) - 30);
        } else {
            if (player.getLifes() < 1) { //Caso en el que el jugador ha perdido todas las vidas
                game.setScreen(new DeathScreen(game, player));
                this.dispose();
            } else {
                if (this.levelManager.getEnemyQueue().getSize() == 0 && this.levelManager.getEnemyCollection().getSize() == 0) { //Caso en el que el jugador ha completado el nivel
                    game.setScreen(new LevelCompleteScreen(game, player));
                    this.dispose();
                } else {
                    //Renderizado de objetos en el mapa
                    this.updateBulletPlayer();
                    this.updateEnemies();
                    this.updatePlayerShip();
                    this.updateBulletEnemies();
                    this.updateBulletColisions();
                    this.updateExplosions();
                    this.updatePowerUps();
                    this.itemBar.update(player.getPowerUpStack(), game.batch);
                    levelManager.enemiesShoot();
                }
            }
            inputProcessor.checkInput(); //Se asigna el procesador de entradas
            game.batch.end();
        }
    }

    /**
     * Metodo que se encarga de verificar si hay una colision entre las balas del jugador y las balas del enemigo
     */

    private void updateBulletColisions(){
        for(int x = 0; x < levelManager.getBulletEnemyCollection().getSize(); x++){
            BulletEnemy bulletEnemy = (BulletEnemy) levelManager.getBulletEnemyCollection().getElement(x).getDataT();
            boolean collision = false;
            int posEnemyBullet = -1;
            int posPlayerBullet = -1;

            for(int i = 0; i < levelManager.getBulletPlayerCollection().getSize(); i++){
                BulletPlayer bulletPlayer = (BulletPlayer) levelManager.getBulletPlayerCollection().getElement(i).getDataT();

                if(bulletEnemy.checkOverlap(bulletPlayer.getSprite().getBoundingRectangle())){
                    collision = true;
                    posEnemyBullet = x;
                    posPlayerBullet = i;
                }
            }

            if(collision == true){ //Si la bala del enemigo choco contra alguna del jugador
                levelManager.getBulletEnemyCollection().deleteElement(posEnemyBullet); //Se elimina de la lista
                levelManager.getBulletPlayerCollection().deleteElement(posPlayerBullet); //Se elimina de la lista
            }
        }
    }

    /**
     * Metodo que se encarga de renderizar la nave del jugador
     */

    private void updatePlayerShip(){
        playerShip.getSubjectSprite().setPosition(playerShip.getPlaneLocation().x, playerShip.getPlaneLocation().y); //Se coloca la nave del jugador
        playerShip.getSubjectSprite().draw(game.batch); //Se dibuja la nave en el batch                              //en la posicion correspondiente
        if (player.getShieldLife() > 0) {   //Si el jugador tiene escudo se dibuja
            playerShip.getShieldSprite().setPosition(playerShip.getPlaneLocation().x, playerShip.getPlaneLocation().y);
            playerShip.getShieldSprite().draw(game.batch);
        }
    }

    /**
     * Metodo que se encarga de renderizar las balas de los enemigos
     */

    private void updateBulletEnemies(){
        for (int x = 0; x < levelManager.getBulletEnemyCollection().getSize(); x++) { //Se renderizan todas las balas de la lista
            BulletEnemy bullet = (BulletEnemy) levelManager.getBulletEnemyCollection().getElement(x).getDataT();
            bullet.render(game.batch); //Se dibuja la bala
        }

        for (int x = 0; x < levelManager.getBulletEnemyCollection().getSize(); x++) {
            BulletEnemy bullet = (BulletEnemy) levelManager.getBulletEnemyCollection().getElement(x).getDataT();

            if(bullet.checkOverlap(playerShip.getSubjectSprite().getBoundingRectangle())){ //Se verifica si hay colision
                float width = playerShip.getSubjectSprite().getWidth();
                float height = playerShip.getSubjectSprite().getHeight();
                levelManager.createExplosion(playerShip.getPlaneLocation().x, playerShip.getPlaneLocation().y, width, height); //Se crea una explosion
                player.updateLifes(); //Se actualizan las vidas del jugador
                Bridge.UpdateServer();
                bullet.dispose(); //Se deja de dibujar la bala
                levelManager.getBulletEnemyCollection().deleteElement(x); //Se elimina la bala de la lista
            }
            if (bullet.getY() < -bullet.getSprite().getHeight()) { //Si la bala a salido de la pantalla
                bullet.dispose(); //Se deja de dibujar la bala
                levelManager.getBulletEnemyCollection().deleteElement(x); //Se elimina de la lista
            }
        }
    }

    /**
     * Metodo que se encarga de renderizar los enemigos
     */

    private void updateEnemies(){
        for (int x = 0; x < levelManager.getEnemyCollection().getSize(); x++) {
            Enemy enemy = (Enemy) levelManager.getEnemyCollection().getElement(x).getDataT();
            enemy.render(game.batch);
            if (enemy.checkOverlap(playerShip.getSubjectSprite().getBoundingRectangle())) {
                //Explosion del enemigo
                float widthE = enemy.getSprite().getWidth();
                float heightE = enemy.getSprite().getHeight();
                levelManager.createExplosion(enemy.getSprite().getX(), enemy.getSprite().getY(), widthE, heightE);

                //Explosion del jugador
                float widthP = playerShip.getSubjectSprite().getWidth();
                float heightP = playerShip.getSubjectSprite().getHeight();
                levelManager.createExplosion(playerShip.getPlaneLocation().x, playerShip.getPlaneLocation().y, widthP, heightP);

                player.updateLifes(); //Se actualizan las vidas del jugador
                Bridge.UpdateServer();
                enemy.setDispose(); //Se deja de dibujar al enemigo
                levelManager.getEnemyCollection().deleteElement(x); //Se elimina el enemigo
            }
            if(enemy.getSprite().getY() < -100){ //Si el enemigo a salido del borde inferior de la pantalla
                enemy.dispose(); //Se deja de dibujar el enemigo
                levelManager.getEnemyCollection().deleteElement(x); //Se elimina de la lista
            }
        }

    }

    /**
     * Metodo que se encarga de renderizar las balas del jugador
     */

    private void updateBulletPlayer(){
        //Se renderizan las balas
        for (int x = 0; x < levelManager.getBulletPlayerCollection().getSize(); x++) {
            BulletPlayer bullet = (BulletPlayer) levelManager.getBulletPlayerCollection().getElement(x).getDataT();
            bullet.render(game.batch);
        }

        //Se verifica si han salido del borde de la pantalla
        for (int x = 0; x < levelManager.getBulletPlayerCollection().getSize(); x++) {
            BulletPlayer bulletPlayer = (BulletPlayer) levelManager.getBulletPlayerCollection().getElement(x).getDataT();
            if (bulletPlayer.getY() > MyGdxGame.appHeight - (bulletPlayer.getSprite()).getWidth()) { //Si la bala ha salido de la pantalla
                bulletPlayer.dispose();
                levelManager.getBulletPlayerCollection().deleteElement(x);
            }
        }

        //Se verifica si alguna bala a impactado contra un enemigo
        for (int x = 0; x < levelManager.getEnemyCollection().getSize(); x++) {
            Enemy enemy = (Enemy) levelManager.getEnemyCollection().getElement(x).getDataT();
            boolean impact = false; //Determina si se ha impactado algun enemigo
            int posEnemy = -1;
            int posBullet = -1;

            for (int i = 0; i < levelManager.getBulletPlayerCollection().getSize(); i++) {
                BulletPlayer bullet = (BulletPlayer) levelManager.getBulletPlayerCollection().getElement(i).getDataT();

                if (enemy.checkOverlap(bullet.getSprite().getBoundingRectangle())) { //Si la bala actual esta en colision con el enemigo actual
                    impact = true; //Hay que eliminar la bala y el enemigo
                    posEnemy = x; //Se guarda la posicion del enemigo en especifico
                    posBullet = i; //Se guarda la posicion de la bala especifica
                }
            }
            //Se reduce la vida del enemigo si hubo impacto
            if (impact == true) {
                BulletPlayer bulletPlayer = (BulletPlayer) levelManager.getBulletPlayerCollection().getElement(posBullet).getDataT();
                enemy.updateLife(bulletPlayer.getDamage()); //Se reduce la vida del enemigo
                levelManager.getBulletPlayerCollection().deleteElement(posBullet); //Se elimina la bala

                if(enemy.getLife() >= 1){ //Si el enemigo sigue vivo se reproduce el sonido de hit
                    enemy.getHitSound().play(5.00f);
                }

                if (enemy.getLife() < 1) {
                    player.updateScore(enemy.getScore()); //Se aumenta el score
                    Bridge.UpdateServer();
                    levelManager.getEnemyCollection().deleteElement(posEnemy); //Se elimina el enemigo

                    //Explosion del enemigo
                    float widthE = enemy.getSprite().getWidth(); //Se obtiene la anchura del enemigp
                    float heightE = enemy.getSprite().getHeight(); //Se obtiene la altura del enemigo
                    levelManager.createExplosion(enemy.getSprite().getX(), enemy.getSprite().getY(), widthE, heightE); //Se crea la explosion


                    //Se inserta powerup si existe en la lista de powerups
                    if (enemy.getPowerUp() != null){
                        PowerUp powerUp = enemy.getPowerUp();
                        float XPos = enemy.getSprite().getX(); //Se obtiene la posicion en X
                        float YPos = enemy.getSprite().getY(); //Se obtiene la posicion en y
                        powerUp.initialPath(XPos,YPos); //Se establece ruta que va a seguir el powerup
                        levelManager.getPowerUpCollection().insertAtEnd(powerUp); //Se inserta powerup en la lista
                    }
                }
            }
        }
    }

    /**
     * Metodo que se encarga de actualizar las explosiones
     */

    private void updateExplosions(){
        for(int x = 0; x < levelManager.getExplosionCollection().getSize(); x++){
            Explosion explosion = (Explosion) levelManager.getExplosionCollection().getElement(x).getDataT();
            explosion.render(game.batch); //Se dibuja en el SpriteBatch
        }

        //Se verifica si la animacion ya ha terminado
        for (int i = 0; i < levelManager.getExplosionCollection().getSize(); i++) {
            Explosion explosion = (Explosion) levelManager.getExplosionCollection().getElement(i).getDataT();
            if(explosion.getElapsedTime() > 1.6/2f){
                explosion.dispose();
                levelManager.getExplosionCollection().deleteElement(i); //Si ya termino, se elimina de la lista
            }
        }
    }
    /**
     * Metodo que se encarga de actualizar los powerups
     */

    private void updatePowerUps(){
        for(int x = 0; x < levelManager.getPowerUpCollection().getSize(); x++){
            PowerUp powerUp = (PowerUp) levelManager.getPowerUpCollection().getElement(x).getDataT();
            powerUp.render(game.batch); //Se dibuja en el SpriteBatch
            if (powerUp.checkOverlap(playerShip.getSubjectSprite().getBoundingRectangle())) {
                if (player.getPowerUpStack().getSize() < 12) { //El maximo de power up es 12
                    player.getPowerUpStack().insert(powerUp);
                    powerUp.getSoundCollected().play();
                    levelManager.getPowerUpCollection().deleteElement(x);
                }
            }
            if (powerUp.getSprite().getY() < -powerUp.getSprite().getHeight()){
                levelManager.getPowerUpCollection().deleteElement(x);
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
        this.music.dispose();
    }
}
