package org.lab.grageasmagicas;

import org.lab.estructuras.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bermudez Martin, Kurchan Ines, Marinelli Giuliano
 */
public class ComprobadorAncho extends Comprobador {

    public ComprobadorAncho(Gragea[][] matrizGrageas, int seccion, CopyOnWriteArrayList grageasCombinadas, CyclicBarrier finComp) {
        super(matrizGrageas, seccion, grageasCombinadas, finComp);
    }

    @Override
    public void run() {
        try {
            while (true) {
                barrierComp.await();
                List<Point> combinacionTemp = new ArrayList();
                combinacionTemp.add(new Point(seccion, 0));
                int tipoAnt = matrizGrageas[seccion][0].getTipo();
                for (int i = 1; i < matrizGrageas.length; i++) {
                    if (tipoAnt != matrizGrageas[seccion][i].getTipo()) {
                        if (combinacionTemp.size() < 3) {
                            combinacionTemp.clear();
                        } else {
                            grageasCombinadas.addAll(combinacionTemp);
                            combinacionTemp.clear();
                        }
                    }
                    combinacionTemp.add(new Point(seccion, i));
                    tipoAnt = matrizGrageas[seccion][i].getTipo();
                }
                if (combinacionTemp.size() > 2) {
                    grageasCombinadas.addAll(combinacionTemp);
                }
                barrierComp.await();
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Gragea.class.getName()).log(Level.SEVERE, null, e);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
