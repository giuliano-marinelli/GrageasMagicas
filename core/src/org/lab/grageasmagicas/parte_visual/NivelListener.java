package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import org.lab.grageasmagicas.parte_logica.Juego;

import java.util.concurrent.atomic.AtomicBoolean;

public class NivelListener extends ClickListener {

    private int nivel;
    private int nivelLogrado;
    private AdministradorPantalla adminPantalla;

    public NivelListener(int nivel, int nivelLogrado, AdministradorPantalla adminPantalla) {
        this.nivel = nivel;
        this.nivelLogrado = nivelLogrado;
        this.adminPantalla = adminPantalla;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (nivel <= nivelLogrado) {
            int ancho = 5 + (nivel / 15);
            int alto = 5 + (nivel / 15);
            int velocidad = 10;
            int cantGragea = 4 + nivel / 10;
            int movimientos = 5 + (nivel / 5) * 2;
            int puntajeGanar = movimientos * 30 + (nivel + 1) * 75 - (nivel / 10) * (750);
            int poderMovDiagonalUsos = nivel / 10;
            AtomicBoolean finJuego = new AtomicBoolean(false);

            Juego juegoLogico = new Juego(ancho, alto, velocidad, cantGragea, movimientos, puntajeGanar, nivel, poderMovDiagonalUsos, 0, 0, 0, finJuego);

            JuegoVisual juegoVisual = new JuegoVisual(adminPantalla);

            JuegoControlador juegoControlador = new JuegoControlador(juegoLogico, juegoVisual, finJuego);
            Thread juegoControladorThread = new Thread(juegoControlador);
            juegoControladorThread.start();

            adminPantalla.setScreen(juegoVisual);
            //System.out.println(nivel+","+cantGragea+","+movimientos+","+puntajeGanar);
        } else {
            System.out.println("Nivel no alcanzado");
        }
    }

}
