package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Efecto extends Actor {

    private ParticleEffect efecto;
    private Vector2 acc = new Vector2();

    public Efecto(ParticleEffect efecto) {
        super();
        this.efecto = efecto;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        efecto.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        acc.set(getWidth()/2, getHeight()/2);
        localToStageCoordinates(acc);
        efecto.setPosition(acc.x, acc.y);
        efecto.update(delta);
    }

    public void start() {
        efecto.start();
    }

    public void allowCompletion() {
        efecto.allowCompletion();
    }

    public boolean isComplete() {
        return efecto.isComplete();
    }

}
