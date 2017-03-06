package org.lab.grageasmagicas.parte_logica;

import org.lab.grageasmagicas.parte_logica.patron_jugada_posible.*;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Verifica si existe alguna jugada posible.
 */

public class PatronControlador implements Runnable {

    //barrera que se rompe cuando se debe verificar una jugada o cuando se terminó de verificar.
    private CyclicBarrier barrierVerificarJugada;
    //valor booleano del estado del juego (si terminó o no)
    private AtomicBoolean finJuego;
    //barrera que se rompe cuando los patrones terminan de buscar jugadas posibles
    private CyclicBarrier barrierFinPatrones;
    private AtomicBoolean hayJugadaRecta;

    //patrones jugadas rectas
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
    private Patron i2;
    private Patron j2;
    private Patron k2;
    private Patron l2;
    private Patron sg;

    //movimiento encontrado
    private Movimiento movimientoRecto;


    public PatronControlador(Gragea[][] matrizGrageas, CyclicBarrier verificarJugada, AtomicBoolean finJuego) {
        this.barrierVerificarJugada = verificarJugada;
        this.finJuego = finJuego;
        int limAlto = (matrizGrageas.length);
        int limAncho = (matrizGrageas[0].length);
        barrierFinPatrones = new CyclicBarrier(18);
        hayJugadaRecta = new AtomicBoolean(false);
        movimientoRecto = new Movimiento();
        //patrones de jugadas rectas
        a = new A(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        b = new B(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        c = new C(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        d = new D(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        e = new E(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        f = new F(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        g = new G(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        h = new H(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        i = new I(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        j = new J(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        k = new K(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        l = new L(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        i2 = new I2(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        j2 = new J2(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        k2 = new K2(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        l2 = new L2(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);
        //buscador de superGragea
        sg = new SG(hayJugadaRecta, matrizGrageas, limAlto, limAncho, barrierFinPatrones, movimientoRecto);

    }

    @Override
    public void run() {
        try {
            while (!finJuego.get()) {
                barrierVerificarJugada.await();
                if (!finJuego.get()) {
                    movimientoRecto.clear();
                    hayJugadaRecta.set(false);
                    //patrones de jugadas rectas
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
                    Thread tI2 = new Thread(i2);
                    tI2.start();
                    Thread tJ2 = new Thread(j2);
                    tJ2.start();
                    Thread tK2 = new Thread(k2);
                    tK2.start();
                    Thread tL2 = new Thread(l2);
                    tL2.start();
                    Thread tSG = new Thread(sg);
                    tSG.start();

                    barrierFinPatrones.await();
                    barrierVerificarJugada.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * devuelve true si existe una jugada recta posible. false en caso contrario.
     *
     * @return
     */
    public boolean existeJugadaRecta() {
        //cualquier objeto patron va a tener el mismo valor en la variable hayJugada
        return a.hayJugadaRecta();
    }


    /**
     * devuelve un movimiento en linea recta si es que existe
     * @return
     */
    public Movimiento getJugadaRecta() {
        return movimientoRecto;
    }

}
