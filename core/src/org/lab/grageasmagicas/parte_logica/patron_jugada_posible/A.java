package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Forma del patron: 0 es el elemento buscado
 * |0x00|
 */
public class A extends Patron {

    public A(AtomicBoolean hayJugada, Gragea[][] matrizGragea, int alto, int ancho, CyclicBarrier barrierFinPatrones) {
        super(hayJugada, matrizGragea, barrierFinPatrones);
        Point pos;
        //cada Patron calcula que posiciones debe verificar
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                pos = new Point(i, j);
                if (ancho - j > 3) {
                    setPosicion(pos);
                }
            }
        }
    }

    @Override
    public boolean verificarPatron(int x, int y) {
        boolean res;
        res = (((matrizGragea[x][y].getTipo() == matrizGragea[x][y + 2].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x][y + 3].getTipo())));
      /*  if(res){
            System.out.println("A detecto movimiento en "+x+","+y);
        }*/
        return res;
    }

}
