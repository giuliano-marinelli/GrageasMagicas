package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Bermu on 22/01/2017.
 */

/**
 * Forma del patron: 0 es el elemento buscado.
 * |0x|
 * |x0|
 * |0x|
 */
public class G extends Patron {

    public G(AtomicBoolean hayJugada, Gragea[][] matrizGragea, int alto, int ancho, CyclicBarrier barrierFinPatrones) {
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
        boolean res;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 2][y].getTipo()));
       if (res) {
            System.out.println("G detecto movimiento en " + x + "," + y);
        }
        return res;
    }
}
