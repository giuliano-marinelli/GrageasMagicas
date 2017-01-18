package org.lab.grageasmagicas;

import org.lab.estructuras.Point;
import org.lab.teclado.TecladoIn;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Bermudez Martin, Kurchan Ines, Marinelli Giuliano
 */
public class Juego implements Runnable {

    private final Gragea[][] matrizGrageas;
    private int velocidad;
    private int cantGragea;
    private boolean pausa = false;
    private boolean fin = false;
    private CopyOnWriteArrayList<Point> grageasCombinadas;
    private Comprobador[] comprobadorAlto;
    private Comprobador[] comprobadorAncho;
    private Eliminador[] eliminadores;
    private CyclicBarrier barrierCompAlto;
    private CyclicBarrier barrierCompAncho;
    private CyclicBarrier barrierElim;

    public Juego(int ancho, int alto, int velocidad, int cantGragea) {
        this.velocidad = velocidad;
        this.cantGragea = cantGragea;

        matrizGrageas = new Gragea[alto][ancho];
        comprobadorAlto = new Comprobador[alto];
        comprobadorAncho = new Comprobador[ancho];
        eliminadores = new Eliminador[ancho];
        grageasCombinadas = new CopyOnWriteArrayList();

        Random random = new Random();

        //crea las grageas con un tipo aleatorio y las agrega a la matriz
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                matrizGrageas[i][j] = new Gragea(random.nextInt(cantGragea));
            }
        }

        //cargarMatrizDefault(matrizGrageas);

        barrierCompAlto = new CyclicBarrier(alto + 1);

        //crea y lanza los comprobadores
        Thread comprobadorThread;
        for (int i = 0; i < alto; i++) {
            comprobadorAlto[i] = new ComprobadorAlto(matrizGrageas, i, grageasCombinadas, barrierCompAlto);
            comprobadorThread = new Thread(comprobadorAlto[i]);
            comprobadorThread.start();
        }

        barrierCompAncho = new CyclicBarrier(ancho + 1);

        for (int i = 0; i < ancho; i++) {
            comprobadorAncho[i] = new ComprobadorAncho(matrizGrageas, i, grageasCombinadas, barrierCompAncho);
            comprobadorThread = new Thread(comprobadorAncho[i]);
            comprobadorThread.start();
        }

        barrierElim = new CyclicBarrier(ancho + 1);

        //crea y lanza los eliminadores
        Thread eliminadorThread;
        for (int i = 0; i < ancho; i++) {
            eliminadores[i] = new Eliminador(matrizGrageas, i, grageasCombinadas, barrierElim, cantGragea);
            eliminadorThread = new Thread(eliminadores[i]);
            eliminadorThread.start();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("\033[34mPrimer matriz\033[30m \n");
            //Imprime el juego por consola
            System.out.println(toStringComb(matrizGrageas));

            System.out.println("Presione enter...");
            TecladoIn.read();

            //incialmente se realizan las combinaciones que hallan salido de forma aleatoria
            realizarCombinaciones();

            Point grageaIni;
            Point grageaFin;
            while (!fin) {

                System.out.println("\033[32mJuega\033[30m \n");
                //Imprime el juego por consola
                System.out.println(toStringComb(matrizGrageas));

                boolean sonAdy = false;
                int gix;
                int giy;
                int gfx;
                int gfy;
                do {
                    //Permite que usuario pueda interactuar con la interfaz
                    System.out.print("Gragea inicial XY: ");
                    String gi = TecladoIn.readLine();
                    System.out.print("Gragea final XY: ");
                    String gf = TecladoIn.readLine();
                    System.out.println();
                    gix = Integer.parseInt(gi.substring(0, 1));
                    giy = Integer.parseInt(gi.substring(1, 2));
                    gfx = Integer.parseInt(gf.substring(0, 1));
                    gfy = Integer.parseInt(gf.substring(1, 2));

                    /*grageaIni = new Point(gix, giy);
                    grageaFin = new Point(gfx, gfy);*/
                    //verificar si el movimiento de las grageas es válido.
                    sonAdy = verificarAdyacentes(gix, giy, gfx, gfy);
                    if (!sonAdy) {
                        System.out.println("\033[31mMovimiento no válido\033[30m");
                    }

                } while (!sonAdy);

                //invertimos las grageas de lugar
                intercambiarGrageas(gix, giy, gfx, gfy);

                //despierta a los comprobadores
                barrierCompAncho.await();
                barrierCompAlto.await();
                //queda a la espera de que los comprobadores terminen
                barrierCompAncho.await();
                barrierCompAlto.await();

                //Imprime el juego por consola
                System.out.println("\033[34mGrageas intercambiadas\033[30m");
                System.out.println(toStringComb(matrizGrageas));

                System.out.println("Presione enter...");
                TecladoIn.read();

                //si las combinaciones de grageas esta vacia significa que el intercambio esta mal hecho
                if (grageasCombinadas.isEmpty()) {
                    System.out.println("\033[31mIntercambio de grageas incorrecto\033[30m");
                    System.out.println();
                    intercambiarGrageas(gix, giy, gfx, gfy);
                } else {
                    //System.out.println("grageasCombinadas NO IsEmpty()");
                    while (!grageasCombinadas.isEmpty()) {

                        System.out.println("\033[34mCombinacion y eliminacion de grageas: \033[30m");
                        System.out.println("\033[31mEliminadores: \033[30m");

                        //despierta a los eliminadores
                        barrierElim.await();
                        //queda en espera de que los eliminadores terminen
                        barrierElim.await();

                        //limpia el buffer con las combinaciones de grageas que ya fueron
                        //eliminadas por los eliminadores
                        grageasCombinadas.clear();

                        //despierta a los comprobadores
                        barrierCompAncho.await();
                        barrierCompAlto.await();
                        //queda a la espera de que los comprobadores terminen
                        barrierCompAncho.await();
                        barrierCompAlto.await();

                        //Imprime el juego por consola
                        System.out.println();
                        System.out.println(toStringComb(matrizGrageas));

                        System.out.println("Presione enter...");
                        TecladoIn.read();
                    }
                }

                //si el juego es pausado se detendra aqui
                if (pausa) {
                    dormir();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void realizarCombinaciones() {
        try {
            //despierta a los comprobadores
            barrierCompAncho.await();
            barrierCompAlto.await();
            //queda a la espera de que los comprobadores terminen
            barrierCompAncho.await();
            barrierCompAlto.await();

            while (!grageasCombinadas.isEmpty()) {

                System.out.println("\033[34mCombinacion y eliminacion de grageas: \033[30m");
                System.out.println("\033[31mEliminadores: \033[30m");

                //despierta a los eliminadores
                barrierElim.await();
                //queda en espera de que los eliminadores terminen
                barrierElim.await();

                //limpia el buffer con las combinaciones de grageas que ya fueron
                //eliminadas por los eliminadores
                grageasCombinadas.clear();

                //despierta a los comprobadores
                barrierCompAncho.await();
                barrierCompAlto.await();
                //queda a la espera de que los comprobadores terminen
                barrierCompAncho.await();
                barrierCompAlto.await();

                //Imprime el juego por consola
                System.out.println();
                System.out.println(toStringComb(matrizGrageas));

                System.out.println("Presione enter...");
                TecladoIn.read();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve un string con la matriz del juego para imprimirla por pantalla.
     *
     * @param juego
     * @return String de la matriz.
     */
    public String toString(Gragea[][] juego) {
        int alto = juego.length;
        int ancho = juego[0].length;
        String res = "    ";
        for (int i = 0; i < alto; i++) {
            res += " " + i;
        }
        res += "\n    ";
        for (int i = 0; i < alto; i++) {
            res += "__";
        }
        res += "\n";
        for (int i = 0; i < alto; i++) {
            res += i + "  | ";
            for (int j = 0; j < ancho - 1; j++) {
                res += "\033[3" + (juego[i][j].getTipo() + 1) + "m" + juego[i][j].getTipo() + "\033[30m";
                res += ",";
            }
            res += "\033[3" + (juego[i][ancho - 1].getTipo() + 1) + "m" + juego[i][ancho - 1].getTipo() + "\033[30m";
            res += "\n";
        }
        return res;
    }

    /**
     * Devuelve un string con la matriz del juego indicando las combinaciones que se encontraron
     * para imprimirla por pantalla.
     *
     * @param juego
     * @return String de la matriz.
     */
    public String toStringComb(Gragea[][] juego) {
        int alto = juego.length;
        int ancho = juego[0].length;
        String res = "    ";
        for (int i = 0; i < alto; i++) {
            res += " " + i;
        }
        res += "\n    ";
        for (int i = 0; i < alto; i++) {
            res += "__";
        }
        res += "\n";
        for (int i = 0; i < alto; i++) {
            res += i + "  | ";
            for (int j = 0; j < ancho - 1; j++) {
                if (grageasCombinadas.contains(new Point(i, j))) {
                    res += "\033[3" + (juego[i][j].getTipo() + 1) + ";40m" + juego[i][j].getTipo() + "\033[30m";
                    res += "\033[30;40m" + "," + "\033[30m";
                } else {
                    res += "\033[3" + (juego[i][j].getTipo() + 1) + "m" + juego[i][j].getTipo() + "\033[30m";
                    res += ",";
                }
            }
            if (grageasCombinadas.contains(new Point(i, (ancho - 1)))) {
                res += "\033[3" + (juego[i][ancho - 1].getTipo() + 1) + ";40m" + juego[i][ancho - 1].getTipo() + "\033[30m";
            } else {
                res += "\033[3" + (juego[i][ancho - 1].getTipo() + 1) + "m" + juego[i][ancho - 1].getTipo() + "\033[30m";
            }
            res += "\n";
        }
        return res;
    }

    /**
     * Duerme al juego (simula pausar).
     *
     * @throws InterruptedException
     */
    synchronized public void dormir() throws InterruptedException {
        wait();
    }

    /**
     * Despierta al juego (simula despausar).
     */
    synchronized public void despertar() {
        notify();
    }

    /**
     * Termina el hilo, seteando fin en true.
     */
    public void terminar() {
        this.fin = true;
        System.out.println("\033[34mFIN\033[30m \n");
    }


    /**
     * Modifica el tipo de las grageas de forma aleatoria y lo indica por
     * consola.
     */
    public void random() {
        System.out.println("\033[34mRANDOM\033[30m \n");
        Random random = new Random();
        for (int i = 0; i < matrizGrageas.length; i++) {
            for (int j = 0; j < matrizGrageas[0].length; j++) {
                matrizGrageas[i][j].setTipo((random.nextInt(cantGragea)));
            }
        }
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Sube la velocidad del juego si es posible (la velocidad aumenta cuanto
     * menor es).
     */
    public void subirVelocidad() {
        if (this.velocidad > 0) {
            this.velocidad--;
        }
    }

    /**
     * Baja la velocidad del juego (la velocidad disminuye cuanto mayor es).
     */
    public void bajarVelocidad() {
        if (this.velocidad < 9) {
            this.velocidad++;
        }
    }

    public boolean getPausa() {
        return pausa;
    }

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    /**
     * Pausea o despausea y muestra el mensaje por consola.
     */
    public void modPausa() {
        if (!pausa) {
            System.out.println("\033[34mPAUSE\033[30m \n");
        } else {
            System.out.println("\033[34mUNPAUSE\033[30m \n");
        }
        this.pausa = !pausa;
    }

    public Gragea[][] getMatrizGrageas() {
        return matrizGrageas;
    }

    /**
     * @param gix
     * @param giy
     * @param gfx
     * @param gfy
     */
    public void intercambiarGrageas(int gix, int giy, int gfx, int gfy) {
        Gragea grageaAux = matrizGrageas[gix][giy];
        matrizGrageas[gix][giy] = matrizGrageas[gfx][gfy];
        matrizGrageas[gfx][gfy] = grageaAux;
    }

    /**
     * @param gix
     * @param giy
     * @param gfx
     * @param gfy
     */
    public void intercambiarTipoGrageas(int gix, int giy, int gfx, int gfy) {
        int tipoAux = matrizGrageas[gix][giy].getTipo();
        matrizGrageas[gix][giy].setTipo(matrizGrageas[gfx][gfy].getTipo());
        matrizGrageas[gfx][gfy].setTipo(tipoAux);
    }

    private void cargarMatrizDefault(Gragea[][] matrizGr) {
        matrizGr[0][0] = new Gragea(1);
        matrizGr[0][1] = new Gragea(0);
        matrizGr[0][2] = new Gragea(1);
        matrizGr[0][3] = new Gragea(1);
        matrizGr[0][4] = new Gragea(0);
        matrizGr[1][0] = new Gragea(3);
        matrizGr[1][1] = new Gragea(2);
        matrizGr[1][2] = new Gragea(0);
        matrizGr[1][3] = new Gragea(1);
        matrizGr[1][4] = new Gragea(0);
        matrizGr[2][0] = new Gragea(2);
        matrizGr[2][1] = new Gragea(3);
        matrizGr[2][2] = new Gragea(0);
        matrizGr[2][3] = new Gragea(0);
        matrizGr[2][4] = new Gragea(2);
        matrizGr[3][0] = new Gragea(2);
        matrizGr[3][1] = new Gragea(3);
        matrizGr[3][2] = new Gragea(3);
        matrizGr[3][3] = new Gragea(2);
        matrizGr[3][4] = new Gragea(1);
        matrizGr[4][0] = new Gragea(0);
        matrizGr[4][1] = new Gragea(1);
        matrizGr[4][2] = new Gragea(1);
        matrizGr[4][3] = new Gragea(2);
        matrizGr[4][4] = new Gragea(3);
    }

    /**
     * Verifica que el intercambio de grageas sea una jugada válida.
     * @param gix
     * @param giy
     * @param gfx
     * @param gfy
     * @return
     */
    private boolean verificarAdyacentes(int gix, int giy, int gfx, int gfy) {
        boolean res;
        res = ((gix + 1 == gfx && giy == gfy) || (gix - 1 == gfx && giy == gfy) || (gix == gfx && giy + 1 == gfy)
                || (gix == gfx && giy - 1 == gfy));
        res = (res && (gix >= 0) && (gix <= matrizGrageas.length - 1) && (gfx >= 0) && (gfx <= matrizGrageas.length - 1)
                && (giy >= 0) && (giy <= matrizGrageas[0].length - 1) && (gfy >= 0) && (gfy <= matrizGrageas.length - 1));
        return res;
    }
}
