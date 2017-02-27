package org.lab.grageasmagicas.parte_logica;

import org.lab.estructuras.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bermudez Martin, Kurchan Ines, Marinelli Giuliano
 */
public class ComprobadorAncho extends Comprobador {

    public ComprobadorAncho(Gragea[][] matrizGrageas, int seccion, CopyOnWriteArrayList grageasCombinadas,  CyclicBarrier barrierComp, AtomicBoolean finJuego) {
        super(matrizGrageas, seccion, grageasCombinadas, barrierComp, finJuego);
    }

    @Override
    public void run() {
        try {
            while (!finJuego.get()) {
                barrierComp.await();
                if (!finJuego.get()) {
                    List<Point> combinacionTemp = new ArrayList();
                    combinacionTemp.add(new Point(seccion, 0));
                    int tipoAnt = matrizGrageas[seccion][0].getTipo();
                    for (int i = 1; i < matrizGrageas.length; i++) {
                        //if (matrizGrageas[seccion][i].getTipo() != 100 && tipoAnt != 100) {
                            if (tipoAnt != matrizGrageas[seccion][i].getTipo()) {
                                if (combinacionTemp.size() < 3) {
                                    combinacionTemp.clear();
                                } else {
                                    grageasCombinadas.addAll(combinacionTemp);
                                    combinacionTemp.clear();
                                }
                            }
                        //}
                        tipoAnt = matrizGrageas[seccion][i].getTipo();
                        combinacionTemp.add(new Point(seccion, i));
                    }
                    if (combinacionTemp.size() > 2) {
                        grageasCombinadas.addAll(combinacionTemp);
                    }
                    barrierComp.await();
                }
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Gragea.class.getName()).log(Level.SEVERE, null, e);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
