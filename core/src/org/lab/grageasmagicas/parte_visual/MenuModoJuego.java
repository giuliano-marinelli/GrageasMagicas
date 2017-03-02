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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class MenuModoJuego implements Screen {

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
    private ImageButton btnModo0;
    private ImageButton btnModo1;
    private Image imgFondo;
    private Label lblModoJuego;
    private Table tblContenido;
    //assets
    private Texture txtFondo;
    private Texture txtBtnModoJuego0;
    private Texture txtBtnModoJuego1;
    private BitmapFont fntFuenteBase;

    public MenuModoJuego(AdministradorPantalla adminPantalla) {
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

        lblModoJuego = new Label(strings.get("btn_modojuego"), lblStlModoJuego);
        lblModoJuego.setFontScale(2f, 2f);
        lblModoJuego.setWrap(true);
        lblModoJuego.setAlignment(1);

        TextureRegionDrawable trBtnModo0 = new TextureRegionDrawable(new TextureRegion(txtBtnModoJuego0));
        btnModo0 = new ImageButton(trBtnModo0);
        btnModo0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuNiveles()));
            }
        });

        TextureRegionDrawable trBtnModo1 = new TextureRegionDrawable(new TextureRegion(txtBtnModoJuego1));
        btnModo1 = new ImageButton(trBtnModo1);
        btnModo1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuNivelesTiempo()));
            }
        });

        tblContenido = new Table();
        tblContenido.row();
        tblContenido.add(lblModoJuego).colspan(2);
        tblContenido.row().minHeight(altoCamara-300);
        tblContenido.add(btnModo0).pad(25f);
        tblContenido.add(btnModo1).pad(25f);

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
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuPrincipal()));
        }
    }

    @Override
    public void dispose() {
        txtFondo.dispose();
        txtBtnModoJuego0.dispose();
        txtBtnModoJuego1.dispose();
        fntFuenteBase.dispose();
        escena.dispose();
        //assetManager.clear();
        assetManager.unload("imagenes/fondo_tablero.png");
        assetManager.unload("imagenes/btn_modojuego0.png");
        assetManager.unload("imagenes/btn_modojuego1.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("strings/strings");
    }

    private void cargarAssets() {
        assetManager.load("imagenes/fondo_tablero.png", Texture.class);
        assetManager.load("imagenes/btn_modojuego0.png", Texture.class);
        assetManager.load("imagenes/btn_modojuego1.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondo_tablero.png");
        txtBtnModoJuego0 = assetManager.get("imagenes/btn_modojuego0.png");
        txtBtnModoJuego1 = assetManager.get("imagenes/btn_modojuego1.png");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        strings = assetManager.get("strings/strings");
    }
}
