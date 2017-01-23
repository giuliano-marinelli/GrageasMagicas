package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;


public class GrageaVisual extends Actor {

    private int tipo;
    private boolean seleccionada;

    private TextureRegion grafico;

    public GrageaVisual(int tipo, Texture texturaBtnGragea) {
        this.tipo = tipo;
        this.grafico = new TextureRegion(texturaBtnGragea);
        seleccionada = false;
        setSize(128, 96);
        setTouchable(Touchable.enabled);
        actualizarColor();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw(grafico, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void seleccionar() {
        this.seleccionada = true;
        setSize(138, 106);
    }

    public void deseleccionar() {
        this.seleccionada = false;
        setSize(128, 96);
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
        actualizarColor();
    }

    public void actualizarColor() {
        switch (tipo) {
            case 0:
                setColor(Color.RED);
                break;
            case 1:
                setColor(Color.GREEN);
                break;
            case 2:
                setColor(Color.YELLOW);
                break;
            case 3:
                setColor(Color.BLUE);
                break;
            case 4:
                setColor(Color.PURPLE);
                break;
            default:
                setColor(Color.LIGHT_GRAY);
                break;
        }
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
}