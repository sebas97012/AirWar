package com.itcr.ce.airwar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.itcr.ce.airwar.*;
import com.itcr.ce.airwar.entities.PlayerPlane;
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
    private PlayerPlane playerPlane;
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
        this.playerPlane = player.getPlane();
        this.level = player.getLevel();
        this.inputProcessor = player.getInputProcessor();
        this.camera = new OrthographicCamera(MyGdxGame.appWidth, MyGdxGame.appHeight); //Se crea una nueva camara
        this.ground = new Ground(level.getTexture(), MyGdxGame.appWidth, MyGdxGame.appHeight, 80.0f); //Se crea el fondo
        camera.translate(0, 0); //Se coloca la camara en la posicion correcta

        level.scheduleEnemies(); //Generacion de enemigos
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
        //game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin(); //Se empieza a correr la zona donde renderizar
        this.ground.render(game.batch); //Se renderiza el fondo
        game.font.draw(game.batch, "Score: " + this.score, 20, MyGdxGame.appHeight - 20);

        game.font.draw(game.batch, "Enemigos restantes: " + level.getEnemyCollection().getSize(), 20, MyGdxGame.appHeight - 60);

        if (paused == true) {
            game.font.draw(game.batch, "Game Paused", 350, 300);
            game.font.draw(game.batch, "Press ESC to Continue", 350, 250);
        } else {
            if (player.getHealth() < 0) { //Caso en el que el jugador ha perdido todas las vidas
                game.setScreen(new DeathScreen(game));
                dispose();
            } else {
                if (levelComplete == true) { //Caso en el que el jugador ha completado el nivel
                    player.setLevel(player.getLevel().getNextLevel());
                    //game.setScreen(new LevelCompleteScreen(game, gameState, actionResolver));
                    //dispose();
                } else {
                    //RENDERIZADO DE TODOS LOS OBJETOS EN EL MAPA
                    //RENDERIZADO BALAS JUGADOR
                    for (int x = 0; x < level.getBulletPlayerCollection().getSize(); x++) {
                        BulletPlayer bullet = (BulletPlayer) level.getBulletPlayerCollection().getElement(x).getDataT();
                        bullet.render(game.batch);
                    }

                    for (int x = 0; x < level.getBulletPlayerCollection().getSize(); x++) {
                        BulletPlayer bulletPlayer = (BulletPlayer) level.getBulletPlayerCollection().getElement(x).getDataT();
                        if (bulletPlayer.getY() > MyGdxGame.appHeight - (bulletPlayer.getSprite()).getWidth()) {
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
                            }
                        }
                        //Se elimina el enemigo si hubo impacto
                        if (eliminate == true) {
                            this.score += enemy.getScore();

                            level.getEnemyCollection().deleteElement(posEnemy); //Se elimina el enemigo

                            //Se elimina la bala
                            level.getBulletPlayerCollection().deleteElement(posBullet); //Se elimina la bala
                        }
                    }

                    // TERMINA RENDERIZADO BALAS JUGADOR

                    //EMPIEZA RENDERIZADO DE ENEMIGOS
                    for (int x = 0; x < level.getEnemyCollection().getSize(); x++) {
                        Enemy enemy = (Enemy) level.getEnemyCollection().getElement(x).getDataT();
                        enemy.render(game.batch);
                        if (enemy.checkOverlap(playerPlane.getSubjectSprite().getBoundingRectangle())) {
                            this.score += enemy.getScore();
                            // sound
                            //pigeon.play();
                            enemy.setDispose();
                            level.getEnemyCollection().deleteElement(x); //Se elimina el enemigo
                            //Explosion exp = new Explosion("explosion19.png", (tmp.x + tmp.sprite.getWidth()/2), tmp.y + tmp.sprite.getHeight()/2);
                            //expCollection.add(exp);
                            //expCollectionSize++;
                        }
                    }
                }
                //TERMINA RENDERIZADO DE ENEMIGOS

                playerPlane.getSubjectSprite().setPosition(playerPlane.getPlaneLocation().x, playerPlane.getPlaneLocation().y); //Se coloca la nave del jugador
                playerPlane.getSubjectSprite().draw(game.batch); //Se dibuja la nave en el batch                                //en la posicion correspondiente

                //TERMINA RENDERIZADO DE TODOS LOS OBJETOS
            }
        }
        inputProcessor.checkInput(Gdx.graphics.getDeltaTime()); //Se asigna el procesador de entradas
        game.batch.end();
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
