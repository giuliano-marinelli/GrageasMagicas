package org.lab.grageasmagicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GrageaVisualListener extends InputListener {

    public GrageaVisual grageaVisual;
    public Gragea grageaLogica;
    public JuegoVisual juegoVisual;

    public GrageaVisualListener(GrageaVisual grageaVisual, Gragea grageaLogica, JuegoVisual juegoVisual) {
        this.grageaVisual = grageaVisual;
        this.grageaLogica = grageaLogica;
        this.juegoVisual = juegoVisual;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        super.touchDown(event, x, y, pointer, button);
        return true;
    }

    // Este es el metodo que llama cuando se aprieta el boton con el dedo(equivalente a un click)
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        if(!grageaVisual.isSeleccionada()) {
            if (!juegoVisual.isHayGrageaSeleccionada()) {
                grageaVisual.seleccionar();
                juegoVisual.setHayGrageaSeleccionada(true);
                juegoVisual.setPrimerGrageaX(grageaVisual.getFila());
                juegoVisual.setPrimerGrageaY(grageaVisual.getColumna());
            } else {
                juegoVisual.setSegundaGrageaX(grageaVisual.getFila());
                juegoVisual.setSegundaGrageaY(grageaVisual.getColumna());
                if (juegoVisual.verificarAdyacentes()) {
                    juegoVisual.intercambiarGrageas();
                } else {
                    Gdx.app.log("Check", "Movimiento invalido");
                }

            }
        } else {
            grageaVisual.deseleccionar();
            juegoVisual.setHayGrageaSeleccionada(false);
        }
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
    }

}
