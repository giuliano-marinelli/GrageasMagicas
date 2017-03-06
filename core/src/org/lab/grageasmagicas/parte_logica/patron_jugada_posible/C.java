package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Forma del patron: 0 es el elemento buscado
 * |0|
 * |x|
 * |0|
 * |0|
 */
public class C extends Patron {
    public C(AtomicBoolean hayJugadaRec, Gragea[][] matrizGragea, int alto,
             int ancho, CyclicBarrier barrierFinPatrones, Movimiento bMovimiento) {
        super(hayJugadaRec, matrizGragea, barrierFinPatrones, bMovimiento);
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
        res = (((matrizGragea[x][y].getTipo() == matrizGragea[x + 2][y].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 3][y].getTipo())));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y), new Point(x + 1, y));
            System.out.println("C detecto movimiento en " + x + "," + y);
        }
        return res;
    }
}
