package org.lab.grageasmagicas;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Verifica si existe alguna jugada posible.
 */

public class PatronControlador implements Runnable {

    private CyclicBarrier barrierVerificarJugada;
    private Patron a;
    private Patron b;
    private Patron c;
    private Patron d;
    private Patron e;
    private Patron f;
    private Patron g;
    private Patron h;
    private Patron i;
    private Patron j;
    private Patron k;
    private Patron l;
    private boolean finJuego;
    private CyclicBarrier barrierFinPatrones;
    private AtomicBoolean hayJugada;

    public PatronControlador(Gragea[][] matrizGrageas, CyclicBarrier verificarJugada) {
        this.barrierVerificarJugada = verificarJugada;
        this.finJuego = false;
        int limAlto = (matrizGrageas.length - 1);
        int limAncho = (matrizGrageas[0].length - 1);
        barrierFinPatrones = new CyclicBarrier(13);
        hayJugada = new AtomicBoolean(false);
        a = new A(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        b = new B(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        c = new C(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        d = new D(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        e = new E(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        f = new F(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        g = new G(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        h = new H(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        i = new I(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        j = new J(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        k = new K(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
        l = new L(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
    }

    @Override
    public void run() {
        try {
            while (!finJuego) {
                barrierVerificarJugada.await();
                hayJugada.set(false);
                Thread tA = new Thread(a);
                tA.start();
                Thread tB = new Thread(b);
                tB.start();
                Thread tC = new Thread(c);
                tC.start();
                Thread tD = new Thread(d);
                tD.start();
                Thread tE = new Thread(e);
                tE.start();
                Thread tF = new Thread(f);
                tF.start();
                Thread tG = new Thread(g);
                tG.start();
                Thread tH = new Thread(h);
                tH.start();
                Thread tI = new Thread(i);
                tI.start();
                Thread tJ = new Thread(j);
                tJ.start();
                Thread tK = new Thread(k);
                tK.start();
                Thread tL = new Thread(l);
                tL.start();
                barrierFinPatrones.await();
                barrierVerificarJugada.await();
                barrierVerificarJugada.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * devuelve true si existe una jugada posible. false en caso contrario.
     *
     * @return
     */
    public boolean existeJugada() {
        //cualquier objeto patron va a tener el mismo valor en la variable hayJugada
        return a.hayJugada();
    }

    public void setFinJuego(boolean fin) {
        finJuego = fin;
    }

}
