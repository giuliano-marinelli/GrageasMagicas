package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Patron implements Runnable {

    protected AtomicBoolean hayJugadaRec;
    protected AtomicBoolean hayJugadaDiag;
    protected List<Point> posicion;
    protected Gragea[][] matrizGragea;
    protected CyclicBarrier barrierFinPatron;
    protected Movimiento bMovimiento;

    public Patron(AtomicBoolean hayJugadaRec, AtomicBoolean hayJugadaDiag, Gragea[][] matrizGragea,
                  CyclicBarrier barrierFinPatrones, Movimiento bMovimiento) {
        this.hayJugadaRec = hayJugadaRec;
        this.hayJugadaDiag = hayJugadaDiag;
        this.barrierFinPatron = barrierFinPatrones;
        posicion = new LinkedList<Point>();
        this.matrizGragea = matrizGragea;
        this.bMovimiento = bMovimiento;
        //cada Patron implementado debe calcular que posiciones necesita verificar.
        //debe setear en la lista posicion el inicio de la submatriz donde se encuentran las posiciones
        //que va a verificar.

    }

    protected abstract boolean verificarPatron(int x, int y);

    public void setPosicion(Point pos) {
        posicion.add(pos);
    }

    public boolean hayJugadaRecta() {
        return hayJugadaRec.get();
    }

    @Override
    public void run() {
        try {
            int tam = posicion.size();
            int i = 0;
            int x;
            int y;
            while (i < tam && !hayJugadaRec.get() && !hayJugadaDiag.get()) {
                x = posicion.get(i).x;
                y = posicion.get(i).y;
                hayJugadaRec.set(verificarPatron(x, y) || hayJugadaRec.get() || hayJugadaDiag.get());
                i++;
            }
            i = 0;
            //espera a que todos los hilos terminen el while, luego se rompe y PatronControlador puede continuar.
            barrierFinPatron.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}