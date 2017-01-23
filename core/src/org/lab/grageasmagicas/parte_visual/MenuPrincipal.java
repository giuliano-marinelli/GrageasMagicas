package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.grageasmagicas.parte_logica.Juego;

public class MenuPrincipal implements Screen {

    private int anchoCamara;
    private int altoCamara;

    private Stage escena;
    private Texture texturaFondo;
    private Texture texturaBtnJugar;
    private Texture texturaBtnOpciones;
    private Texture texturaBtnAcercaDe;
    private Texture texturaTitulo;
    private AssetManager assetManager;
    private AdministradorPantalla adminPantalla;
    private Viewport vista;

    public MenuPrincipal(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();

        cargarTexturas();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);
    }

    @Override
    public void show() {
        Image imgFondo = new Image(texturaFondo);
        float escalaX = anchoCamara / imgFondo.getWidth();
        float escalaY = altoCamara / imgFondo.getHeight();
        imgFondo.setScale(escalaX, escalaY);

        escena.addActor(imgFondo);

        TextureRegionDrawable trBtnJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
        ImageButton btnJugar = new ImageButton(trBtnJugar);
        btnJugar.setPosition(anchoCamara / 2, altoCamara * 0.6f);
        escena.addActor(btnJugar);

        TextureRegionDrawable trBtnOpciones = new TextureRegionDrawable(new TextureRegion(texturaBtnOpciones));
        ImageButton btnOpciones = new ImageButton(trBtnOpciones);
        btnOpciones.setPosition(anchoCamara / 2, altoCamara * 0.4f);
        escena.addActor(btnOpciones);

        TextureRegionDrawable trBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(texturaBtnAcercaDe));
        ImageButton btnAcercaDe = new ImageButton(trBtnAcercaDe);
        btnAcercaDe.setPosition(anchoCamara / 2, altoCamara * 0.2f);
        escena.addActor(btnAcercaDe);

        Image imgTitulo = new Image(texturaTitulo);
        imgTitulo.setPosition(anchoCamara / 2 - imgTitulo.getWidth() / 2, altoCamara * 0.8f);

        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Presiono jugar");
                int ancho = 5;
                int alto = 5;
                int cantGragea = 5;
                int velocidad = 10;

                Juego juegoLogico = new Juego(ancho, alto, velocidad, cantGragea);

                JuegoVisual juegoVisual = new JuegoVisual(adminPantalla);

                JuegoControlador juegoControlador = new JuegoControlador(juegoLogico, juegoVisual);
                Thread juegoControladorThread = new Thread(juegoControlador);
                juegoControladorThread.start();

                dispose();
                adminPantalla.setScreen(juegoVisual);
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
        //dispose();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.act(delta);
        escena.setViewport(vista);
        escena.draw();
    }

    public void dispose() {
        texturaFondo.dispose();
        texturaBtnJugar.dispose();
        texturaBtnOpciones.dispose();
        texturaBtnAcercaDe.dispose();
        texturaTitulo.dispose();
        escena.dispose();
        assetManager.unload("fondo.jpg");
        assetManager.unload("jugar.png");
        assetManager.unload("opciones.png");
        assetManager.unload("acerca_de.png");
        assetManager.unload("titulo.png");
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
