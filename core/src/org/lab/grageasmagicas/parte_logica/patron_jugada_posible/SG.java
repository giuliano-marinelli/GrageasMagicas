package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Forma del patron:
 * Busca si hay una super gragea
 */
public class SG extends Patron {
    public SG(AtomicBoolean hayJugadaRec, AtomicBoolean hayJugadaDiag, Gragea[][] matrizGragea, int alto,
              int ancho, CyclicBarrier barrierFinPatrones, Movimiento bMovimiento) {
        super(hayJugadaRec, hayJugadaDiag, matrizGragea, barrierFinPatrones, bMovimiento);
        //cada Patron calcula que posiciones debe verificar
        Point pos;
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                pos = new Point(i, j);
                //if ((ancho - j > 1) && (alto - i > 2)) {
                setPosicion(pos);
                //}
            }
        }
    }

    @Override
    protected boolean verificarPatron(int x, int y) {
        boolean res = false;
        res = (matrizGragea[x][y].getTipo() == 100);
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y), new Point(x, y));
            System.out.println("SG detecto movimiento en " + x + "," + y);
        }
        return res;
    }
}
