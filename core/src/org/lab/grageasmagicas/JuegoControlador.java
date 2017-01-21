package org.lab.grageasmagicas;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class JuegoControlador implements Runnable {

    private Juego juegoLogico;
    private JuegoVisual juegoVisual;
    private CyclicBarrier barrierEntradaLogico = new CyclicBarrier(2);
    private CyclicBarrier barrierRespuestaVisual = new CyclicBarrier(2);
    private CyclicBarrier barrierSincVisual = new CyclicBarrier(2);

    public JuegoControlador(Juego juegoLogico, JuegoVisual juegoVisual) {
        this.juegoLogico = juegoLogico;
        this.juegoVisual = juegoVisual;

        juegoLogico.setBarrierEntradaLogico(barrierEntradaLogico);
        juegoLogico.setBarrierSincVisual(barrierSincVisual);
        juegoVisual.setBarrierRespuestaVisual(barrierRespuestaVisual);
        juegoVisual.setBarrierSincVisual(barrierSincVisual);


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
                barrierRespuestaVisual.await();
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
