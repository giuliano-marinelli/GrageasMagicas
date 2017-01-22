package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Forma del patron:
 * |x0x|
 * |0x0|
 */
public class E extends Patron {

    public E(AtomicBoolean hayJugada, Gragea[][] matrizGragea, int alto, int ancho, CyclicBarrier barrierFinPatrones) {
        super(hayJugada, matrizGragea, barrierFinPatrones);
        Point pos;
        //cada Patron calcula que posiciones debe verificar
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                pos = new Point(i, j);
                if ((ancho - j > 2) && (alto - i > 1)) {
                    setPosicion(pos);
                }
            }
        }
    }

    @Override
    protected boolean verificarPatron(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x + 1][y].getTipo() == matrizGragea[x][y + 1].getTipo()) &&
                (matrizGragea[x + 1][y].getTipo() == matrizGragea[x + 1][y + 2].getTipo()));
        /*if (res) {
            System.out.println("E detecto movimiento en " + x + "," + y);
        }*/
        return res;
    }
}
