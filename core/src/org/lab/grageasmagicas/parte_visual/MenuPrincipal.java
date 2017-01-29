package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.grageasmagicas.parte_logica.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class MenuPrincipal implements Screen {

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
    private ImageTextButton btnJugar;
    private ImageTextButton btnOpciones;
    private ImageTextButton btnAcercaDe;
    private ImageTextButton btnTestEffects;
    private ImageButton btnSesion;
    private ImageButton btnRanking;
    private TextButton btnSesionMensaje;
    private Image imgFondo;
    private Image imgTitulo;
    //assets
    private Texture txtFondo;
    private Texture txtBtnMenuUp;
    private Texture txtBtnMenuDown;
    private Texture txtBtnSesion;
    private Texture txtBtnRanking;
    private Texture txtTitulo;
    private BitmapFont fntFuenteBase;

    public MenuPrincipal(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();

        cargarAssets();

        //System.out.println(strings.getLocale());

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);
    }

    @Override
    public void show() {
        imgFondo = new Image(txtFondo);
        imgFondo.setScale(anchoCamara / imgFondo.getWidth(), altoCamara / imgFondo.getHeight());
        escena.addActor(imgFondo);

        TextureRegionDrawable trBtnMenuUp = new TextureRegionDrawable(new TextureRegion(txtBtnMenuUp));
        TextureRegionDrawable trBtnMenuDown = new TextureRegionDrawable(new TextureRegion(txtBtnMenuDown));
        ImageTextButton.ImageTextButtonStyle btnStlMenu = new ImageTextButton.ImageTextButtonStyle(
                trBtnMenuUp, trBtnMenuDown, trBtnMenuUp, fntFuenteBase
        );

        btnJugar = new ImageTextButton(strings.get("btn_jugar"), btnStlMenu);
        btnJugar.setPosition(anchoCamara / 2, altoCamara * 0.6f);
        btnJugar.getLabel().setFontScale(2.5f, 2.5f);
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Presiono jugar");
                int ancho = 5;
                int alto = 5;
                int cantGragea = 5;
                int velocidad = 10;
                int movimientos = 3;
                int puntajeGanar = 30;
                AtomicBoolean finJuego = new AtomicBoolean(false);

                JuegoLogico2 juegoLogico = new JuegoLogico2(ancho, alto, velocidad, cantGragea, movimientos, puntajeGanar, finJuego);

                JuegoVisual juegoVisual = new JuegoVisual(adminPantalla);

                JuegoControlador juegoControlador = new JuegoControlador(juegoLogico, juegoVisual, finJuego);
                Thread juegoControladorThread = new Thread(juegoControlador);
                juegoControladorThread.start();

                adminPantalla.setScreen(juegoVisual);
            }
        });
        escena.addActor(btnJugar);

        btnOpciones = new ImageTextButton(strings.get("btn_opciones"), btnStlMenu);
        btnOpciones.setPosition(anchoCamara / 2, altoCamara * 0.4f);
        btnOpciones.getLabel().setFontScale(2f, 2f);
        btnOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Presiono opciones");
            }
        });
        escena.addActor(btnOpciones);

        btnAcercaDe = new ImageTextButton(strings.get("btn_acerca_de"), btnStlMenu);
        btnAcercaDe.setPosition(anchoCamara / 2, altoCamara * 0.2f);
        btnAcercaDe.getLabel().setFontScale(2f, 2f);
        btnAcercaDe.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Presiono acerca de");
            }
        });
        escena.addActor(btnAcercaDe);

        TextureRegionDrawable trBtnSesionLog = new TextureRegionDrawable(new TextureRegion(txtBtnSesion));
        Drawable trBtnSesionNoLog = trBtnSesionLog.tint(Color.RED);

        if (!adminPantalla.isSession()) {
            btnSesion = new ImageButton(trBtnSesionNoLog);
        } else {
            btnSesion = new ImageButton(trBtnSesionLog);
        }
        btnSesion.setPosition(50, 50);
        btnSesion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!adminPantalla.isSession()) {
                    Login login = new Login(adminPantalla);

                    adminPantalla.setScreen(login);
                }
            }
        });
        escena.addActor(btnSesion);

        TextButton.TextButtonStyle btnStlSesionMensaje = new TextButton.TextButtonStyle();
        btnStlSesionMensaje.font = fntFuenteBase;
        btnStlSesionMensaje.fontColor = Color.BLACK;
        btnSesionMensaje = new TextButton("", btnStlSesionMensaje);
        if (adminPantalla.isSession()) {
            btnSesionMensaje.setText(strings.get("btn_sesion_log") + " " + adminPantalla.getUser());
            btnSesionMensaje.getLabel().setFontScale(1.5f, 1.5f);
            btnStlSesionMensaje.fontColor = Color.BLUE;
        } else {
            btnSesionMensaje.setText(strings.get("btn_sesion_no_log"));
            btnStlSesionMensaje.fontColor = Color.RED;
        }
        btnSesionMensaje.setWidth(btnSesionMensaje.getPrefWidth());
        btnSesionMensaje.setHeight(btnSesionMensaje.getPrefHeight());
        btnSesionMensaje.setPosition(100 + btnSesion.getWidth(), 50 + btnSesion.getHeight() / 2 - btnSesionMensaje.getHeight() / 2);
        escena.addActor(btnSesionMensaje);

        TextureRegionDrawable trBtnRanking = new TextureRegionDrawable(new TextureRegion(txtBtnRanking));

        btnRanking = new ImageButton(trBtnRanking);
        btnRanking.setPosition(50, 100 + btnSesion.getHeight());
        btnRanking.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Ranking ranking = new Ranking(adminPantalla);

                adminPantalla.setScreen(ranking);
            }
        });
        escena.addActor(btnRanking);

        /*
        btnTestEffects = new ImageTextButton("TestEffects", btnStlMenu);
        btnTestEffects.getLabel().setFontScale(1.5f, 1.5f);
        btnTestEffects.setTransform(true);
        btnTestEffects.setScale(0.5f, 0.5f);
        btnTestEffects.setWidth(btnTestEffects.getPrefWidth());
        btnTestEffects.setHeight(btnTestEffects.getPrefHeight());
        btnTestEffects.setPosition(anchoCamara / 2 + btnAcercaDe.getWidth() - btnTestEffects.getWidth(), altoCamara * 0.1f);
        btnTestEffects.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adminPantalla.setScreen(new TestEffect(adminPantalla));
            }
        });
        escena.addActor(btnTestEffects);
        */

        imgTitulo = new Image(txtTitulo);
        imgTitulo.setPosition(anchoCamara / 2 - imgTitulo.getWidth() / 2, altoCamara * 0.8f);
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
        dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.act(delta);
        escena.setViewport(vista);
        escena.draw();
    }

    @Override
    public void dispose() {
        txtFondo.dispose();
        txtBtnMenuUp.dispose();
        txtBtnMenuDown.dispose();
        txtBtnSesion.dispose();
        txtBtnRanking.dispose();
        txtTitulo.dispose();
        fntFuenteBase.dispose();
        escena.dispose();
        //assetManager.clear();
        assetManager.unload("imagenes/fondogolosinas.png");
        assetManager.unload("imagenes/menu_btn_up.png");
        assetManager.unload("imagenes/menu_btn_down.png");
        assetManager.unload("imagenes/btn_sesion.png");
        assetManager.unload("imagenes/ranking.png");
        assetManager.unload("imagenes/titulo.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("strings/strings");
    }

    private void cargarAssets() {
        assetManager.load("imagenes/fondogolosinas.png", Texture.class);
        assetManager.load("imagenes/menu_btn_up.png", Texture.class);
        assetManager.load("imagenes/menu_btn_down.png", Texture.class);
        assetManager.load("imagenes/btn_sesion.png", Texture.class);
        assetManager.load("imagenes/ranking.png", Texture.class);
        assetManager.load("imagenes/titulo.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondogolosinas.png");
        txtBtnMenuUp = assetManager.get("imagenes/menu_btn_up.png");
        txtBtnMenuDown = assetManager.get("imagenes/menu_btn_down.png");
        txtBtnSesion = assetManager.get("imagenes/btn_sesion.png");
        txtBtnRanking = assetManager.get("imagenes/ranking.png");
        txtTitulo = assetManager.get("imagenes/titulo.png");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        strings = assetManager.get("strings/strings");
    }
}
