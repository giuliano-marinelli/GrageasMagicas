package org.lab.grageasmagicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.estructuras.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;


public class JuegoVisual implements Screen, Observer {

    private boolean hayGrageaSeleccionada;
    private int primerGrageaX;
    private int primerGrageaY;
    private int segundaGrageaX;
    private int segundaGrageaY;
    private int anchoCamara;
    private int altoCamara;
    private boolean inputHabilitado;
    private GrageaVisual[][] matrizGrageasVisuales;
    private AdministradorPantalla adminPantalla;
    private AssetManager assetManager;
    private Viewport vista;
    private Stage escena;
    private CyclicBarrier barrierRespuestaVisual;
    private Table tblTablero;
    private Texture texturaFondo;
    private Texture texturaGragea;
    private Juego juegoLogico;

    public JuegoVisual(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();
        this.inputHabilitado = false;

        cargarTexturas();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);

        tblTablero = new Table();
        tblTablero.background(new TextureRegionDrawable(new TextureRegion(texturaFondo)));
        tblTablero.setColor(Color.GOLD);
        escena.addActor(tblTablero);
    }

    public void cargarTexturas() {
        assetManager.load("fondogolosinas.png", Texture.class);
        assetManager.load("gragea.png", Texture.class);
        assetManager.finishLoading();
        texturaFondo = assetManager.get("fondogolosinas.png");
        texturaGragea = assetManager.get("gragea.png");
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void show() {
        try {
            if (juegoLogico != null) {
                Gragea[][] matrizGrageasLogica = juegoLogico.getMatrizGrageas();
                int cantColumnas = matrizGrageasLogica[0].length;
                int cantFilas = matrizGrageasLogica.length;
                if (matrizGrageasVisuales == null) {
                    matrizGrageasVisuales = new GrageaVisual[cantFilas][cantColumnas];
                }
                if (juegoLogico.getPrimerGrageaX() != -1) {
                    GrageaVisual priGragea = matrizGrageasVisuales[juegoLogico.getPrimerGrageaX()][juegoLogico.getPrimerGrageaY()];
                    GrageaVisual segGragea = matrizGrageasVisuales[juegoLogico.getSegundaGrageaX()][juegoLogico.getSegundaGrageaY()];
                    //System.out.println(priGragea.getX() + "," + priGragea.getY());
                    //System.out.println(segGragea.getX() + "," + segGragea.getY());
                    priGragea.addAction(Actions.moveTo
                            (segGragea.getX(), segGragea.getY(), 0.5f, Interpolation.bounceOut));
                    segGragea.addAction(Actions.moveTo
                            (priGragea.getX(), priGragea.getY(), 0.5f, Interpolation.bounceOut));
                    sleep(750);
                }

                tblTablero.clear();
                tblTablero.row();
                boolean hayNuevas = false;
                for (int i = 0; i < cantFilas; i++) {
                    for (int j = 0; j < cantColumnas; j++) {
                        /*boolean fueEliminada = false;
                        float posXNuevaGrageaVisual = 0;
                        float posYNuevaGrageaVisual = 0;
                        if (matrizGrageasVisuales[i][j] != null) {
                            fueEliminada = !matrizGrageasVisuales[i][j].isVisible();
                            posXNuevaGrageaVisual = matrizGrageasVisuales[i][j].getX();
                            posYNuevaGrageaVisual = matrizGrageasVisuales[i][j].getY();
                        }*/
                        matrizGrageasVisuales[i][j] = new GrageaVisual(matrizGrageasLogica[i][j].getTipo(), i, j, texturaGragea);
                        matrizGrageasVisuales[i][j].addListener(new GrageaVisualListener(matrizGrageasVisuales[i][j],
                                matrizGrageasLogica[i][j], this));
                        tblTablero.add(matrizGrageasVisuales[i][j]);
                        /*if (fueEliminada) {
                            hayNuevas = true;
                            System.out.println("fue eliminada = " + i + "," + j + " pos =" + posXNuevaGrageaVisual + "," + posYNuevaGrageaVisual);
                            matrizGrageasVisuales[i][j].setPosition(posXNuevaGrageaVisual, altoCamara);
                            matrizGrageasVisuales[i][j].setVisible(true);
                            matrizGrageasVisuales[i][j].addAction(Actions.moveTo
                                    (posXNuevaGrageaVisual, posYNuevaGrageaVisual, 0.5f, Interpolation.bounceOut));
                        }*/
                    }
                    tblTablero.row();
                }
                if (hayNuevas) {
                    sleep(1000);
                }

                tblTablero.padBottom(5f);
                tblTablero.setFillParent(true);
                tblTablero.pack();

                if (matrizGrageasVisuales[0][0] != null) {
                    eliminadorVisual(juegoLogico);
                    if (!juegoLogico.getGrageasCombinadas().isEmpty()) {
                        sleep(750);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void eliminadorVisual(Juego juegoLogico) {
        Gragea[][] matrizGrageasLogica = juegoLogico.getMatrizGrageas();
        CopyOnWriteArrayList<Point> grageasCombinadas = juegoLogico.getGrageasCombinadas();
        int cantColumnas = matrizGrageasLogica[0].length;
        int cantFilas = matrizGrageasLogica.length;

        for (int j = 0; j < cantColumnas; j++) {
            List<Integer> combinacionTemp = new ArrayList();
            for (int i = 0; i < grageasCombinadas.size(); i++) {
                if (grageasCombinadas.get(i).y == j) {
                    combinacionTemp.add(grageasCombinadas.get(i).x);
                }
            }
            Collections.sort(combinacionTemp);
            HashSet hs = new HashSet();
            hs.addAll(combinacionTemp);
            combinacionTemp.clear();
            combinacionTemp.addAll(hs);
            Random random = new Random();
            int bajar = 0;
            for (int i = cantFilas - 1; i >= 0; i--) {
                if (combinacionTemp.contains(i)) {
                    matrizGrageasVisuales[i][j].setVisible(false);
                    bajar++;
                } else {
                    if (bajar != 0) {
                        float posXAnt = matrizGrageasVisuales[i][j].getX();
                        float posYAnt = matrizGrageasVisuales[i][j].getY();
                        matrizGrageasVisuales[i][j].addAction(Actions.moveTo
                                (matrizGrageasVisuales[i + bajar][j].getX(), matrizGrageasVisuales[i + bajar][j].getY(),
                                        0.5f, Interpolation.bounceOut));
                        matrizGrageasVisuales[i + bajar][j].setPosition(posXAnt, posYAnt);
                        GrageaVisual aux = matrizGrageasVisuales[i][j];
                        matrizGrageasVisuales[i][j] = matrizGrageasVisuales[i + bajar][j];
                        matrizGrageasVisuales[i + bajar][j] = aux;
                    }
                }
            }
            /*while (!combinacionTemp.isEmpty()) {
                matrizGrageasVisuales[combinacionTemp.get(0)][j].setVisible(false);
                for (int i = combinacionTemp.get(0); i > min; i--) {
                    System.out.println("mover gragea: " + (i - 1) + "," + j + " a " + matrizGrageasVisuales[i][j].getX() + "," + matrizGrageasVisuales[i][j].getY());
                    matrizGrageasVisuales[i - 1][j].addAction(Actions.moveTo
                            (matrizGrageasVisuales[i][j].getX(), matrizGrageasVisuales[i][j].getY(),
                                    0.5f, Interpolation.bounceOut));
                    matrizGrageasVisuales[i][j] = matrizGrageasVisuales[i - 1][j];
                }
                min++;
                combinacionTemp.remove(0);
            }*/
        }
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.act(delta);
        escena.setViewport(vista);
        escena.draw();
    }

    @Override
    public void update(Observable observable, Object o) {
        juegoLogico = (Juego) observable;
        show();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaGragea.dispose();
        escena.dispose();
        assetManager.unload("fondogolosinas.png");
        assetManager.unload("gragea.png");
    }

    /*
        public void intercambiarGrageas() {
            try {

                //Intercambiamos las grageas visuales en la matriz
                GrageaVisual aux = matrizGrageasVisuales[segundaGrageaX][segundaGrageaY];
                matrizGrageasVisuales[segundaGrageaX][segundaGrageaY] = matrizGrageasVisuales[primerGrageaX][primerGrageaY];
                matrizGrageasVisuales[primerGrageaX][primerGrageaY] = aux;

                matrizGrageasVisuales[primerGrageaX][primerGrageaY].deseleccionar();

                GrageaVisual primeraSeleccionada = matrizGrageasVisuales[primerGrageaX][primerGrageaY];
                GrageaVisual segundaSeleccionada = matrizGrageasVisuales[segundaGrageaX][segundaGrageaY];

                //Intercambia la posicion en pantalla de las GrageasVisuales
                float primeraPosicionX = primeraSeleccionada.getX();
                float primeraPosicionY = primeraSeleccionada.getY();
                //primeraSeleccionada.setPosition(segundaSeleccionada.getX(), segundaSeleccionada.getY());
                //segundaSeleccionada.setPosition(primeraPosicionX, primeraPosicionY);

                //Intercambia las filas y columnas de las GrageasVisuales
                matrizGrageasVisuales[primerGrageaX][primerGrageaY].setFila(segundaGrageaX);
                matrizGrageasVisuales[primerGrageaX][primerGrageaY].setColumna(segundaGrageaY);
                matrizGrageasVisuales[segundaGrageaX][segundaGrageaY].setFila(primerGrageaX);
                matrizGrageasVisuales[segundaGrageaX][segundaGrageaY].setColumna(primerGrageaY);

                hayGrageaSeleccionada = false;
                barrierRespuestaVisual.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    */
    public boolean verificarAdyacentes() {
        return (segundaGrageaX == primerGrageaX && ((segundaGrageaY == primerGrageaY - 1) || (segundaGrageaY == primerGrageaY + 1)))
                || (segundaGrageaY == primerGrageaY && ((segundaGrageaX == primerGrageaX - 1) || (segundaGrageaX == primerGrageaX + 1)));
    }

    public boolean isHayGrageaSeleccionada() {
        return hayGrageaSeleccionada;
    }

    public void setHayGrageaSeleccionada(boolean hayGrageaSeleccionada) {
        this.hayGrageaSeleccionada = hayGrageaSeleccionada;
    }

    public CyclicBarrier getBarrierRespuestaVisual() {
        return barrierRespuestaVisual;
    }

    public void setBarrierRespuestaVisual(CyclicBarrier barrierRespuestaVisual) {
        this.barrierRespuestaVisual = barrierRespuestaVisual;
    }

    public int getPrimerGrageaX() {
        return primerGrageaX;
    }

    public void setPrimerGrageaX(int primerGrageaX) {
        this.primerGrageaX = primerGrageaX;
    }

    public int getPrimerGrageaY() {
        return primerGrageaY;
    }

    public void setPrimerGrageaY(int primerGrageaY) {
        this.primerGrageaY = primerGrageaY;
    }

    public int getSegundaGrageaX() {
        return segundaGrageaX;
    }

    public void setSegundaGrageaX(int segundaGrageaX) {
        this.segundaGrageaX = segundaGrageaX;
    }

    public int getSegundaGrageaY() {
        return segundaGrageaY;
    }

    public void setSegundaGrageaY(int segundaGrageaY) {
        this.segundaGrageaY = segundaGrageaY;
    }

    public boolean isInputHabilitado() {
        return inputHabilitado;
    }

    public void setInputHabilitado(boolean inputHabilitado) {
        this.inputHabilitado = inputHabilitado;
    }

    public GrageaVisual[][] getMatrizGrageasVisuales() {
        return matrizGrageasVisuales;
    }

    public void setMatrizGrageasVisuales(GrageaVisual[][] matrizGrageasVisuales) {
        this.matrizGrageasVisuales = matrizGrageasVisuales;
    }
}
