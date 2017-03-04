package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Bermu on 03/03/2017.
 */

public class BuscadorMovimientoDiagonal implements Runnable {

    private CyclicBarrier barrierFinBuscMov;
    private List<Point> posicion;
    private Gragea[][] matrizGragea;
    private AtomicBoolean hayJugadaRecta;
    private AtomicBoolean hayJugadaDiag;
    private Movimiento bMovimiento;
    /**
     * TIPO_PATRON:
     * ---jugadas en diagonal----
     * 0: el tipo de matriz que utiliza 2x3
     * 1: el tipo de matriz que utiliza 3x2
     * 2: el tipo de matriz que utiliza 2x4
     * 3: el tipo de matriz que utiliza 4x2
     */
    private int TIPO_PATRON;

    public BuscadorMovimientoDiagonal(AtomicBoolean hayJugadaRec, AtomicBoolean hayJugadaDiag, Gragea[][]
            matrizGragea, CyclicBarrier barrierFinPatrones, SubMatriz subMatriz, int TIPO_PATRON, Movimiento bMovimiento) {
        this.barrierFinBuscMov = barrierFinPatrones;
        posicion = new ArrayList<Point>();
        this.matrizGragea = matrizGragea;
        this.TIPO_PATRON = TIPO_PATRON;
        this.hayJugadaDiag = hayJugadaDiag;
        this.hayJugadaRecta = hayJugadaRec;
        this.bMovimiento = bMovimiento;
        switch (TIPO_PATRON) {
            case 0:
                posicion = subMatriz.getMatriz2x3();
                break;
            case 1:
                posicion = subMatriz.getMatriz3x2();
                break;
            case 2:
                posicion = subMatriz.getMatriz2x4();
                break;
            case 3:
                posicion = subMatriz.getMatriz4x2();
                break;
            default:
                throw new RuntimeException("Error, tipo patron diagonal no encontrado.");

        }
    }

    public boolean hayJugadaDiagonal() {
        return hayJugadaDiag.get();
    }

