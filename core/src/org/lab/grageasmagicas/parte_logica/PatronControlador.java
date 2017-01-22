package org.lab.grageasmagicas.parte_logica;

import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.B;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.C;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.D;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.E;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.F;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.G;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.H;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.I;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.J;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.K;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.L;
import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Verifica si existe alguna jugada posible.
 */

public class PatronControlador implements Runnable {

    private CyclicBarrier barrierVerificarJugada;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron a;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron b;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron c;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron d;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron e;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron f;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron g;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron h;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron i;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron j;
    private org.lab.grageasmagicas.parte_logica.patron_jugada_posible.Patron k;
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
        a = new org.lab.grageasmagicas.parte_logica.patron_jugada_posible.A(hayJugada, matrizGrageas, limAlto, limAncho, barrierFinPatrones);
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
