package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.estructuras.Point;
import org.lab.grageasmagicas.parte_logica.Gragea;
import org.lab.grageasmagicas.parte_logica.Juego;
import org.lab.grageasmagicas.parte_logica.JuegoLogico2;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;


public class JuegoVisual implements Screen, Observer {
    //juego visual
    private boolean hayGrageaSeleccionada;
    private int primerGrageaX;
    private int primerGrageaY;
    private int segundaGrageaX;
    private int segundaGrageaY;
    private int anchoCamara;
    private int altoCamara;
    private int animacionesEjecutando;
    private boolean inputGrageas;
    private boolean inputMenus;
    private boolean tableroListo;
    private boolean drawParEfcBrillante;
    private GrageaVisual[][] matrizGrageasVisuales;
    private CyclicBarrier barrierRespuestaVisual;
    private float[][] matrizPosGrageaX;
    private float[][] matrizPosGrageaY;
    //juego logico
    private Juego juegoLogico;
    private Gragea[][] matrizGrageasLogica;
    private CopyOnWriteArrayList<Point> grageasCombinadas;
    private int cantColumnas;
    private int cantFilas;
    //administradores
    private AdministradorPantalla adminPantalla;
    private AssetManager assetManager;
    private Viewport vista;
    private Stage escena;
    private SpriteBatch batch;
    private I18NBundle strings;
    //actors
    private Table tblTablero;
    private TextButton btnPuntaje;
    private TextButton btnPuntajeGanar;
    private TextButton btnVolver;
    private TextButton btnSinMovimiento;
    private TextButton btnMovimientos;
    private ImageTextButton btnFinJuego;
    private ImageButton btnMusica;
    private Image imgFondo;
    //assets
    private Texture txtFondo;
    private Texture txtGragea;
    private Texture txtBtnMusicaOn;
    private Texture txtBtnMusicaOff;
    private Texture txtBtnMusicaClick;
    private Texture txtFinJuegoFondo;
    private BitmapFont fntFuenteBase;
    private Music mscMusicaFondo;
    private Sound sndExplosion;
    private ParticleEffect parEfcExplosion;
    private ParticleEffect parEfcBrillante;
    //efectos
    private ParticleEffectPool parEfcPoolExplosion;
    private Array<ParticleEffectPool.PooledEffect> actEfcExplosion;
    private ParticleEffectPool.PooledEffect poolEfcExplosion;

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
        this.drawParEfcBrillante = false;

        cargarAssets();

