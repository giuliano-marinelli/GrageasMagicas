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
    private TextButton btnAcercaDe;
    private TextButton btnNombreApp;
    private TextButton btnVersion;
    private TextButton btnDesarrolladores;
    private TextButton btnNombreDesarrolladores;
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


        final TextButton.TextButtonStyle btnStlAcercaDe = new TextButton.TextButtonStyle();
        btnStlAcercaDe.font = fntFuenteBase;
        btnStlAcercaDe.fontColor = Color.FIREBRICK;
        btnAcercaDe = new TextButton(strings.get("btn_acerca_de"), btnStlAcercaDe);
        btnAcercaDe.getLabel().setFontScale(3, 3);
        btnAcercaDe.setWidth(btnAcercaDe.getPrefWidth());
        btnAcercaDe.setHeight(btnAcercaDe.getPrefHeight());
        btnAcercaDe.setPosition(anchoCamara / 2 - (btnAcercaDe.getWidth() / 2), altoCamara * 0.8f);
        escena.addActor(btnAcercaDe);

        final TextButton.TextButtonStyle btnStlNombreApp = new TextButton.TextButtonStyle();
        btnStlNombreApp.font = fntFuenteBase;
        btnStlNombreApp.fontColor = Color.CHARTREUSE;
        btnNombreApp = new TextButton(strings.get("btn_nombre_app"), btnStlNombreApp);
        btnNombreApp.getLabel().setFontScale(2, 2);
        btnNombreApp.setWidth(btnNombreApp.getPrefWidth());
        btnNombreApp.setHeight(btnNombreApp.getPrefHeight());
        btnNombreApp.setPosition(anchoCamara / 2 - (btnNombreApp.getWidth() / 2), altoCamara * 0.72f);
        escena.addActor(btnNombreApp);

        final TextButton.TextButtonStyle btnStlVersion = new TextButton.TextButtonStyle();
        btnStlVersion.font = fntFuenteBase;
        btnStlVersion.fontColor = Color.CHARTREUSE;
        btnVersion = new TextButton(strings.get("btn_version")+": "+adminPantalla.getNumVersion(), btnStlVersion);
        btnVersion.getLabel().setFontScale(2, 2);
        btnVersion.setWidth(btnVersion.getPrefWidth());
        btnVersion.setHeight(btnVersion.getPrefHeight());
        btnVersion.setPosition(anchoCamara / 2 - (btnVersion.getWidth() / 2), altoCamara * 0.6f);
        escena.addActor(btnVersion);

        final TextButton.TextButtonStyle btnStlDesarrolladores = new TextButton.TextButtonStyle();
        btnStlDesarrolladores.font = fntFuenteBase;
        btnStlDesarrolladores.fontColor = Color.CHARTREUSE;
        btnDesarrolladores = new TextButton(strings.get("btn_desarrolladores")+":", btnStlDesarrolladores);
        btnDesarrolladores.getLabel().setFontScale(2, 2);
        btnDesarrolladores.setWidth(btnDesarrolladores.getPrefWidth());
        btnDesarrolladores.setHeight(btnDesarrolladores.getPrefHeight());
        btnDesarrolladores.setPosition(anchoCamara / 2 - (btnDesarrolladores.getWidth() / 2), altoCamara * 0.5f);
        escena.addActor(btnDesarrolladores);

        final TextButton.TextButtonStyle btnStlNombreDesarrolladores = new TextButton.TextButtonStyle();
        btnStlNombreDesarrolladores.font = fntFuenteBase;
        btnStlNombreDesarrolladores.fontColor = Color.LIME;
        btnNombreDesarrolladores = new TextButton("Marinelli Giuliano \n Ines Kurchan \n Bermudez Martin", btnStlDesarrolladores);
        btnNombreDesarrolladores.getLabel().setFontScale(1.5f, 1.5f);
        btnNombreDesarrolladores.setWidth(btnNombreDesarrolladores.getPrefWidth());
        btnNombreDesarrolladores.setHeight(btnNombreDesarrolladores.getPrefHeight());
        btnNombreDesarrolladores.setPosition(anchoCamara / 2 - (btnNombreDesarrolladores.getWidth() / 2), altoCamara * 0.3f);
        escena.addActor(btnNombreDesarrolladores);


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
