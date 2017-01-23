package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.estructuras.Point;
import org.lab.estructuras.Text;
import org.lab.grageasmagicas.parte_logica.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;


public class JuegoVisual implements Screen, Observer {
    //logica
    private boolean hayGrageaSeleccionada;
    private int primerGrageaX;
    private int primerGrageaY;
    private int segundaGrageaX;
    private int segundaGrageaY;
    private int anchoCamara;
    private int altoCamara;
    private boolean inputHabilitado;
    private GrageaVisual[][] matrizGrageasVisuales;
    private Juego juegoLogico;
    private CyclicBarrier barrierRespuestaVisual;
    //administradores
    private AdministradorPantalla adminPantalla;
    private AssetManager assetManager;
    private Viewport vista;
    private Stage escena;
    //actors
    private Table tblTablero;
    private Text puntaje;
    //assets
    private Texture texturaFondo;
    private Texture texturaGragea;
    private BitmapFont fuenteBase;

    public JuegoVisual(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();
        this.inputHabilitado = false;
        this.primerGrageaX = -1;
        this.primerGrageaY = -1;
        this.segundaGrageaX = -1;
        this.segundaGrageaY = -1;

        cargarTexturas();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);
    }

    public void cargarTexturas() {
        assetManager.load("fondogolosinas.png", Texture.class);
        assetManager.load("gragea.png", Texture.class);
        assetManager.load("texto_base_fuente.fnt", BitmapFont.class);
        assetManager.finishLoading();
        texturaFondo = assetManager.get("fondogolosinas.png");
        texturaGragea = assetManager.get("gragea.png");
        fuenteBase = assetManager.get("texto_base_fuente.fnt");
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
                CopyOnWriteArrayList<Point> grageasCombinadas = juegoLogico.getGrageasCombinadas();
                int cantColumnas = matrizGrageasLogica[0].length;
                int cantFilas = matrizGrageasLogica.length;

                //crea la matriz visual y la estrutura de tabla si no fue creada aun
                if (matrizGrageasVisuales == null) {

                    matrizGrageasVisuales = new GrageaVisual[cantFilas][cantColumnas];

                    tblTablero = new Table();
                    tblTablero.background(new TextureRegionDrawable(new TextureRegion(texturaFondo)));
                    //tblTablero.setColor(Color.GOLD);
                    escena.addActor(tblTablero);

                    puntaje = new Text(fuenteBase, juegoLogico.getPuntaje() + "");
                    puntaje.setPosition(0, 0);
                    escena.addActor(puntaje);

                    tblTablero.row();
                    for (int i = 0; i < cantFilas; i++) {
                        for (int j = 0; j < cantColumnas; j++) {
                            matrizGrageasVisuales[i][j] = new GrageaVisual(matrizGrageasLogica[i][j].getTipo(), texturaGragea);
                            matrizGrageasVisuales[i][j].addListener(new GrageaVisualListener(matrizGrageasVisuales[i][j], this, i, j));
                            tblTablero.add(matrizGrageasVisuales[i][j]);
                        }
                        tblTablero.row();
                    }
                    tblTablero.padBottom(5f);
                    tblTablero.setFillParent(true);
                    tblTablero.pack();
                }

                //puntaje.setTexto(juegoLogico.getPuntaje() + "");

                //intercambia las grageas cuando se realizo un movimiento
                if (juegoLogico.getPrimerGrageaX() != -1) {
                    GrageaVisual priGragea = matrizGrageasVisuales[juegoLogico.getPrimerGrageaX()][juegoLogico.getPrimerGrageaY()];
                    GrageaVisual segGragea = matrizGrageasVisuales[juegoLogico.getSegundaGrageaX()][juegoLogico.getSegundaGrageaY()];
                    GrageaVisualListener priGrageaListener = (GrageaVisualListener) (priGragea.getListeners().get(0));
                    GrageaVisualListener segGrageaListener = (GrageaVisualListener) (segGragea.getListeners().get(0));
                    //System.out.println(priGragea.getX() + "," + priGragea.getY());
                    //System.out.println(segGragea.getX() + "," + segGragea.getY());
                    priGragea.addAction(Actions.moveTo
                            (segGragea.getX(), segGragea.getY(), 0.5f, Interpolation.bounceOut));
                    segGragea.addAction(Actions.moveTo
                            (priGragea.getX(), priGragea.getY(), 0.5f, Interpolation.bounceOut));
                    priGrageaListener.setFilaColumnaGragea(juegoLogico.getSegundaGrageaX(), juegoLogico.getSegundaGrageaY());
                    segGrageaListener.setFilaColumnaGragea(juegoLogico.getPrimerGrageaX(), juegoLogico.getPrimerGrageaY());
                    matrizGrageasVisuales[juegoLogico.getPrimerGrageaX()][juegoLogico.getPrimerGrageaY()] = segGragea;
                    matrizGrageasVisuales[juegoLogico.getSegundaGrageaX()][juegoLogico.getSegundaGrageaY()] = priGragea;
                    sleep(750);
                }

                //verifica que grageas fueron eliminadas y las reemplaza por las nuevas grageas
                //aleatorias que se generaron
                boolean hayNuevas = false;
                float posXNuevaGrageaVisual;
                float posYNuevaGrageaVisual;
                boolean fueEliminada;
                for (int i = 0; i < cantFilas; i++) {
                    for (int j = 0; j < cantColumnas; j++) {
                        if (matrizGrageasVisuales[i][j] != null) {
                            fueEliminada = !matrizGrageasVisuales[i][j].isVisible();
                            if (fueEliminada) {
                                hayNuevas = true;
                                posXNuevaGrageaVisual = matrizGrageasVisuales[i][j].getX();
                                posYNuevaGrageaVisual = matrizGrageasVisuales[i][j].getY();
                                matrizGrageasVisuales[i][j].setTipo(matrizGrageasLogica[i][j].getTipo());
                                GrageaVisualListener grageaListener = (GrageaVisualListener) (matrizGrageasVisuales[i][j].getListeners().get(0));
                                grageaListener.setFilaColumnaGragea(i, j);
                                //System.out.println("fue eliminada = " + i + "," + j + " pos =" + posXNuevaGrageaVisual + "," + posYNuevaGrageaVisual);
                                matrizGrageasVisuales[i][j].setPosition(posXNuevaGrageaVisual, altoCamara);
                                matrizGrageasVisuales[i][j].setVisible(true);
                                matrizGrageasVisuales[i][j].addAction(Actions.moveTo
                                        (posXNuevaGrageaVisual, posYNuevaGrageaVisual, 0.5f, Interpolation.bounceOut));
                            }
                        }
                    }
                }

                if (hayNuevas) {
                    sleep(1000);
                }

                //verifica si ocurrieron combinaciones e intercambia aquellas grageas que se van a
                //eliminar con sus superiores y luego las oculta para que puedan ser reemplazadas por
                //las nuevas grageas aleatorias
                if (!juegoLogico.getGrageasCombinadas().isEmpty()) {
                    GrageaVisualListener priGrageaListener;
                    GrageaVisualListener segGrageaListener;
                    float posXAnt;
                    float posYAnt;
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
                                posXAnt = matrizGrageasVisuales[i][j].getX();
                                posYAnt = matrizGrageasVisuales[i][j].getY();
                                matrizGrageasVisuales[i][j].addAction(Actions.moveTo
                                        (matrizGrageasVisuales[i + bajar][j].getX(), matrizGrageasVisuales[i + bajar][j].getY(),
                                                0.5f, Interpolation.bounceOut));
                                matrizGrageasVisuales[i + bajar][j].setPosition(posXAnt, posYAnt);
                                priGrageaListener = (GrageaVisualListener) (matrizGrageasVisuales[i][j].getListeners().get(0));
                                segGrageaListener = (GrageaVisualListener) (matrizGrageasVisuales[i + bajar][j].getListeners().get(0));
                                priGrageaListener.setFilaColumnaGragea(i + bajar, j);
                                segGrageaListener.setFilaColumnaGragea(i, j);
                                GrageaVisual aux = matrizGrageasVisuales[i][j];
                                matrizGrageasVisuales[i][j] = matrizGrageasVisuales[i + bajar][j];
                                matrizGrageasVisuales[i + bajar][j] = aux;
                            }
                        }
                    }
                    sleep(750);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (juegoLogico != null) {
            escena.act(delta);
            escena.setViewport(vista);
            escena.draw();
        }
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
        //dispose();
    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaGragea.dispose();
        escena.dispose();
        assetManager.unload("fondogolosinas.png");
        assetManager.unload("gragea.png");
    }

    public void limpiarPosGrageas() {
        primerGrageaX = -1;
        primerGrageaY = -1;
        segundaGrageaX = -1;
        segundaGrageaY = -1;
    }

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

}
