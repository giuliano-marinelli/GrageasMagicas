package org.lab.grageasmagicas;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GrageaVisualListener extends InputListener {

    public GrageaVisual grageavisual;
    public Gragea grageaLogica;
    public TableroVisual tablero;

    public GrageaVisualListener(GrageaVisual gv, Gragea gl, TableroVisual tv) {
        grageavisual = gv;
        grageaLogica = gl;
        tablero = tv;
    }

    //Called when a mouse button or a finger touch goes down on the actor.

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        super.touchDown(event, x, y, pointer, button);
        return true;
    }

    // Este es el metodo que llama cuando se aprieta el boton con el dedo(equivalente a un click)
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        if(grageavisual.getCantClicks()==0) {

            if (tablero.aumentarCantSeleccionadas(grageavisual.getFila(), grageavisual.getColumna())) {
                grageavisual.getBoton().setChecked(true);
                grageavisual.aumentarCantClicks();
                tablero.intercambiarGrageas();
                verificarSiSeIntercambio();
            }
            else
                grageavisual.getBoton().setChecked(false);
        }
        else if(grageavisual.getCantClicks()==1){
            grageavisual.setCantClicksToZero();
            //creo que la siguiente linea no es necesaria
            grageavisual.getBoton().setChecked(false);
            //////////////
            tablero.disminuirCantSeleccionadas();
        }
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
    }

    public void verificarSiSeIntercambio(){
        if(grageavisual.getSeIntercambio()) {
            grageavisual.setCantClicksToZero();
            grageavisual.getBoton().setChecked(false);
            grageavisual.setSeIntercambio(false);
        }
    }
}
