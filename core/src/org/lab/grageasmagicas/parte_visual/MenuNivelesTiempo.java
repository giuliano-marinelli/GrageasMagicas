package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.grageasmagicas.parte_logica.Juego;

import java.util.concurrent.atomic.AtomicBoolean;

public class MenuNivelesTiempo implements Screen {

    //visual
    private int anchoCamara;
    private int altoCamara;
    //administradores
    private AssetManager assetManager;
    private AdministradorPantalla adminPantalla;
    private Viewport vista;
    private Stage escena;
    private I18NBundle strings;
    //actors
    private TextButton btnVolver;
    private ImageTextButton[] btnDificultad;
    private Image imgFondo;
    private Label lblDificultad;
    private Table tblContenido;
    //assets
    private Texture txtFondo;
    private Texture txtBtnNivel;
    private BitmapFont fntFuenteBase;

    public MenuNivelesTiempo(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();

        cargarAssets();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        imgFondo = new Image(txtFondo);
        imgFondo.setScale(anchoCamara / imgFondo.getWidth(), altoCamara / imgFondo.getHeight());
        escena.addActor(imgFondo);

        Label.LabelStyle lblStlModoJuego = new Label.LabelStyle(fntFuenteBase, Color.GOLD);

        lblDificultad = new Label(strings.get("btn_dificultad"), lblStlModoJuego);
        lblDificultad.setFontScale(2f, 2f);
        lblDificultad.setWidth(50);
        lblDificultad.setWrap(true);
        lblDificultad.setAlignment(1);

        btnDificultad = new ImageTextButton[3];

        TextureRegionDrawable trBtnDificultad = new TextureRegionDrawable(new TextureRegion(txtBtnNivel));
        ImageTextButton.ImageTextButtonStyle btnStlDificultad = new ImageTextButton.ImageTextButtonStyle(
                trBtnDificultad, trBtnDificultad, trBtnDificultad, fntFuenteBase);

        btnDificultad[0] = new ImageTextButton(strings.get("btn_dificultad_facil"), btnStlDificultad);
        btnDificultad[0].setColor(Color.GREEN);
        btnDificultad[0].getLabel().setColor(Color.GREEN);
        btnDificultad[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AtomicBoolean finJuego = new AtomicBoolean(false);
                Juego juegoLogico = new Juego(5, 5, 0, 4, 0, 0, 0, 5, 1, 60, 0, finJuego);

                JuegoVisual juegoVisual = new JuegoVisual(adminPantalla);

                JuegoControlador juegoControlador = new JuegoControlador(juegoLogico, juegoVisual, finJuego);
                Thread juegoControladorThread = new Thread(juegoControlador);
                juegoControladorThread.start();

                adminPantalla.setScreen(juegoVisual);
            }
        });

        btnDificultad[1] = new ImageTextButton(strings.get("btn_dificultad_media"), btnStlDificultad);
        btnDificultad[1].setColor(Color.YELLOW);
        btnDificultad[1].getLabel().setColor(Color.YELLOW);
        btnDificultad[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AtomicBoolean finJuego = new AtomicBoolean(false);
                Juego juegoLogico = new Juego(6, 6, 0, 5, 0, 0, 0, 5, 1, 80, 1, finJuego);

                JuegoVisual juegoVisual = new JuegoVisual(adminPantalla);

                JuegoControlador juegoControlador = new JuegoControlador(juegoLogico, juegoVisual, finJuego);
                Thread juegoControladorThread = new Thread(juegoControlador);
                juegoControladorThread.start();

                adminPantalla.setScreen(juegoVisual);
            }
        });

        btnDificultad[2] = new ImageTextButton(strings.get("btn_dificultad_dificil"), btnStlDificultad);
        btnDificultad[2].setColor(Color.RED);
        btnDificultad[2].getLabel().setColor(Color.RED);
        btnDificultad[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AtomicBoolean finJuego = new AtomicBoolean(false);
                Juego juegoLogico = new Juego(7, 7, 0, 6, 0, 0, 0, 5, 1, 95, 2, finJuego);

                JuegoVisual juegoVisual = new JuegoVisual(adminPantalla);

                JuegoControlador juegoControlador = new JuegoControlador(juegoLogico, juegoVisual, finJuego);
                Thread juegoControladorThread = new Thread(juegoControlador);
                juegoControladorThread.start();

                adminPantalla.setScreen(juegoVisual);
            }
        });

        tblContenido = new Table();
        tblContenido.row();
        tblContenido.add(lblDificultad).width(600);
        tblContenido.row();
        tblContenido.add(btnDificultad[0]).pad(15f).size(192, 144);
        tblContenido.row();
        tblContenido.add(btnDificultad[1]).pad(15f).size(192, 144);
        tblContenido.row();
        tblContenido.add(btnDificultad[2]).pad(15f).size(192, 144);

        tblContenido.pad(50f);
        tblContenido.top();
        //tblContenido.debug();
        tblContenido.pack();
        tblContenido.setPosition(anchoCamara / 2 - tblContenido.getWidth() / 2, altoCamara - tblContenido.getHeight());
        escena.addActor(tblContenido);

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
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuModoJuego()));
            }
        });
        escena.addActor(btnVolver);

    }

    @Override
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
        dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.act(delta);
        escena.setViewport(vista);
        escena.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuModoJuego()));
        }
    }

    @Override
    public void dispose() {
        txtFondo.dispose();
        txtBtnNivel.dispose();
        fntFuenteBase.dispose();
        escena.dispose();
        //assetManager.clear();
        assetManager.unload("imagenes/fondo_tablero.png");
        assetManager.unload("imagenes/btn_nivel.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("strings/strings");
    }

    private void cargarAssets() {
        assetManager.load("imagenes/fondo_tablero.png", Texture.class);
        assetManager.load("imagenes/btn_nivel.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondo_tablero.png");
        txtBtnNivel = assetManager.get("imagenes/btn_nivel.png");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        strings = assetManager.get("strings/strings");
    }
}
