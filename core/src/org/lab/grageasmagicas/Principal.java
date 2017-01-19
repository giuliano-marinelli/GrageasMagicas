package org.lab.grageasmagicas;

import org.lab.teclado.TecladoIn;

/**
 * @author Bermudez Martin, Kurchan Ines, Marinelli Giuliano
 */
public class Principal {

    public static void main(String[] args) {
        //System.out.println("\033[3" + 1 + ";43m" + 1 + "\033[30m");
        //System.out.println("Ingrese ancho del juego: ");
        int ancho = 5;//TecladoIn.readInt();
        //System.out.println("Ingrese alto del juego: ");
        int alto = 5;//TecladoIn.readInt();
        //System.out.println("Ingrese velocidad: ");
        int velocidad = 10;//TecladoIn.readInt();
        //System.out.println("Cantidad de grageas: ");
        int cantGragea = 3;//TecladoIn.readInt();

        //crea y lanza el juego
        Juego juego = new Juego(ancho, alto, velocidad, cantGragea);
        Thread juegoThread = new Thread(juego);
        juegoThread.start();
    }

}
