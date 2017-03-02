package org.lab.grageasmagicas.parte_logica;

import java.util.concurrent.atomic.AtomicBoolean;

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
        //System.out.println("Nivel: ");
        int nivel = 0;//TecladoIn.readInt();
        //System.out.println("Ingrese velocidad: ");
        int velocidad = 10;//TecladoIn.readInt();
        //System.out.println("Cantidad de grageas: ");
        int cantGragea = 4 + nivel / 10;//TecladoIn.readInt();
        //System.out.println("Cantidad de movimientos: ");
        int movimientos = 5 + (nivel / 5) * 2;//TecladoIn.readInt();
        //System.out.println("Puntaje para ganar: ");
        int puntajeGanar = movimientos * 30 + (nivel + 1) * 50 - (nivel / 10) * (500);//TecladoIn.readInt();

        int poderMovDiagonalUsos = nivel / 10;

        AtomicBoolean finJuego = new AtomicBoolean(false);

        //crea y lanza el juego
        Juego juego = new Juego(ancho, alto, velocidad, cantGragea, movimientos, puntajeGanar, nivel, poderMovDiagonalUsos, 0, 60, 0, finJuego);
        Thread juegoThread = new Thread(juego);
        juegoThread.start();
    }

}
