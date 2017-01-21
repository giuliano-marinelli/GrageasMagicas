package org.lab.grageasmagicas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;


public class GrageaVisual extends Actor {

    private int tipo;
    private int fila;
    private int columna;
    private boolean seleccionada;

    private TextureRegion grafico;

    public GrageaVisual(int tipo, int fila, int columna, Texture texturaBtnGragea) {
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.grafico = new TextureRegion(texturaBtnGragea);
        seleccionada = false;
        setSize(128, 96);
        setTouchable(Touchable.enabled);
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
        setColor(getColor().r, getColor().g, getColor().b, 0.8f);
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

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void seleccionar() {
        this.seleccionada = true;
        setPosition(getX() - 10, getY() - 10);
        setColor(getColor().r, getColor().g, getColor().b, 1);
        setSize(138, 106);
    }

    public void deseleccionar() {
        this.seleccionada = false;
        setPosition(getX() + 10, getY() + 10);
        setColor(getColor().r, getColor().g, getColor().b, 0.8f);
        setSize(128, 96);
    }

    public void setFila(int nuevaFila) {
        fila = nuevaFila;
    }

    public void setColumna(int nuevaColumna) {
        columna = nuevaColumna;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
}