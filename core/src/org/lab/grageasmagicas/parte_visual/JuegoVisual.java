package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;


public class JuegoVisual implements Screen, Observer {
    //juego visual
    private boolean hayGrageaSeleccionada;
    private int primerGrageaX;
    private int primerGrageaY;
    private int segundaGrageaX;
    private int segundaGrageaY;
    private int anchoCamara;
    private int altoCamara;
    private boolean inputGrageas;
    private boolean inputMenus;
    private GrageaVisual[][] matrizGrageasVisuales;
    private Juego juegoLogico;
    private CyclicBarrier barrierRespuestaVisual;
    private int animacionesEjecutando;
    //juego logico
    private Gragea[][] matrizGrageasLogica;
    private CopyOnWriteArrayList<Point> grageasCombinadas;
    private int cantColumnas;
    private int cantFilas;
    //administradores
    private AdministradorPantalla adminPantalla;
    private AssetManager assetManager;
    private Viewport vista;
    private Stage escena;
    private boolean tableroListo;
    //actors
    private Table tblTablero;
    private TextButton puntaje;
    private TextButton volver;
    private TextButton sinMovimiento;
    private TextButton musicaEncendida;
    private TextButton.TextButtonStyle puntajeStyle;
    private TextButton.TextButtonStyle volverStyle;
    private TextButton.TextButtonStyle sinMovimintoStyle;
    //assets
    private Texture texturaFondo;
    private Texture texturaGragea;
    private BitmapFont fuenteBase;
    private Music sndMusicaFondo;

