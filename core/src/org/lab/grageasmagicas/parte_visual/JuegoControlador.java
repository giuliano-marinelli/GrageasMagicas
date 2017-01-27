package org.lab.grageasmagicas.parte_visual;

import org.lab.grageasmagicas.parte_logica.*;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class JuegoControlador implements Runnable {

    private Juego juegoLogico;
    private JuegoVisual juegoVisual;
    private CyclicBarrier barrierEntradaLogico = new CyclicBarrier(2);
    private CyclicBarrier barrierRespuestaVisual = new CyclicBarrier(2);
    private AtomicBoolean finJuego;

    public JuegoControlador(Juego juegoLogico, JuegoVisual juegoVisual, AtomicBoolean finJuego) {
        this.juegoLogico = juegoLogico;
        this.juegoVisual = juegoVisual;
        this.finJuego = finJuego;

        juegoLogico.setBarrierEntrada(barrierEntradaLogico);
        juegoVisual.setBarrierRespuestaVisual(barrierRespuestaVisual);


        Thread juegoLogicoThread = new Thread(juegoLogico);

        juegoLogico.addObserver(juegoVisual);

        //juegoLogico.sincronizar();

        juegoLogicoThread.start();
    }

    @Override
    public void run() {
        while (!finJuego.get()) {
            try {
                barrierEntradaLogico.await();
                if (!juegoLogico.isFinJuego()) {
                    if (juegoLogico.isHayJugadas()) {
                        juegoVisual.setInputGrageas(true);
                    }
                    juegoVisual.setInputMenus(true);
                }
                barrierRespuestaVisual.await();
                if (!finJuego.get()) {
                    if (juegoLogico.isHayJugadas()) {
                        juegoVisual.setInputGrageas(false);
                        juegoVisual.setInputMenus(false);
                        juegoLogico.setIntercambioGrageas(juegoVisual.getPrimerGrageaX(), juegoVisual.getPrimerGrageaY(),
                                juegoVisual.getSegundaGrageaX(), juegoVisual.getSegundaGrageaY());
                        juegoVisual.getMatrizGrageasVisuales()[juegoVisual.getPrimerGrageaX()][juegoVisual.getPrimerGrageaY()].deseleccionar();
                        juegoVisual.limpiarPosGrageas();
                        juegoVisual.setHayGrageaSeleccionada(false);
                    }
                }
                barrierEntradaLogico.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
