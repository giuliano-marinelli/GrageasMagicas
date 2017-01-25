package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Forma del patron: 0 es el elemento buscado
 * |x0|
 * |0x|
 * |x0|
 */
public class F extends Patron {

    public F(AtomicBoolean hayJugada, Gragea[][] matrizGragea, int alto, int ancho, CyclicBarrier barrierFinPatrones) {
        super(hayJugada, matrizGragea, barrierFinPatrones);
        Point pos;
        //cada Patron calcula que posiciones debe verificar
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
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 2][y + 1].getTipo()));
        if (res) {
            System.out.println("F detecto movimiento en " + x + "," + y);
        }
        return res;
    }
}
