package org.lab.grageasmagicas.parte_logica;

import org.lab.estructuras.Point;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Bermudez Martin, Kurchan Ines, Marinelli Giuliano
 */
public class Comprobador implements Runnable {

    protected final Gragea[][] matrizGrageas;
    protected final int seccion;
    protected final CopyOnWriteArrayList<Point> grageasCombinadas;
    protected final CyclicBarrier barrierComp;

    public Comprobador(Gragea[][] matrizGrageas, int seccion, CopyOnWriteArrayList grageasCombinadas, CyclicBarrier barrierComp) {
        this.matrizGrageas = matrizGrageas;
        this.seccion = seccion;
        this.grageasCombinadas = grageasCombinadas;
        this.barrierComp = barrierComp;
    }

    @Override
    public void run() {
    }

    synchronized public void dormir() throws InterruptedException {
        wait();
    }

    synchronized public void despertar() {
        notify();
    }

}

