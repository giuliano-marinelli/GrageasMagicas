package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bermu on 22/02/2017.
 */

public class SubMatriz {

    private Gragea[][] matrizGragea;
    private List<Point> matriz4x1;
    private List<Point> matriz1x4;
    private List<Point> matriz2x2;
    private List<Point> matriz2x3;
    private List<Point> matriz3x2;
    private List<Point> matriz4x3;
    private List<Point> matriz3x4;
    private List<Point> matriz2x4;
    private List<Point> matriz4x2;
    private List<Point> matriz2x5;
    private List<Point> matriz5x2;

    public SubMatriz(Gragea[][] matrizGragea) {
        this.matrizGragea = matrizGragea;
        matriz4x1 = new ArrayList<Point>();
        matriz1x4 = new ArrayList<Point>();
        matriz2x2 = new ArrayList<Point>();
        matriz2x3 = new ArrayList<Point>();
        matriz3x2 = new ArrayList<Point>();
        matriz4x3 = new ArrayList<Point>();
        matriz3x4 = new ArrayList<Point>();
        matriz2x4 = new ArrayList<Point>();
        matriz4x2 = new ArrayList<Point>();
        matriz5x2 = new ArrayList<Point>();
        matriz2x5 = new ArrayList<Point>();
        int ancho = matrizGragea[0].length;
        int alto = matrizGragea.length;
        //cargar matriz 1x4
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if (ancho - j > 3) {
                    matriz1x4.add(new Point(i, j));
                }
            }
        }

        //cargar matriz 4x1
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if (alto - i > 3) {
                    matriz4x1.add(new Point(i, j));
                }
            }
        }

        //cargar matriz 2x2
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((alto - i > 1) && (ancho - j > 1)) {
                    matriz2x2.add(new Point(i, j));
                }
            }
        }

        //cargar matriz 2x3
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((ancho - j > 2) && (alto - i > 1)) {
                    matriz2x3.add(new Point(i, j));
                }
            }
        }

        //cargar matriz 3x2
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((ancho - j > 1) && (alto - i > 2)) {
                    matriz3x2.add(new Point(i, j));
                }
            }
        }

        //cargar matriz 2x4
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((ancho - j > 3) && (alto - i > 1)) {
                    matriz2x4.add(new Point(i, j));
                }
            }
        }
        //cargar matriz 4x2
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((ancho - j > 1) && (alto - i > 3)) {
                    matriz4x2.add(new Point(i, j));
                }
            }
        }
        //cargar matriz 4x3
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((ancho - j > 2) && (alto - i > 3)) {
                    matriz4x3.add(new Point(i, j));
                }
            }
        }
        //cargar matriz 3x4
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((ancho - j > 3) && (alto - i > 2)) {
                    matriz3x4.add(new Point(i, j));
                }
            }
        }

        //cargar matriz 2x5
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((ancho - j > 4) && (alto - i > 1)) {
                    matriz2x5.add(new Point(i, j));
                }
            }
        } //cargar matriz 5x2
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if ((ancho - j > 1) && (alto - i > 4)) {
                    matriz5x2.add(new Point(i, j));
                }
            }
        }
    }


    public List<Point> getMatriz4x2() {
        return matriz4x2;
    }

    public List<Point> getMatriz2x4() {
        return matriz2x4;
    }

    public List<Point> getMatriz3x4() {
        return matriz3x4;
    }

    public List<Point> getMatriz4x3() {
        return matriz4x3;
    }

    public List<Point> getMatriz2x2() {
        return matriz2x2;
    }

    public List<Point> getMatriz3x2() {
        return matriz3x2;
    }

    public List<Point> getMatriz2x3() {
        return matriz2x3;
    }

    public List<Point> getMatriz1x4() {
        return matriz1x4;
    }

    public List<Point> getMatriz4x1() {
        return matriz4x1;
    }

    public List<Point> getMatriz2x5() {
        return matriz2x5;
    }

    public List<Point> getMatriz5x2() {
        return matriz5x2;
    }

}
