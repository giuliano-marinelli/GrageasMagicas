package org.lab.grageasmagicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PrincipalVisual extends ScreenAdapter {

    private static final float ancho = Gdx.graphics.getWidth();
    private static final float alto = Gdx.graphics.getHeight();

    private Stage escena;
    private Texture texturaFondo;
    private Texture texturaBtnJugar;
    private Texture texturaBtnOpciones;
    private Texture texturaBtnAcercaDe;
    private Texture texturaTitulo;
    private final AssetManager assetManager = new AssetManager();
    private static Juego juegoLogico;

    private final GrageasMagicasGame juego;

    public PrincipalVisual(GrageasMagicasGame grageasmagicasjuego) {
        juego = grageasmagicasjuego;
    }

    @Override
    public void show() {
        cargarTexturas();
        escena = new Stage(new FitViewport(ancho, alto));
        Gdx.input.setInputProcessor(escena);

        Image imgFondo = new Image(texturaFondo);
        float escalaX = ancho / imgFondo.getWidth();
        float escalaY = alto / imgFondo.getHeight();
        imgFondo.setScale(escalaX, escalaY);

        escena.addActor(imgFondo);

        TextureRegionDrawable trBtnJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
        ImageButton btnJugar = new ImageButton(trBtnJugar);
        btnJugar.setPosition(ancho / 2, alto * 0.6f);
        escena.addActor(btnJugar);

        TextureRegionDrawable trBtnOpciones = new TextureRegionDrawable(new TextureRegion(texturaBtnOpciones));
        ImageButton btnOpciones = new ImageButton(trBtnOpciones);
        btnOpciones.setPosition(ancho / 2, alto * 0.4f);
        escena.addActor(btnOpciones);

        TextureRegionDrawable trBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(texturaBtnAcercaDe));
        ImageButton btnAcercaDe = new ImageButton(trBtnAcercaDe);
        btnAcercaDe.setPosition(ancho / 2, alto * 0.2f);
        escena.addActor(btnAcercaDe);

        Image imgTitulo = new Image(texturaTitulo);
        imgTitulo.setPosition(ancho / 2 - imgTitulo.getWidth() / 2, alto * 0.8f);

        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Presiono jugar");
                int ancho = 5;
                int alto = 5;
                int cantGragea = 5;
                int velocidad = 10;

                GrageaVisual[][] matrizgrageasvisuales = new GrageaVisual[5][5];
                juegoLogico = new Juego(ancho, alto, velocidad, cantGragea);
                Thread juegoThread = new Thread(juegoLogico);
                juegoThread.start();

                Gragea[][] matrizGrageas = juegoLogico.getMatrizGrageas();

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        matrizgrageasvisuales[i][j] = new GrageaVisual(matrizGrageas[i][j].getTipo(), i, j);
                    }
                }

                TableroVisual tablero = new TableroVisual(matrizgrageasvisuales);
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        matrizgrageasvisuales[i][j].getBoton().addListener(
                                new GrageaVisualListener(matrizgrageasvisuales[i][j], matrizGrageas[i][j], tablero));
                    }
                }

                dispose();
                juego.borrarPantalla();
                juego.setScreen(new TableroVisual(matrizgrageasvisuales));
            }
        });

        btnOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Presiono opciones");
            }
        });

        btnAcercaDe.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Presiono acerca de");
            }
        });

        escena.addActor(imgTitulo);
    }

    public void resize(int width, int height) {
        escena.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        escena.act(delta);
        escena.draw();
    }

    public void dispose() {
        escena.dispose();

    }


    private void cargarTexturas() {
        assetManager.load("fondo.jpg", Texture.class);
        assetManager.load("jugar.png", Texture.class);
        assetManager.load("opciones.png", Texture.class);
        assetManager.load("acerca_de.png", Texture.class);
        assetManager.load("titulo.png", Texture.class);

        assetManager.finishLoading();

        texturaFondo = assetManager.get("fondo.jpg");
        texturaBtnJugar = assetManager.get("jugar.png");
        texturaBtnOpciones = assetManager.get("opciones.png");
        texturaBtnAcercaDe = assetManager.get("acerca_de.png");
        texturaTitulo = assetManager.get("titulo.png");
    }
}
