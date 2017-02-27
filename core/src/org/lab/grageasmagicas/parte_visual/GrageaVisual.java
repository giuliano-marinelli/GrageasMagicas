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
    private boolean brilla;

    private Texture txtGragea;
    private Texture txtSuperGragea;
    private Texture txtGrageaBrillo;
    private TextureRegion grafico;
    private TextureRegion graficoBrillo;

    public GrageaVisual(int tipo, Texture txtGragea, Texture txtSuperGragea, Texture txtGrageaBrillo) {
        this.tipo = tipo;
        this.txtGragea = txtGragea;
        this.txtSuperGragea = txtSuperGragea;
        this.txtGrageaBrillo = txtGrageaBrillo;
        graficoBrillo = new TextureRegion(txtGrageaBrillo);
        seleccionada = false;
        brilla = false;
        setSize(128, 96);
        setTouchable(Touchable.enabled);
        actualizarGrafico();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw(grafico, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if (brilla) {
            batch.setColor(Color.WHITE);
            batch.draw(graficoBrillo, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
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
        setPosition(getX()-5,getY()-5);
    }

    public void deseleccionar() {
        this.seleccionada = false;
        setSize(128, 96);
        setPosition(getX()+5,getY()+5);
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
        actualizarGrafico();
    }

    public void actualizarGrafico() {
        if (tipo == 100) {
            grafico = new TextureRegion(txtSuperGragea);
        } else {
            grafico = new TextureRegion(txtGragea);
        }
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
            case 5:
                setColor(Color.CYAN);
                break;
            case 6:
                setColor(Color.ORANGE);
                break;
            case 7:
                setColor(Color.PINK);
                break;
            case 100:
                setColor(Color.GOLD);
                break;
            default:
                setColor(Color.WHITE);
                break;
        }
    }

    public void brillar(boolean valor) {
        if (tipo != 100) {
            brilla = valor;
        }
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
}