    public JuegoVisual(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();
        this.inputGrageas = false;
        this.inputMenus = false;
        this.primerGrageaX = -1;
        this.primerGrageaY = -1;
        this.segundaGrageaX = -1;
        this.segundaGrageaY = -1;
        this.animacionesEjecutando = 0;
        this.tableroListo = false;

        cargarAssets();

        sndMusicaFondo.setLooping(true);
        sndMusicaFondo.setVolume(0.25f);
        sndMusicaFondo.play();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void show() {
        //implementado en update
    }

    public void gameLoop() {
        try {
            //crea la matriz visual y la estrutura de tabla si no fue creada aun
            if (!tableroListo) {
                inicializar();
            }

            //actualiza el puntaje segun el obtenido del juego logico
            puntaje.setText((int) juegoLogico.getPuntaje() + "");

            //intercambia las grageas cuando se realizo un movimiento
            intercambiarGrageas();

            //verifica que grageas fueron eliminadas y las reemplaza por las nuevas grageas
            //aleatorias que se generaron
            crearNuevasGrageas();

            //verifica si ocurrieron combinaciones e intercambia aquellas grageas que se van a
            //eliminar con sus superiores y luego las oculta para que puedan ser reemplazadas por
            //las nuevas grageas aleatorias
            eliminarGrageas();

            //verificar si quedan movimientos posibles
            verificarMovimientoPosible();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Crea la matriz visual, la estrutura de tabla, y el resto de los elementos visuales
     */
    public void inicializar() {
        matrizGrageasVisuales = new GrageaVisual[cantFilas][cantColumnas];

        tblTablero = new Table();
        tblTablero.background(new TextureRegionDrawable(new TextureRegion(texturaFondo)));
        //tblTablero.setColor(Color.GOLD);
        escena.addActor(tblTablero);

        puntajeStyle = new TextButton.TextButtonStyle();
        puntajeStyle.font = fuenteBase;
        puntajeStyle.fontColor = Color.GOLD;
        puntaje = new TextButton((int) juegoLogico.getPuntaje() + "", puntajeStyle);
        puntaje.setPosition(50, altoCamara - 50);
        escena.addActor(puntaje);

        volverStyle = new TextButton.TextButtonStyle();
        volverStyle.font = fuenteBase;
        volver = new TextButton("VOLVER", volverStyle);
        volver.setPosition(anchoCamara - volver.getWidth() - 50, altoCamara - 50);
        volver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (isInputMenus()) {
                        MenuPrincipal menuPrincipal = new MenuPrincipal(adminPantalla);
                        juegoLogico.terminar();

                        barrierRespuestaVisual.await();

                        //dispose();
                        adminPantalla.setScreen(menuPrincipal);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        escena.addActor(volver);

        sinMovimintoStyle = new TextButton.TextButtonStyle();
        sinMovimintoStyle.font = fuenteBase;
        sinMovimintoStyle.fontColor = Color.RED;
        sinMovimiento = new TextButton("NO HAY MOVIMIENTOS", sinMovimintoStyle);
        sinMovimiento.setPosition(anchoCamara / 2 - sinMovimiento.getWidth() / 2, altoCamara - 50);
        sinMovimiento.setVisible(false);
        sinMovimiento.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    sinMovimiento.setVisible(false);
                    barrierRespuestaVisual.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        escena.addActor(sinMovimiento);

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
        tableroListo = true;
    }

    /**
     * Intercambia grageas graficamente cuando se realizo un movimiento por parte del usuario
     *
     * @throws InterruptedException
     */
    public void intercambiarGrageas() throws InterruptedException {
        //solo actua cuando las posiciones ingresadas de las grageas son validas
        if (juegoLogico.getPrimerGrageaX() != -1) {
            GrageaVisual priGragea = matrizGrageasVisuales[juegoLogico.getPrimerGrageaX()][juegoLogico.getPrimerGrageaY()];
            GrageaVisual segGragea = matrizGrageasVisuales[juegoLogico.getSegundaGrageaX()][juegoLogico.getSegundaGrageaY()];
            GrageaVisualListener priGrageaListener = (GrageaVisualListener) (priGragea.getListeners().get(0));
            GrageaVisualListener segGrageaListener = (GrageaVisualListener) (segGragea.getListeners().get(0));
                /*priGragea.addAction(Actions.moveTo
                        (segGragea.getX(), segGragea.getY(), 0.5f, Interpolation.bounceOut));
                segGragea.addAction(Actions.moveTo
                        (priGragea.getX(), priGragea.getY(), 0.5f, Interpolation.bounceOut));*/
            priGragea.addAction(new AnimacionMover
                    (segGragea.getX(), segGragea.getY(), 0.5f, Interpolation.bounceOut, this));
            animacionesEjecutando++;
            segGragea.addAction(new AnimacionMover
                    (priGragea.getX(), priGragea.getY(), 0.5f, Interpolation.bounceOut, this));
            animacionesEjecutando++;
            priGrageaListener.setFilaColumnaGragea(juegoLogico.getSegundaGrageaX(), juegoLogico.getSegundaGrageaY());
            segGrageaListener.setFilaColumnaGragea(juegoLogico.getPrimerGrageaX(), juegoLogico.getPrimerGrageaY());
            matrizGrageasVisuales[juegoLogico.getPrimerGrageaX()][juegoLogico.getPrimerGrageaY()] = segGragea;
            matrizGrageasVisuales[juegoLogico.getSegundaGrageaX()][juegoLogico.getSegundaGrageaY()] = priGragea;
            //sleep(2000);
            if (animacionesEjecutando > 0) {
                dormir();
            }
        }
    }

    /**
     * Verifica que grageas fueron eliminadas a traves de la propiedad "visible" y las reemplaza por
     * las nuevas grageas aleatorias que se generaron
     *
     * @throws InterruptedException
     */
    public void crearNuevasGrageas() throws InterruptedException {
        boolean hayNuevas = false;
        float posXNuevaGrageaVisual;
        float posYNuevaGrageaVisual;
        for (int i = 0; i < cantFilas; i++) {
            for (int j = 0; j < cantColumnas; j++) {
                if (matrizGrageasVisuales[i][j] != null) {
                    if (!matrizGrageasVisuales[i][j].isVisible()) {
                        hayNuevas = true;
                        posXNuevaGrageaVisual = matrizGrageasVisuales[i][j].getX();
                        posYNuevaGrageaVisual = matrizGrageasVisuales[i][j].getY();
                        matrizGrageasVisuales[i][j].setTipo(matrizGrageasLogica[i][j].getTipo());
                        GrageaVisualListener grageaListener = (GrageaVisualListener) (matrizGrageasVisuales[i][j].getListeners().get(0));
                        grageaListener.setFilaColumnaGragea(i, j);
                        matrizGrageasVisuales[i][j].setPosition(posXNuevaGrageaVisual, altoCamara);
                        matrizGrageasVisuales[i][j].setVisible(true);
                            /*matrizGrageasVisuales[i][j].addAction(Actions.moveTo
                                    (posXNuevaGrageaVisual, posYNuevaGrageaVisual, 0.5f, Interpolation.bounceOut));*/
                        matrizGrageasVisuales[i][j].addAction(new AnimacionMover
                                (posXNuevaGrageaVisual, posYNuevaGrageaVisual, 0.5f, Interpolation.bounceOut, this));
                        animacionesEjecutando++;
                        //Sound sGrageasNuevas = Gdx.audio.newSound(Gdx.files.internal("grageasNuevas.mp3"));
                    }
                }
            }
        }

        if (hayNuevas) {
            //sleep(2000);
            if (animacionesEjecutando > 0) {
                dormir();
            }
        }
    }

    /**
     * Verifica si ocurrieron combinaciones e intercambia aquellas grageas que se van a
     * eliminar con sus superiores y luego las oculta para que puedan ser reemplazadas por
     * las nuevas grageas aleatorias
     *
     * @throws InterruptedException
     */
    public void eliminarGrageas() throws InterruptedException {
        //solo actua cuando se encuentran elementos en el arreglo de grageasCombinadas
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
                /*Collections.sort(combinacionTemp);
                HashSet hs = new HashSet();
                hs.addAll(combinacionTemp);
                combinacionTemp.clear();
                combinacionTemp.addAll(hs);*/
                int bajar = 0;
                for (int i = cantFilas - 1; i >= 0; i--) {
                    if (combinacionTemp.contains(i)) {
                        matrizGrageasVisuales[i][j].setVisible(false);
                        bajar++;
                    } else {
                        if (bajar != 0) {
                            posXAnt = matrizGrageasVisuales[i][j].getX();
                            posYAnt = matrizGrageasVisuales[i][j].getY();
                                /*matrizGrageasVisuales[i][j].addAction(Actions.moveTo
                                        (matrizGrageasVisuales[i + bajar][j].getX(), matrizGrageasVisuales[i + bajar][j].getY(),
                                                0.5f, Interpolation.bounceOut));*/
                            matrizGrageasVisuales[i][j].addAction(new AnimacionMover(
                                    matrizGrageasVisuales[i + bajar][j].getX(), matrizGrageasVisuales[i + bajar][j].getY(),
                                    0.5f, Interpolation.bounceOut, this));
                            animacionesEjecutando++;
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
            }
            //Sound sCombinacion = Gdx.audio.newSound(Gdx.files.internal("combinacion.mp3"));
            //sleep(2000);
            if (animacionesEjecutando > 0) {
                dormir();
            }
        }
    }

    /**
     * Verifica si quedan movimientos posibles y de no ser asi muestra el boton para para mezclar
     * nuevamente las grageas
     */
    public void verificarMovimientoPosible() {
        if (!juegoLogico.isHayJugadas()) {
            inputGrageas = false;
            sinMovimiento.setVisible(true);
        } else {
            sinMovimiento.setVisible(false);
        }
    }

    /**
     * Permite sincronizar las animaciones con el juegoVisual, haciendo que la ultima animacion en
     * terminar lo despierte
     */
    synchronized public void animacionTermina() {
        animacionesEjecutando--;
        System.out.println(animacionesEjecutando);
        if (animacionesEjecutando == 0) {
            notify();
        }
    }

    /**
     * Duerme al juegoVisual, dejandolo a la espera de un notify
     *
     * @throws InterruptedException
     */
    synchronized public void dormir() throws InterruptedException {
        wait();
    }

    /**
     * Re-dibuja el juego a 30fps (default), se altera los elementos graficos de la "escena" y luego
     * son dibujados en el render
     *
     * @param delta
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (tableroListo) {
            escena.act(delta);
            escena.setViewport(vista);
            escena.draw();
        }
    }

    /**
     * Obtiene los cambios del juegoLogico y actualiza las estructuras locales para luego reflejar
     * los cambios visualmente a traves del gameLoop
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        synchronized (this) {
            if (assetManager.update()) {
                //seteamos los nuevos datos obtenidos desde el juego logico observado
                juegoLogico = (Juego) observable;
                matrizGrageasLogica = juegoLogico.getMatrizGrageas();
                grageasCombinadas = juegoLogico.getGrageasCombinadas();
                cantColumnas = matrizGrageasLogica[0].length;
                cantFilas = matrizGrageasLogica.length;

                //realizamos los cambios en el juego a partir de lo obtenido
                gameLoop();
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaGragea.dispose();
        fuenteBase.dispose();
        sndMusicaFondo.dispose();
        escena.dispose();
        assetManager.unload("imagenes/fondogolosinas.png");
        assetManager.unload("imagenes/gragea.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("sonidos/musica_fondo.mp3");
    }

    public void cargarAssets() {
        assetManager.load("imagenes/fondogolosinas.png", Texture.class);
        assetManager.load("imagenes/gragea.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("sonidos/musica_fondo.mp3", Music.class);
        assetManager.finishLoading();
        texturaFondo = assetManager.get("imagenes/fondogolosinas.png");
        texturaGragea = assetManager.get("imagenes/gragea.png");
        fuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        sndMusicaFondo = assetManager.get("sonidos/musica_fondo.mp3");
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

    public boolean isInputGrageas() {
        return inputGrageas;
    }

    public void setInputGrageas(boolean inputGrageas) {
        this.inputGrageas = inputGrageas;
    }

    public GrageaVisual[][] getMatrizGrageasVisuales() {
        return matrizGrageasVisuales;
    }

    public boolean isInputMenus() {
        return inputMenus;
    }

    public void setInputMenus(boolean inputMenus) {
        this.inputMenus = inputMenus;
    }

    public int getAnimacionesEjecutando() {
        return animacionesEjecutando;
    }

    public void setAnimacionesEjecutando(int animacionesEjecutando) {
        this.animacionesEjecutando = animacionesEjecutando;
    }
}
