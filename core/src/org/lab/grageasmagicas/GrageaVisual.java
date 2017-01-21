package org.lab.grageasmagicas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class GrageaVisual {

    private int tipo;
    private ImageButton btnGragea;
    private int fila;
    private int columna;
    private boolean seleccionada;
    private Texture texturaBtnGrageaSeleccionada;
    private Texture texturaBtnGragea;
    private Button.ButtonStyle grageaStyle;

    public GrageaVisual(int tipo, int fila, int columna, Texture texturaBtnGragea, Texture texturaBtnGrageaSeleccionada) {
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.texturaBtnGrageaSeleccionada = texturaBtnGrageaSeleccionada;
        this.texturaBtnGragea = texturaBtnGragea;
        seleccionada = false;
        TextureRegionDrawable trBtnGragea = new TextureRegionDrawable(new TextureRegion(texturaBtnGragea));


        Button.ButtonStyle grageaStyle = new Button.ButtonStyle();
        grageaStyle.up = new TextureRegionDrawable(new TextureRegion(texturaBtnGragea));
        grageaStyle.down = new TextureRegionDrawable(new TextureRegion(texturaBtnGrageaSeleccionada));
        grageaStyle.checked = new TextureRegionDrawable(new TextureRegion(texturaBtnGrageaSeleccionada));
        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle(grageaStyle);

        btnGragea = new ImageButton(btnStyle);
    }

    public ImageButton getBtnGragea() {
        return btnGragea;
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
        btnGragea.setChecked(true);
        btnGragea.setColor(Color.BLUE);
    }

    public void deseleccionar() {
        this.seleccionada = false;
        btnGragea.setChecked(false);
        btnGragea.setColor(Color.RED);
    }

    public void setFila(int nuevaFila) {
        fila = nuevaFila;
    }

    public void setColumna(int nuevaColumna) {
        columna = nuevaColumna;
    }

}