        mscMusicaFondo.setLooping(true);
        mscMusicaFondo.setVolume(0.25f);
        mscMusicaFondo.play();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);

        batch = new SpriteBatch();
        parEfcPoolExplosion = new ParticleEffectPool(parEfcExplosion, 25, 100);
        actEfcExplosion = new Array<ParticleEffectPool.PooledEffect>();
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

            //actualiza el btnPuntaje segun el obtenido del juego logico
            btnPuntaje.setText(juegoLogico.getPuntaje() + "");
            btnPuntaje.setWidth(btnPuntaje.getPrefWidth());
            btnPuntaje.setHeight(btnPuntaje.getPrefHeight());
            btnPuntaje.setPosition(50, altoCamara - btnPuntaje.getHeight() - 25);

            //actualiza el btnMovimientos segun el obtenido del juego logico
            btnMovimientos.setText(juegoLogico.getMovimientos() + " / " + juegoLogico.getMovimientosTotales());
            btnMovimientos.setWidth(btnMovimientos.getPrefWidth());
            btnMovimientos.setHeight(btnMovimientos.getPrefHeight());
            btnMovimientos.setPosition(50, altoCamara - btnMovimientos.getHeight() - btnPuntaje.getHeight() - 25);

            if (juegoLogico.getPuntaje() >= juegoLogico.getPuntajeGanar()) {
                parEfcBrillante.setPosition(
                        btnPuntajeGanar.getX() + btnPuntajeGanar.getWidth() / 2,
                        btnPuntajeGanar.getY() + btnPuntajeGanar.getHeight() / 2);
                parEfcBrillante.start();
                drawParEfcBrillante = true;
            }

            //intercambia las grageas cuando se realizo un movimiento
            intercambiarGrageas();

            //verifica que grageas fueron eliminadas y las reemplaza por las nuevas grageas
            //aleatorias que se generaron
            crearNuevasGrageas();

            //verifica si ocurrieron combinaciones e intercambia aquellas grageas que se van a
            //eliminar con sus superiores y luego las oculta para que puedan ser reemplazadas por
            //las nuevas grageas aleatorias
            eliminarGrageas();

            //este metodo se ejecuta luego de realizar todas las animaciones para asegurar que no
            //se rompa la consistencia grafica del juego
            corregirPosiciones();

            //verificar si quedan movimientos posibles
            verificarMovimientoPosible();

            if (juegoLogico.isFinJuego()) {
                mostrarResultado();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Crea la matriz visual, la estrutura de tabla, y el resto de los elementos visuales
     */
    public void inicializar() {
        matrizGrageasVisuales = new GrageaVisual[cantFilas][cantColumnas];

        imgFondo = new Image(txtFondo);
        imgFondo.setScale(anchoCamara / imgFondo.getWidth(), altoCamara / imgFondo.getHeight());
        escena.addActor(imgFondo);

        tblTablero = new Table();
        escena.addActor(tblTablero);

        TextButton.TextButtonStyle btnStlVolver = new TextButton.TextButtonStyle();
        btnStlVolver.font = fntFuenteBase;
        btnVolver = new TextButton(strings.get("btn_volver"), btnStlVolver);
        btnVolver.getLabel().setFontScale(2, 2);
        btnVolver.setWidth(btnVolver.getPrefWidth());
        btnVolver.setHeight(btnVolver.getPrefHeight());
        btnVolver.setPosition(anchoCamara - btnVolver.getWidth() - 50, altoCamara - btnVolver.getHeight() - 25);
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (isInputMenus()) {

                        juegoLogico.terminar();

                        barrierRespuestaVisual.await();

                        adminPantalla.setScreen(new MenuPrincipal(adminPantalla));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        escena.addActor(btnVolver);

        TextureRegionDrawable trBtnMusicaOn = new TextureRegionDrawable(new TextureRegion(txtBtnMusicaOn));
        TextureRegionDrawable trBtnMusicaOff = new TextureRegionDrawable(new TextureRegion(txtBtnMusicaOff));
        TextureRegionDrawable trBtnMusicaClick = new TextureRegionDrawable(new TextureRegion(txtBtnMusicaClick));
        btnMusica = new ImageButton(trBtnMusicaOn, trBtnMusicaClick, trBtnMusicaOff);
        btnMusica.setDisabled(true);
        btnMusica.setPosition(anchoCamara - btnMusica.getWidth() - 75, altoCamara - btnMusica.getHeight() - btnVolver.getHeight() - 50);
        btnMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isInputMenus()) {
                    btnMusica.setDisabled(false);
                    if (mscMusicaFondo.isPlaying()) {
                        mscMusicaFondo.pause();
                        btnMusica.setChecked(true);
                    } else {
                        mscMusicaFondo.play();
                        btnMusica.setChecked(false);
                    }
                    btnMusica.setDisabled(true);
                }
            }
        });
        escena.addActor(btnMusica);

        TextButton.TextButtonStyle btnStlSinMoviminto = new TextButton.TextButtonStyle();
        btnStlSinMoviminto.font = fntFuenteBase;
        btnStlSinMoviminto.fontColor = Color.RED;
        btnSinMovimiento = new TextButton(strings.get("btn_sin_movimientos"), btnStlSinMoviminto);
        btnSinMovimiento.setPosition(anchoCamara / 2 - btnSinMovimiento.getWidth() / 2, altoCamara - btnSinMovimiento.getHeight() - 25);
        btnSinMovimiento.setVisible(false);
        btnSinMovimiento.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    btnSinMovimiento.setVisible(false);
                    barrierRespuestaVisual.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        escena.addActor(btnSinMovimiento);

        TextureRegionDrawable trBtnFinJuegoFondo = new TextureRegionDrawable(new TextureRegion(txtFinJuegoFondo));
        ImageTextButton.ImageTextButtonStyle btnStlFinJuego = new ImageTextButton.ImageTextButtonStyle(
                trBtnFinJuegoFondo, trBtnFinJuegoFondo, trBtnFinJuegoFondo, fntFuenteBase);
        btnStlFinJuego.font = fntFuenteBase;
        btnStlFinJuego.fontColor = Color.GOLD;
        btnFinJuego = new ImageTextButton(strings.get("btn_fin_juego"), btnStlFinJuego);
        btnFinJuego.getLabel().setFontScale(3, 3);
        btnFinJuego.setWidth(btnFinJuego.getPrefWidth());
        btnFinJuego.setHeight(btnFinJuego.getPrefHeight());
        btnFinJuego.setPosition(anchoCamara / 2 - btnFinJuego.getWidth() / 2, altoCamara / 2 - btnFinJuego.getHeight() / 2);
        btnFinJuego.setVisible(false);
        btnFinJuego.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    //if (isInputMenus()) {
                    juegoLogico.terminar();

                    barrierRespuestaVisual.await();

                    adminPantalla.setScreen(new MenuNiveles(adminPantalla));
                    //}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        escena.addActor(btnFinJuego);

        TextButton.TextButtonStyle btnStlPuntaje = new TextButton.TextButtonStyle();
        btnStlPuntaje.font = fntFuenteBase;
        btnStlPuntaje.fontColor = Color.GOLD;
        btnPuntaje = new TextButton(juegoLogico.getPuntaje() + "", btnStlPuntaje);
        btnPuntaje.getLabel().setFontScale(2, 2);
        btnPuntaje.setWidth(btnPuntaje.getPrefWidth());
        btnPuntaje.setHeight(btnPuntaje.getPrefHeight());
        btnPuntaje.setPosition(50, altoCamara - btnPuntaje.getHeight() - 25);
        escena.addActor(btnPuntaje);

        TextButton.TextButtonStyle btnStlPuntajeGanar = new TextButton.TextButtonStyle();
        btnStlPuntajeGanar.font = fntFuenteBase;
        btnStlPuntajeGanar.fontColor = Color.GOLD;
        btnPuntajeGanar = new TextButton(juegoLogico.getPuntajeGanar() + "", btnStlPuntajeGanar);
        btnPuntajeGanar.getLabel().setFontScale(2, 2);
        btnPuntajeGanar.setWidth(btnPuntajeGanar.getPrefWidth());
        btnPuntajeGanar.setHeight(btnPuntajeGanar.getPrefHeight());
        btnPuntajeGanar.setPosition(anchoCamara / 2 - btnPuntajeGanar.getWidth() / 2, 25);
        escena.addActor(btnPuntajeGanar);

        TextButton.TextButtonStyle btnStlMovimientos = new TextButton.TextButtonStyle();
        btnStlMovimientos.font = fntFuenteBase;
        btnStlMovimientos.fontColor = Color.BLUE;
        btnMovimientos = new TextButton(juegoLogico.getMovimientos() + " / " + juegoLogico.getMovimientosTotales(), btnStlMovimientos);
        btnMovimientos.getLabel().setFontScale(2, 2);
        btnMovimientos.setWidth(btnMovimientos.getPrefWidth());
        btnMovimientos.setHeight(btnMovimientos.getPrefHeight());
        btnMovimientos.setPosition(50, altoCamara - btnMovimientos.getHeight() - btnPuntaje.getHeight() - 25);
        escena.addActor(btnMovimientos);

        tblTablero.row();
        for (int i = 0; i < cantFilas; i++) {
            for (int j = 0; j < cantColumnas; j++) {
                matrizGrageasVisuales[i][j] = new GrageaVisual(matrizGrageasLogica[i][j].getTipo(), txtGragea);
                matrizGrageasVisuales[i][j].addListener(new GrageaVisualListener(matrizGrageasVisuales[i][j], this, i, j));
                tblTablero.add(matrizGrageasVisuales[i][j]);
            }
            tblTablero.row();
        }
        tblTablero.padBottom(5f);
        tblTablero.setFillParent(true);
        tblTablero.pack();

        matrizPosGrageaX = new float[cantFilas][cantColumnas];
        matrizPosGrageaY = new float[cantFilas][cantColumnas];
        for (int i = 0; i < cantFilas; i++) {
            for (int j = 0; j < cantColumnas; j++) {
                matrizPosGrageaX[i][j] = matrizGrageasVisuales[i][j].getX();
                matrizPosGrageaY[i][j] = matrizGrageasVisuales[i][j].getY();
            }
        }

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
            if (animacionesEjecutando > 0) {
                dormir();
                //sleep(500);
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
            if (animacionesEjecutando > 0) {
                dormir();
                //sleep(500);
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
                        poolEfcExplosion = parEfcPoolExplosion.obtain();
                        poolEfcExplosion.setPosition(
                                matrizGrageasVisuales[i][j].getX() + matrizGrageasVisuales[i][j].getWidth() / 2,
                                matrizGrageasVisuales[i][j].getY() + matrizGrageasVisuales[i][j].getHeight() / 2);
                        actEfcExplosion.add(poolEfcExplosion);
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
                if (juegoLogico.getHuboCombo()) {
                    Gdx.input.vibrate(200);
                    juegoLogico.setHuboCombo(false);
                }
            }
            sndExplosion.play();
            if (animacionesEjecutando > 0) {
                dormir();
                //sleep(500);
            }
        }
    }


    /**
     * Corrige las posiciones de las grageas visuales segun la matriz de posiciones inicial
     */
    public void corregirPosiciones() {
        for (int i = 0; i < cantFilas; i++) {
            for (int j = 0; j < cantColumnas; j++) {
                matrizGrageasVisuales[i][j].setPosition(matrizPosGrageaX[i][j], matrizPosGrageaY[i][j]);
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
            btnSinMovimiento.setVisible(true);
        } else {
            btnSinMovimiento.setVisible(false);
        }
    }

    /**
     *
     */
    public void mostrarResultado() {
        if (juegoLogico.getPuntaje() < juegoLogico.getPuntajeGanar()) {
            btnFinJuego.setText(strings.get("btn_fin_derrota"));
            btnFinJuego.getLabel().setColor(Color.RED);
        } else {
            btnFinJuego.setText(strings.get("btn_fin_victoria"));
            btnFinJuego.getLabel().setColor(Color.GREEN);
            if (adminPantalla.isSesion()) {
                adminPantalla.getInterfazDb().insertarPuntaje(adminPantalla.getIdUsuario(), juegoLogico.getPuntaje());
                adminPantalla.getInterfazDb().desbloquearNivel(adminPantalla.getIdUsuario(), juegoLogico.getNivel());
            }
        }
        btnFinJuego.setVisible(true);
        btnPuntaje.getLabel().setFontScale(4, 4);
        btnPuntaje.setWidth(btnPuntaje.getPrefWidth());
        btnPuntaje.setHeight(btnPuntaje.getPrefHeight());
        btnPuntaje.addAction(new AnimacionMover(anchoCamara / 2 - btnPuntaje.getWidth() / 2,
                altoCamara - btnPuntaje.getHeight() - 50, 0.5f, Interpolation.bounceOut, this));
    }

    /**
     * Permite sincronizar las animaciones con el juegoVisual, haciendo que la ultima animacion en
     * terminar lo despierte
     */
    synchronized public void animacionTermina() {
        animacionesEjecutando--;
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

            //dibuja los efectos de particulas
            batch.begin();
            float deltaTime = Gdx.graphics.getDeltaTime();
            batch.setProjectionMatrix(adminPantalla.getCamara().combined);
            for (int i = 0; i < actEfcExplosion.size; ) {
                ParticleEffectPool.PooledEffect effect = actEfcExplosion.get(i);
                if (effect.isComplete()) {
                    parEfcPoolExplosion.free(effect);
                    actEfcExplosion.removeIndex(i);
                } else {
                    effect.draw(batch, deltaTime);
                    ++i;
                }
            }

            if (drawParEfcBrillante) {
                parEfcBrillante.draw(batch, deltaTime);
                if (parEfcBrillante.isComplete()) {
                    parEfcBrillante.reset();
                }
            }

            batch.end();
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
        txtFondo.dispose();
        txtGragea.dispose();
        txtBtnMusicaOn.dispose();
        txtBtnMusicaOff.dispose();
        txtBtnMusicaClick.dispose();
        txtFinJuegoFondo.dispose();
        fntFuenteBase.dispose();
        mscMusicaFondo.dispose();
        sndExplosion.dispose();
        parEfcExplosion.dispose();
        parEfcBrillante.dispose();
        escena.dispose();
        //assetManager.clear();
        assetManager.unload("imagenes/fondo_juego.png");
        assetManager.unload("imagenes/gragea.png");
        assetManager.unload("imagenes/musica_on.png");
        assetManager.unload("imagenes/musica_off.png");
        assetManager.unload("imagenes/musica_click.png");
        assetManager.unload("imagenes/fin_btn_fondo.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("sonidos/musica_fondo.mp3");
        assetManager.unload("sonidos/explosion.mp3");
        assetManager.unload("efectos/explosion.effect");
        assetManager.unload("efectos/brillante.effect");
        assetManager.unload("strings/strings");
    }

    public void cargarAssets() {
        //loader para efectos de particulas
        ParticleEffectLoader.ParticleEffectParameter effectParameter = new ParticleEffectLoader.ParticleEffectParameter();
        effectParameter.imagesDir = Gdx.files.internal("imagenes");

        assetManager.load("imagenes/fondo_juego.png", Texture.class);
        assetManager.load("imagenes/gragea.png", Texture.class);
        assetManager.load("imagenes/musica_on.png", Texture.class);
        assetManager.load("imagenes/musica_off.png", Texture.class);
        assetManager.load("imagenes/musica_click.png", Texture.class);
        assetManager.load("imagenes/fin_btn_fondo.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("sonidos/musica_fondo.mp3", Music.class);
        assetManager.load("sonidos/explosion.mp3", Sound.class);
        assetManager.load("efectos/explosion.effect", ParticleEffect.class, effectParameter);
        assetManager.load("efectos/brillante.effect", ParticleEffect.class, effectParameter);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondo_juego.png");
        txtGragea = assetManager.get("imagenes/gragea.png");
        txtBtnMusicaOn = assetManager.get("imagenes/musica_on.png");
        txtBtnMusicaOff = assetManager.get("imagenes/musica_off.png");
        txtBtnMusicaClick = assetManager.get("imagenes/musica_click.png");
        txtFinJuegoFondo = assetManager.get("imagenes/fin_btn_fondo.png");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        mscMusicaFondo = assetManager.get("sonidos/musica_fondo.mp3");
        sndExplosion = assetManager.get("sonidos/explosion.mp3");
        parEfcExplosion = assetManager.get("efectos/explosion.effect");
        parEfcBrillante = assetManager.get("efectos/brillante.effect");
        strings = assetManager.get("strings/strings");
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
