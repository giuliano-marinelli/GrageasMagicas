package org.lab.grageasmagicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class TableroVisual extends ScreenAdapter {

    private Stage escenaTablero;
    private static final float ancho= Gdx.graphics.getWidth();
    private static final float alto= Gdx.graphics.getHeight();
    private GrageaVisual[][] matrizGrageasVisuales;


    public TableroVisual(GrageaVisual[][] mgv){

        matrizGrageasVisuales=mgv;

        escenaTablero = new Stage(new FitViewport(ancho, alto));
        Gdx.input.setInputProcessor(escenaTablero);

        Table table = new Table();
        table.row();

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                table.add(matrizGrageasVisuales[i][j].getBoton());
            }
            table.row();
        }

        table.padBottom(5f);
        table.setFillParent(true);
        table.pack();

        escenaTablero.addActor(table);

    }

    public void resize(int width, int height){
        escenaTablero.getViewport().update(width, height, true);
    }

    public void render(float delta){
        escenaTablero.act(delta);
        escenaTablero.draw();
    }



}
