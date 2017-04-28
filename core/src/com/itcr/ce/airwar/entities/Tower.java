package com.itcr.ce.airwar.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.data.LinkedList;
import com.itcr.ce.data.Random;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Arturo on 7/4/2017.
 */
public class Tower extends Enemy{
    private Animation animation;
    private float elapsedTime = 0.0f;

    /**
     * Constructor
     */
    public Tower(int life, int tipo){
        super("tower/swarmer0000.png", 0.15f, life, tipo);
        this.score = 10;

        LinkedList<Texture> textures = new LinkedList<Texture>();
        textures.insertAtEnd(new Texture(Gdx.files.internal("tower/swarmer0000.png")));
        textures.insertAtEnd(new Texture(Gdx.files.internal("tower/swarmer0001.png")));
        textures.insertAtEnd(new Texture(Gdx.files.internal("tower/swarmer0002.png")));
        textures.insertAtEnd(new Texture(Gdx.files.internal("tower/swarmer0003.png")));
        textures.insertAtEnd(new Texture(Gdx.files.internal("tower/swarmer0004.png")));
        textures.insertAtEnd(new Texture(Gdx.files.internal("tower/swarmer0005.png")));
        textures.insertAtEnd(new Texture(Gdx.files.internal("tower/swarmer0006.png")));
        textures.insertAtEnd(new Texture(Gdx.files.internal("tower/swarmer0007.png")));

        TextureRegion[] frames = new TextureRegion[8];

        for(int i = 0; i < 8; i++){
            Texture tex = (Texture) textures.getElement(i).getDataT();
            frames[i] = new TextureRegion(tex, (int)this.sprite.getWidth(), (int)this.sprite.getHeight());
        }

        this.animation = new Animation<TextureRegion>(1/4f, frames);
    }

    @Override
    /**
     * Metodo que se encarga de renderizar el enemigo
     * @param batch batch
     */
    public void render(SpriteBatch batch) {
        EnemyVector.y -= 2;

        sprite.setPosition(EnemyVector.x, EnemyVector.y); //Se coloca en la posicion
        sprite.draw(batch); //Se dibuja
    }
}