    @Override
    public void run() {
        try {
            int tam = posicion.size();
            int i = 0;
            int x;
            int y;
            while (i < tam && !hayJugadaDiag.get() && !hayJugadaRecta.get()) {
                x = posicion.get(i).x;
                y = posicion.get(i).y;
                hayJugadaDiag.set(verificarPatron(x, y) || hayJugadaDiag.get() || hayJugadaRecta.get());
                i++;
            }
            //espera a que todos los hilos terminen el while, luego se rompe y PatronControlador puede continuar.
            barrierFinBuscMov.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarPatron(int x, int y) {
        boolean res = false;
        switch (TIPO_PATRON) {
            case 0: //2x3
                res = verificarDiagonal1_2x3(x, y);
                if (!res) {
                    res = verificarDiagonal2_2x3(x, y);
                }
                if (!res) {
                    res = verificarDiagonal3_2x3(x, y);
                }
                if (!res) {
                    res = verificarDiagonal4_2x3(x, y);
                }
                if (!res) {
                    res = verificarDiagonal5_2x3(x, y);
                }
                if (!res) {
                    res = verificarDiagonal6_2x3(x, y);
                }
                if (!res) {
                    res = verificarDiagonal7_2x3(x, y);
                }
                if (!res) {
                    res = verificarDiagonal8_2x3(x, y);
                }
                break;
            case 1://3x2
                res = verificarDiagonal1_3x2(x, y);
                if (!res) {
                    res = verificarDiagonal2_3x2(x, y);
                }
                if (!res) {
                    res = verificarDiagonal3_3x2(x, y);
                }
                if (!res) {
                    res = verificarDiagonal4_3x2(x, y);
                }
                if (!res) {
                    res = verificarDiagonal5_3x2(x, y);
                }
                if (!res) {
                    res = verificarDiagonal6_3x2(x, y);
                }
                if (!res) {
                    res = verificarDiagonal7_3x2(x, y);
                }
                if (!res) {
                    res = verificarDiagonal8_3x2(x, y);
                }
                break;
            case 2://2x4
                res = verificarDiagonal1_2x4(x, y);
                if (!res) {
                    res = verificarDiagonal2_2x4(x, y);
                }
                if (!res) {
                    res = verificarDiagonal3_2x4(x, y);
                }
                if (!res) {
                    res = verificarDiagonal4_2x4(x, y);
                }
                break;
            case 3://4x2
                res = verificarDiagonal1_4x2(x, y);
                if (!res) {
                    res = verificarDiagonal2_4x2(x, y);
                }
                if (!res) {
                    res = verificarDiagonal3_4x2(x, y);
                }
                if (!res) {
                    res = verificarDiagonal4_4x2(x, y);
                }
                break;
            default:
                throw new RuntimeException("Error, tipo patron diagonal no encontrado.");
        }
        return res;
    }


    /*------2x3------*/
    private boolean verificarDiagonal1_2x3(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y + 1), new Point(x + 1, y + 2));
            System.out.println("1_2x3 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal2_2x3(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x][y + 1].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y + 1), new Point(x, y + 2));
            System.out.println("2_2x3 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal3_2x3(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x][y + 2].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y + 1), new Point(x, y));
            System.out.println("3_2x3 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal4_2x3(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y + 2].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y + 1), new Point(x + 1, y));
            System.out.println("4_2x3 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal5_2x3(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y + 2].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y), new Point(x + 1, y + 1));
            System.out.println("5_2x3 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal6_2x3(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x + 1][y].getTipo() == matrizGragea[x][y + 2].getTipo()) &&
                (matrizGragea[x + 1][y].getTipo() == matrizGragea[x + 1][y + 2].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y + 2), new Point(x + 1, y + 1));
            System.out.println("6_2x3 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal7_2x3(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x][y + 2].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y), new Point(x, y + 1));
            System.out.println("7_2x3 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal8_2x3(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y + 2].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x][y + 2].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y + 2), new Point(x, y + 1));
            System.out.println("8_2x3 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }


    /*------3x2------*/

    private boolean verificarDiagonal1_3x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y + 1), new Point(x + 2, y));
            System.out.println("1_3x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal2_3x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y + 1].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y), new Point(x + 2, y + 1));
            System.out.println("2_3x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal3_3x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x + 1][y].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x + 1][y].getTipo() == matrizGragea[x + 2][y + 1].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y), new Point(x, y + 1));
            System.out.println("3_3x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal4_3x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x + 1][y].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x + 1][y].getTipo() == matrizGragea[x + 2][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y + 1), new Point(x, y));
            System.out.println("4_3x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal5_3x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 2][y + 1].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 2][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 2, y), new Point(x + 1, y + 1));
            System.out.println("5_3x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal6_3x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x][y + 1].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 2][y + 1].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y), new Point(x + 1, y + 1));
            System.out.println("6_3x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal7_3x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x][y + 1].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 2][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y + 1), new Point(x + 1, y));
            System.out.println("7_3x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal8_3x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 2][y + 1].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 2][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y + 2), new Point(x + 1, y));
            System.out.println("8_3x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    /*------4x2------*/

    private boolean verificarDiagonal1_4x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 2][y + 1].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 3][y + 1].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y), new Point(x + 1, y + 1));
            System.out.println("1_4x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal2_4x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 2][y].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 3][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y + 1), new Point(x + 1, y));
            System.out.println("2_4x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal3_4x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 3][y + 1].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 3, y + 1), new Point(x + 2, y));
            System.out.println("3_4x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal4_4x2(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x][y + 1].getTipo() == matrizGragea[x + 3][y].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 3, y), new Point(x + 2, y + 1));
            System.out.println("4_4x2 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    /*------2x4------*/

    private boolean verificarDiagonal1_2x4(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x][y + 1].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y + 3].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y + 3), new Point(x, y + 2));
            System.out.println("1_2x4 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal2_2x4(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x + 1][y].getTipo() == matrizGragea[x][y + 2].getTipo()) &&
                (matrizGragea[x + 1][y].getTipo() == matrizGragea[x][y + 3].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x + 1, y), new Point(x, y + 1));
            System.out.println("2_2x4 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal3_2x4(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x + 1][y].getTipo() == matrizGragea[x + 1][y + 1].getTipo()) &&
                (matrizGragea[x + 1][y].getTipo() == matrizGragea[x][y + 3].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y + 3), new Point(x + 1, y + 2));
            System.out.println("3_2x4 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }

    private boolean verificarDiagonal4_2x4(int x, int y) {
        boolean res = false;
        res = ((matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y + 2].getTipo()) &&
                (matrizGragea[x][y].getTipo() == matrizGragea[x + 1][y + 3].getTipo()));
        if (res) {
            bMovimiento.setMovimiento(new Point(x, y), new Point(x + 1, y + 1));
            System.out.println("4_2x4 detecto movimiento diagonal en " + x + "," + y);
        }
        return res;
    }
}
