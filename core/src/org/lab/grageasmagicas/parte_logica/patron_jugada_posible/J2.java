package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Forma del patron: 0 es el elemento buscado
 * |0x|
 * |0x|
 * |x0|
 */
public class J2 extends Patron {
    public J2(AtomicBoolean hayJugadaRec, Gragea[][] matrizGragea, int alto,
              int ancho, CyclicBarrier barrierFinPatrones, Movimiento bMovimiento) {
        super(hayJugadaRec, matrizGragea, barrierFinPatrones, bMovimiento);
        //cada Patron calcula que posiciones debe verificar
        Point pos;
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                pos = new Point(i, j);
                if ((ancho - j > 1) && (alto - i > 2)) {
                    setPosicion(pos);
                }
            }
        }
    }

    @Override
    protected boolean verificarPatron(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 2][y + 1].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 2, y + 1), new Point(x + 2, y));
            System.out.println("J2 detecto movimiento en " + x + "," + y);
        }
        return res;
    }
}
