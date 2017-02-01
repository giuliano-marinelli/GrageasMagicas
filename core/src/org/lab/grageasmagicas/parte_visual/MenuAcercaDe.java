package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class MenuAcercaDe implements Screen {

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
    private Label lblAcercaDe;
    private Label lblNombreJuego;
    private Label lblVersion;
    private Label lblDesarrolladores;
    private Label lblNombresDesarrolladores;
    private Table tblContenido;
    private Image imgFondo;
    //assets
    private Texture txtFondo;
    private BitmapFont fntFuenteBase;

    public MenuAcercaDe(AdministradorPantalla adminPantalla) {
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

        Label.LabelStyle lblStlAcercaDe = new Label.LabelStyle(fntFuenteBase, Color.GOLD);

        lblAcercaDe = new Label(strings.get("btn_acerca_de"), lblStlAcercaDe);
        lblAcercaDe.setFontScale(2f, 2f);
        lblAcercaDe.setWrap(true);
        lblAcercaDe.setAlignment(1);

        Label.LabelStyle lblStlNombreJuego = new Label.LabelStyle(fntFuenteBase, Color.RED);

        lblNombreJuego = new Label(strings.get("game"), lblStlNombreJuego);
        lblNombreJuego.setFontScale(1.5f, 1.5f);
        lblNombreJuego.setWrap(true);
        lblNombreJuego.setAlignment(1);

        Label.LabelStyle lblStlVersion = new Label.LabelStyle(fntFuenteBase, Color.LIGHT_GRAY);

        lblVersion = new Label(strings.get("btn_version") + ": " + adminPantalla.getNumVersion(), lblStlVersion);
        lblVersion.setFontScale(1.5f, 1.5f);
        lblVersion.setWrap(true);
        lblVersion.setAlignment(1);

        Label.LabelStyle lblStlDesarrolladores = new Label.LabelStyle(fntFuenteBase, Color.GREEN);

        lblDesarrolladores = new Label(strings.get("btn_desarrolladores"), lblStlDesarrolladores);
        lblDesarrolladores.setFontScale(2f, 2f);
        lblDesarrolladores.setWrap(true);
        lblDesarrolladores.setAlignment(1);

        Label.LabelStyle lblStlNombresDesarrolladores = new Label.LabelStyle(fntFuenteBase, Color.WHITE);

        lblNombresDesarrolladores = new Label("Marinelli Giuliano\nInes Kurchan\nBermudez Martin", lblStlNombresDesarrolladores);
        lblNombresDesarrolladores.setFontScale(1.5f, 1.5f);
        lblNombresDesarrolladores.setWrap(true);
        lblNombresDesarrolladores.setAlignment(1);

        tblContenido = new Table();
        tblContenido.row();
        tblContenido.add(lblAcercaDe).padTop(50f);
        tblContenido.row();
        tblContenido.add(lblNombreJuego);
        tblContenido.row();
        tblContenido.add(lblVersion).padBottom(50f);
        tblContenido.row();
        tblContenido.add(lblDesarrolladores);
        tblContenido.row();
        tblContenido.add(lblNombresDesarrolladores).padBottom(50f);
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
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuPrincipal()));
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
            adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuPrincipal()));
        }
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
