package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Forma del patron: 0 es el elemento buscado
 * |0|
 * |0|
 * |x|
 * |0|
 */
public class D extends Patron {

    public D(AtomicBoolean hayJugadaRec, AtomicBoolean hayJugadaDiag, Gragea[][] matrizGragea, int alto,
             int ancho, CyclicBarrier barrierFinPatrones, Movimiento bMovimiento) {
        super(hayJugadaRec, hayJugadaDiag, matrizGragea, barrierFinPatrones, bMovimiento);
        Point pos;
        //cada Patron calcula que posiciones debe verificar
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                pos = new Point(i, j);
                if (alto - i > 3) {
                    setPosicion(pos);
                }
            }
        }
    }

    @Override
    protected boolean verificarPatron(int x, int y) {
        boolean res;
        res = (((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 3][y].getTipo())));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 3, y), new Point(x + 2, y));
            System.out.println("D detecto movimiento en " + x + "," + y);
        }
        return res;
    }
}
