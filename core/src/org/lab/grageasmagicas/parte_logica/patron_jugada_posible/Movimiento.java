package org.lab.grageasmagicas.parte_logica.patron_jugada_posible;

import org.lab.estructuras.Point;

/**
 * Created by Bermu on 24/02/2017.
 */

public class Movimiento {

    private Point movimientoIni;
    private Point movimientoFin;


    public Movimiento() {
        movimientoIni = new Point();
        movimientoFin = new Point();
    }

    public Movimiento(Point ini, Point fin) {
        movimientoFin = fin;
        movimientoIni = ini;
    }

    public synchronized void setMovimiento(Point ini, Point fin){
        movimientoIni = ini;
        movimientoFin = fin;
    }

    @Deprecated
    public synchronized void setMovimientoIni(int x, int y) {
        movimientoIni.x = x;
        movimientoIni.y = y;
    }

    @Deprecated
    public synchronized void setMovimientoFin(int x, int y) {
        movimientoFin.x = x;
        movimientoFin.y = y;
    }

    public Point getMovimientoIni() {
        return movimientoIni;
    }

    public Point getMovimientoFin() {
        return movimientoFin;
    }

    public void clear(){
        movimientoIni = null;
        movimientoFin = null;
    }

}
