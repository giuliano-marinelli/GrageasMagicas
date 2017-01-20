package org.lab.grageasmagicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class TableroVisual extends ScreenAdapter {

    private Stage escenaTablero;
    private static final float ancho = Gdx.graphics.getWidth();
    private static final float alto = Gdx.graphics.getHeight();
    private GrageaVisual[][] matrizGrageasVisuales;
    private int cantSeleccionadas;
    private int filaDeLaPrimeraSeleccionada;
    private int columnaDeLaPrimeraSeleccionada;

    public TableroVisual(GrageaVisual[][] mgv) {
        matrizGrageasVisuales = mgv;
        filaDeLaPrimeraSeleccionada=-1;
        columnaDeLaPrimeraSeleccionada=-1;

        escenaTablero = new Stage(new FitViewport(ancho, alto));
        Gdx.input.setInputProcessor(escenaTablero);

        Table table = new Table();

        Texture fondogolosinasTexture = new Texture(Gdx.files.internal("fondogolosinas.png"));
        TextureRegionDrawable fondogolosinasTextureRegionDrawable = new TextureRegionDrawable(new TextureRegion(fondogolosinasTexture));
        table.background(fondogolosinasTextureRegionDrawable);


        table.row();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                table.add(matrizGrageasVisuales[i][j].getBoton());
            }
            table.row();
        }

        table.padBottom(5f);
        table.setFillParent(true);
        table.pack();

        escenaTablero.addActor(table);
    }

    public void resize(int width, int height) {
        escenaTablero.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        escenaTablero.act(delta);
        escenaTablero.draw();
    }

    public boolean aumentarCantSeleccionadas(int fila, int columna){

        if(cantSeleccionadas==0 ) {
            cantSeleccionadas++;
            filaDeLaPrimeraSeleccionada=fila;
            columnaDeLaPrimeraSeleccionada=columna;
            return true;
        }
        else if(cantSeleccionadas==1){
            if((fila==filaDeLaPrimeraSeleccionada &&
                    ((columna==columnaDeLaPrimeraSeleccionada-1) || (columna==columnaDeLaPrimeraSeleccionada+1)))
                    || (columna==columnaDeLaPrimeraSeleccionada &&
                    ((fila==filaDeLaPrimeraSeleccionada-1) || (fila==filaDeLaPrimeraSeleccionada+1)))) {
                cantSeleccionadas++;
                return true;
            }
            else
                return false;

        }
        else
            return false;

    }

    public void disminuirCantSeleccionadas(){
        cantSeleccionadas--;
    }

}
