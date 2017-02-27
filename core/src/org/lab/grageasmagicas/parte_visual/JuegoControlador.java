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
                if (!finJuego.get()) {
                    if (juegoLogico.isHayJugadas()) {
                        juegoVisual.setInputGrageas(true);
                    }
                    juegoVisual.setInputMenus(true);
                }
                // System.out.println("1 juegoControlador");
                barrierRespuestaVisual.await();
                // System.out.println("2 juegoControlador");
                if (!finJuego.get()) {
                    if (juegoLogico.isHayJugadas()) {
                        juegoVisual.setInputGrageas(false);
                        juegoVisual.setInputMenus(false);
                        if (juegoVisual.isSuperGrageaActivada()) {
                            juegoLogico.setSuperGrageaActivada(true);
                            juegoLogico.setPrimerGrageaX(juegoVisual.getPrimerGrageaX());
                            juegoLogico.setPrimerGrageaY(juegoVisual.getPrimerGrageaY());
                            juegoVisual.setSuperGrageaActivada(false);
                            juegoVisual.limpiarPosGrageas();
                        } else {
                            juegoLogico.setPoderMovDiagonalActivado(juegoVisual.isPoderMovDiagonalActivado());
                            juegoLogico.setIntercambioGrageas(juegoVisual.getPrimerGrageaX(), juegoVisual.getPrimerGrageaY(),
                                    juegoVisual.getSegundaGrageaX(), juegoVisual.getSegundaGrageaY());
                            juegoVisual.getMatrizGrageasVisuales()[juegoVisual.getPrimerGrageaX()][juegoVisual.getPrimerGrageaY()].deseleccionar();
                            juegoVisual.limpiarPosGrageas();
                        }
                        juegoVisual.setHayGrageaSeleccionada(false);
                    }
                }
                // System.out.println("3 juegoControlador");
                barrierEntradaLogico.await();
                // System.out.println("4 juegoControlador");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("SALIO JUEGO CONTROLADOR");
    }

}
