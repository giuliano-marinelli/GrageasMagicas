package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Forma del patron: 0 es el elemento buscado
 * |x0|
 * |x0|
 * |0x|
 */

public class I2 extends Patron {
    public I2(AtomicBoolean hayJugadaRec, Gragea[][] matrizGragea, int alto,
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
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 2][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 2, y), new Point(x + 2, y + 1));
            System.out.println("I2 detecto movimiento en " + x + "," + y);
        }
        return res;
    }
}
