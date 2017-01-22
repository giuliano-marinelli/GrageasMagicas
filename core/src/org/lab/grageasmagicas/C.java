package org.lab.grageasmagicas;

import org.lab.estructuras.Point;

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
    public C(AtomicBoolean hayJugada, Gragea[][] matrizGragea, int alto, int ancho, CyclicBarrier barrierFinPatrones) {
        super(hayJugada, matrizGragea, barrierFinPatrones);
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
       /* if(res){
            System.out.println("C detecto movimiento en "+x+","+y);
        }*/
        return res;
    }
}
