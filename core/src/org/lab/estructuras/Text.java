package org.lab.estructuras;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {

    private BitmapFont fuente;
    private String texto;

    public Text(BitmapFont fuente) {
        this.fuente = fuente;
        fuente.setColor(Color.BLACK);
    }

    public Text(BitmapFont fuente, String texto) {
        this.fuente = fuente;
        this.texto = texto;
        fuente.setColor(Color.BLACK);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        fuente.draw(batch, texto, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public BitmapFont getFuente() {
        return fuente;
    }

    public void setFuente(BitmapFont fuente) {
        this.fuente = fuente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setColor(Color color) {
        fuente.setColor(color);
    }

    public Color getColor() {
        return fuente.getColor();
    }

    public void setEscala(float escalaX, float escalaY) {
        fuente.getData().setScale(escalaX, escalaY);
    }

    public float getEscalaX() {
        return fuente.getData().scaleX;
    }

    public float getEscalaY() {
        return fuente.getData().scaleY;
    }
}
