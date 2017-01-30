package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import org.lab.grageasmagicas.parte_logica.JuegoLogico2;

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
            int ancho = 5;
            int alto = 5;
            int cantGragea = 3 + (nivel / 10);
            int velocidad = 10;
            int movimientos = 10 + ((nivel / 10) * 5);
            int puntajeGanar = movimientos * 30 + (nivel * 25);
            AtomicBoolean finJuego = new AtomicBoolean(false);

            JuegoLogico2 juegoLogico = new JuegoLogico2(ancho, alto, velocidad, cantGragea, movimientos, puntajeGanar, finJuego);

            JuegoVisual juegoVisual = new JuegoVisual(adminPantalla);

            JuegoControlador juegoControlador = new JuegoControlador(juegoLogico, juegoVisual, finJuego);
            Thread juegoControladorThread = new Thread(juegoControlador);
            juegoControladorThread.start();

            adminPantalla.setScreen(juegoVisual);
        } else {
            System.out.println("Nivel no alcanzado");
        }
        //System.out.println(cantGragea+","+movimientos+","+puntajeGanar);
    }

}
