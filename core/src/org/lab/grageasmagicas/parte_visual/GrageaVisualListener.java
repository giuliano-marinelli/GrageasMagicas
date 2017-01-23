package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.concurrent.BrokenBarrierException;

public class GrageaVisualListener extends InputListener {

    private GrageaVisual grageaVisual;
    private JuegoVisual juegoVisual;
    private int filaGragea;
    private int columnaGragea;

    public GrageaVisualListener(GrageaVisual grageaVisual, JuegoVisual juegoVisual, int filaGragea, int columnaGragea) {
        this.grageaVisual = grageaVisual;
        this.juegoVisual = juegoVisual;
        this.filaGragea = filaGragea;
        this.columnaGragea = columnaGragea;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        super.touchDown(event, x, y, pointer, button);
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        try {
            if (juegoVisual.isInputHabilitado()) {
                if (!grageaVisual.isSeleccionada()) {
                    if (!juegoVisual.isHayGrageaSeleccionada()) {
                        //Sound sGrageaSeleccionada = Gdx.audio.newSound(Gdx.files.internal("grageaSeleccionada.mp3"));
                        grageaVisual.seleccionar();
                        juegoVisual.setHayGrageaSeleccionada(true);
                        juegoVisual.setPrimerGrageaX(filaGragea);
                        juegoVisual.setPrimerGrageaY(columnaGragea);
                    } else {
                        juegoVisual.setInputHabilitado(false);
                        juegoVisual.setSegundaGrageaX(filaGragea);
                        juegoVisual.setSegundaGrageaY(columnaGragea);
                        if (juegoVisual.verificarAdyacentes()) {
                            //sGrageaIntercambiada no representa un intercambio exitoso, simplemente acompaña la animacion de intercambio.
                            //Sound sGrageaIntercambiada = Gdx.audio.newSound(Gdx.files.internal("grageaIntercambiada.mp3"));
                            juegoVisual.getBarrierRespuestaVisual().await();
                        } else {
                            //Sound sMovimientoInvalido = Gdx.audio.newSound(Gdx.files.internal("movimientoInvalido.mp3"));
                            Gdx.app.log("Check", "Movimiento invalido");
                            juegoVisual.setInputHabilitado(true);
                        }
                    }
                } else {
                    //Sound sGrageaDeseleccionada = Gdx.audio.newSound(Gdx.files.internal("grageaDeseleccionada.mp3"));
                    grageaVisual.deseleccionar();
                    juegoVisual.setHayGrageaSeleccionada(false);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
    }

    public int getFilaGragea() {
        return filaGragea;
    }

    public void setFilaGragea(int filaGragea) {
        this.filaGragea = filaGragea;
    }

    public int getColumnaGragea() {
        return columnaGragea;
    }

    public void setColumnaGragea(int columnaGragea) {
        this.columnaGragea = columnaGragea;
    }

    public void setFilaColumnaGragea(int filaGragea, int columnaGragea) {
        this.filaGragea = filaGragea;
        this.columnaGragea = columnaGragea;
    }
}
