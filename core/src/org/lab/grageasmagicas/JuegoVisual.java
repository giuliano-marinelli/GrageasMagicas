package org.lab.grageasmagicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BrokenBarrierException;
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
    private CyclicBarrier barrierRespuestaVisual;
    private CyclicBarrier barrierSincVisual;
    private Table tblTablero;
    private AdministradorPantalla adminPantalla;
    private Viewport vista;
    private Stage escena;
    private GrageaVisual[][] matrizGrageasVisuales;
    private AssetManager assetManager;

    private Texture texturaFondo;
    private Texture texturaGragea;
    private Texture texturaGrageaSeleccionada;
    private TextureRegionDrawable trFondo;
    private Juego juegoLogico;

    public JuegoVisual(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();

        cargarTexturas();
        trFondo = new TextureRegionDrawable(new TextureRegion(texturaFondo));
        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);
    }

    public void cargarTexturas() {
        assetManager.load("fondogolosinas.png", Texture.class);
        assetManager.load("caramelo.png", Texture.class);
        assetManager.load("carameloSeleccionado.png", Texture.class);
        assetManager.finishLoading();
        texturaFondo = assetManager.get("fondogolosinas.png");
        texturaGragea = assetManager.get("caramelo.png");
        texturaGrageaSeleccionada = assetManager.get("carameloSeleccionado.png");
    }

    public void resize(int width, int height) {
        vista.update(width, height);
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
        texturaGrageaSeleccionada.dispose();
        escena.dispose();
        assetManager.unload("fondogolosinas.png");
        assetManager.unload("caramelo.png");
        assetManager.unload("carameloSeleccionado.png");
    }

    @Override
    public void show() {
        if (juegoLogico != null) {
            Gragea[][] matrizGrageasLogica = juegoLogico.getMatrizGrageas();
            int cantColumnas = matrizGrageasLogica[0].length;
            int cantFilas = matrizGrageasLogica.length;
            if (matrizGrageasVisuales == null) {
                matrizGrageasVisuales = new GrageaVisual[cantFilas][cantColumnas];
                tblTablero = new Table();
                tblTablero.background(trFondo);
            }
            for (int i = 0; i < cantFilas; i++) {
                for (int j = 0; j < cantColumnas; j++) {
                    matrizGrageasVisuales[i][j] = new GrageaVisual(matrizGrageasLogica[i][j].getTipo(), i, j, texturaGragea, texturaGrageaSeleccionada);
                    matrizGrageasVisuales[i][j].getBtnGragea().addListener(new GrageaVisualListener(matrizGrageasVisuales[i][j],
                            matrizGrageasLogica[i][j], this));
                }
            }
            tblTablero.clear();
            tblTablero.row();
            for (int i = 0; i < cantFilas; i++) {
                for (int j = 0; j < cantColumnas; j++) {
                    tblTablero.add(matrizGrageasVisuales[i][j].getBtnGragea());
                }
                tblTablero.row();
            }

            tblTablero.padBottom(5f);
            tblTablero.setFillParent(true);
            //tblTablero.pack();
            escena.addActor(tblTablero);
            //barrierSincVisual.await();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    public void intercambiarGrageas() {
        try {
            //Intercambiamos las grageas visuales en la matriz
            GrageaVisual aux = matrizGrageasVisuales[segundaGrageaX][segundaGrageaY];
            matrizGrageasVisuales[segundaGrageaX][segundaGrageaY] = matrizGrageasVisuales[primerGrageaX][primerGrageaY];
            matrizGrageasVisuales[primerGrageaX][primerGrageaY] = aux;

            matrizGrageasVisuales[primerGrageaX][primerGrageaY].deseleccionar();

            //Botones de las GrageasVisuales
            ImageButton primeraSeleccionada = matrizGrageasVisuales[primerGrageaX][primerGrageaY].getBtnGragea();
            ImageButton segundaSeleccionada = matrizGrageasVisuales[segundaGrageaX][segundaGrageaY].getBtnGragea();

            //Intercambia la posicion de los botones correspondientes a las GrageasVisuales
            float primeraPosicionX = primeraSeleccionada.getX();
            float primeraPosicionY = primeraSeleccionada.getY();
            primeraSeleccionada.setPosition(segundaSeleccionada.getX(), segundaSeleccionada.getY());
            segundaSeleccionada.setPosition(primeraPosicionX, primeraPosicionY);

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

    public CyclicBarrier getBarrierSincVisual() {
        return barrierSincVisual;
    }

    public void setBarrierSincVisual(CyclicBarrier barrierSincVisual) {
        this.barrierSincVisual = barrierSincVisual;
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

}
