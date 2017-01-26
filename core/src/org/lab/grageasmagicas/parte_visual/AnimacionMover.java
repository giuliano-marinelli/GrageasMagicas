package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Align;

public class AnimacionMover extends TemporalAction {
    private float startX, startY;
    private float endX, endY;
    private int alignment = Align.bottomLeft;
    private JuegoVisual juegoVisual;

    public AnimacionMover(float x, float y, float duration, Interpolation interpolation, JuegoVisual juegoVisual) {
        setPosition(x, y);
        setDuration(duration);
        setInterpolation(interpolation);
        this.juegoVisual = juegoVisual;
    }

    protected void begin() {
        startX = target.getX(alignment);
        startY = target.getY(alignment);
    }

    protected void update(float percent) {
        target.setPosition(startX + (endX - startX) * percent, startY + (endY - startY) * percent, alignment);
        if (getTime() >= getDuration()) {
            juegoVisual.animacionTermina();
        }
    }

    public void reset() {
        super.reset();
        alignment = Align.bottomLeft;
    }

    public void setPosition(float x, float y) {
        endX = x;
        endY = y;
    }

    public void setPosition(float x, float y, int alignment) {
        endX = x;
        endY = y;
        this.alignment = alignment;
    }

    public float getX() {
        return endX;
    }

    public void setX(float x) {
        endX = x;
    }

    public float getY() {
        return endY;
    }

    public void setY(float y) {
        endY = y;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }
}
