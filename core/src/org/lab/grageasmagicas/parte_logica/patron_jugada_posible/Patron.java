package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Bermu on 22/01/2017.
 */

public abstract class Patron implements Runnable {

    protected AtomicBoolean hayJugada;
    protected List<Point> posicion;
    protected Gragea[][] matrizGragea;
    protected CyclicBarrier barrierFinPatron;

    public Patron(AtomicBoolean hayJugada, Gragea[][] matrizGragea, CyclicBarrier barrierFinPatrones) {
        this.hayJugada = hayJugada;
        this.barrierFinPatron = barrierFinPatrones;
        posicion = new LinkedList<Point>();
        this.matrizGragea = matrizGragea;
        //cada Patron implementado debe calcular que posiciones necesita verificar.
        //debe setear en la lista posicion el inicio de la submatriz donde se encuentran las posiciones
        //que va a verificar.

    }

    protected abstract boolean verificarPatron(int x, int y);

    public void setPosicion(Point pos) {
        posicion.add(pos);
    }

    public boolean hayJugada() {
        return hayJugada.get();
    }

    @Override
    public void run() {
        try {
            int tam = posicion.size();
            int i = 0;
            int x;
            int y;
            while (i < tam && !hayJugada.get()) {
                x = posicion.get(i).x;
                y = posicion.get(i).y;
                hayJugada.set(verificarPatron(x, y) || hayJugada.get());
                i++;
            }
            //espera a que todos los hilos terminen el while, luego se rompe y PatronControlador puede continuar.
            barrierFinPatron.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}