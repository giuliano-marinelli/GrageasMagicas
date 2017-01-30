package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class MenuOpciones implements Screen {

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
    private TextButton btnComoJugar;
    private TextButton btnSonidoOnOf;
    private TextButton btnVibracion;
    private Image imgFondo;
    //assets
    private Texture txtFondo;
    private BitmapFont fntFuenteBase;

    public MenuOpciones(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();

        cargarAssets();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);
    }

    @Override
    public void show() {
        imgFondo = new Image(txtFondo);
        imgFondo.setScale(anchoCamara / imgFondo.getWidth(), altoCamara / imgFondo.getHeight());
        escena.addActor(imgFondo);

        ArrayList<String[]> ranking = adminPantalla.getInterfazDb().consultarRanking();

        TextButton.TextButtonStyle btnStlDatos = new TextButton.TextButtonStyle();
        btnStlDatos.font = fntFuenteBase;
        btnStlDatos.fontColor = Color.GOLD;
        TextButton espacio = new TextButton("                       ", btnStlDatos);


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
                MenuPrincipal menuPrincipal = new MenuPrincipal(adminPantalla);

                adminPantalla.setScreen(menuPrincipal);
            }
        });
        escena.addActor(btnVolver);


        TextButton.TextButtonStyle btnStlComoJugar = new TextButton.TextButtonStyle();
        btnStlComoJugar.font = fntFuenteBase;
        btnComoJugar = new TextButton(strings.get("btn_como_jugar"), btnStlComoJugar);
        btnComoJugar.getLabel().setFontScale(2, 2);
        btnComoJugar.setWidth(btnVolver.getPrefWidth());
        btnComoJugar.setHeight(btnVolver.getPrefHeight());
        btnComoJugar.setPosition(450f, altoCamara - btnComoJugar.getHeight() - 50);
        btnComoJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        escena.addActor(btnComoJugar);

        final TextButton.TextButtonStyle btnStlSonidoOnOf = new TextButton.TextButtonStyle();
        btnStlSonidoOnOf.font = fntFuenteBase;
        btnSonidoOnOf = new TextButton(strings.get("btn_sonido") + ": " + strings.get("encendido"), btnStlSonidoOnOf);
        btnSonidoOnOf.getLabel().setFontScale(2, 2);
        btnSonidoOnOf.setWidth(btnVolver.getPrefWidth());
        btnSonidoOnOf.setHeight(btnVolver.getPrefHeight());
        btnSonidoOnOf.setChecked(true);
        btnSonidoOnOf.setPosition(575f, altoCamara - btnSonidoOnOf.getHeight() - 150);
        btnSonidoOnOf.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnSonidoOnOf.isChecked()) {
                    btnSonidoOnOf.setText(strings.get("btn_sonido") + ": " + strings.get("encendido"));
                    btnSonidoOnOf.setChecked(false);
                } else {
                    btnSonidoOnOf.setText(strings.get("btn_sonido") + ": " + strings.get("apagado"));
                    btnSonidoOnOf.setChecked(true);
                }
            }
        });
        escena.addActor(btnSonidoOnOf);

        final TextButton.TextButtonStyle btnStlVibracion = new TextButton.TextButtonStyle();
        btnStlVibracion.font = fntFuenteBase;
        btnVibracion = new TextButton(strings.get("btn_vibrar") + ": " + strings.get("encendido"), btnStlVibracion);
        btnVibracion.getLabel().setFontScale(2, 2);
        btnVibracion.setWidth(btnVibracion.getPrefWidth());
        btnVibracion.setHeight(btnVibracion.getPrefHeight());
        btnVibracion.setChecked(true);
        btnVibracion.setPosition(350f, altoCamara - btnVibracion.getHeight() - 250);
        btnVibracion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!btnVibracion.isChecked()) {
                    btnVibracion.setText(strings.get("btn_vibrar") + ": " + strings.get("encendido"));
                    Gdx.input.vibrate(200);
                    btnVibracion.setChecked(false);
                } else {
                    btnVibracion.setText(strings.get("btn_vibrar") + ": " + strings.get("apagado"));
                    btnVibracion.setChecked(true);
                }
            }
        });
        escena.addActor(btnVibracion);
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
        assetManager.update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.act(delta);
        escena.setViewport(vista);
        escena.draw();
    }

    @Override
    public void dispose() {
        txtFondo.dispose();
        fntFuenteBase.dispose();
        escena.dispose();
        //assetManager.clear();
        assetManager.unload("imagenes/fondo_tablero.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("strings/strings");
    }

    private void cargarAssets() {
        assetManager.load("imagenes/fondo_tablero.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondo_tablero.png");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        strings = assetManager.get("strings/strings");
    }


}
