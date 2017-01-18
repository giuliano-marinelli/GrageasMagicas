package org.lab.grageasmagicas;

import java.util.LinkedList;
import java.util.Observable;

/**
 * @author Bermudez Martin, Kurchan Ines, Marinelli Giuliano
 */
public class Gragea extends Observable {

    private int tipo;

    private LinkedList<Gragea> adyacentes;

    public Gragea(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
        setChanged();
        notifyObservers(tipo);
    }

}
