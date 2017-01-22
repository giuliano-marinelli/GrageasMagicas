package org.lab.grageasmagicas;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class JuegoControlador implements Runnable {

    private org.lab.grageasmagicas.parte_logica.Juego juegoLogico;
    private org.lab.grageasmagicas.parte_visual.JuegoVisual juegoVisual;
    private CyclicBarrier barrierEntradaLogico = new CyclicBarrier(2);
    private CyclicBarrier barrierRespuestaVisual = new CyclicBarrier(2);

    public JuegoControlador(org.lab.grageasmagicas.parte_logica.Juego juegoLogico, org.lab.grageasmagicas.parte_visual.JuegoVisual juegoVisual) {
        this.juegoLogico = juegoLogico;
        this.juegoVisual = juegoVisual;

        juegoLogico.setBarrierEntrada(barrierEntradaLogico);
        juegoVisual.setBarrierRespuestaVisual(barrierRespuestaVisual);


        Thread juegoLogicoThread = new Thread(juegoLogico);

        juegoLogico.addObserver(juegoVisual);

        //juegoLogico.sincronizar();

        juegoLogicoThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                barrierEntradaLogico.await();
                juegoVisual.setInputHabilitado(true);
                barrierRespuestaVisual.await();
                juegoVisual.setInputHabilitado(false);
                juegoLogico.setIntercambioGrageas(juegoVisual.getPrimerGrageaX(), juegoVisual.getPrimerGrageaY(),
                        juegoVisual.getSegundaGrageaX(), juegoVisual.getSegundaGrageaY());
                barrierEntradaLogico.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
