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
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.grageasmagicas.parte_logica.JuegoLogico2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MenuNiveles implements Screen {

    //visual
    private int anchoCamara;
    private int altoCamara;
    private int cantNiveles;
    private int nivelLogrado;
    //administradores
    private AssetManager assetManager;
    private AdministradorPantalla adminPantalla;
    private Viewport vista;
    private Stage escena;
    private I18NBundle strings;
    //actors
    private TextButton btnVolver;
    private Table tblNiveles;
    private ScrollPane scrPaneNiveles;
    private ImageTextButton[] nivel;
    private Image imgFondo;
    //assets
    private Texture txtFondo;
    private Texture txtBtnNivel;
    private BitmapFont fntFuenteBase;

    public MenuNiveles(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();

        cantNiveles = 50;

        if (adminPantalla.isSession()) {
            nivelLogrado = adminPantalla.getInterfazDb().consultarNivelLogrado(adminPantalla.getIdUser());
        } else {
            nivelLogrado = 0;
        }

        cargarAssets();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);
    }

    @Override
    public void show() {
        imgFondo = new Image(txtFondo);
        imgFondo.setScale(anchoCamara / imgFondo.getWidth(), altoCamara / imgFondo.getHeight());
        escena.addActor(imgFondo);

        nivel = new ImageTextButton[cantNiveles];

        TextureRegionDrawable trBtnNivel = new TextureRegionDrawable(new TextureRegion(txtBtnNivel));
        ImageTextButton.ImageTextButtonStyle btnStlNivel = new ImageTextButton.ImageTextButtonStyle(
                trBtnNivel, trBtnNivel, trBtnNivel, fntFuenteBase);

        Random random = new Random();

        tblNiveles = new Table();
        for (int i = 1; i < cantNiveles + 1; i++) {
            nivel[i - 1] = new ImageTextButton((i) + "", btnStlNivel);
            nivel[i - 1].addListener(new NivelListener((i - 1), nivelLogrado, adminPantalla));
            if ((i - 1) <= nivelLogrado) {
                nivel[i - 1].setColor(
                        new Color(random.nextFloat() / 2f + 0.4f,
                                random.nextFloat() / 2f + 0.4f,
                                random.nextFloat() / 2f + 0.4f, 1));
            } else {
                nivel[i - 1].setColor(Color.BLACK);
            }
            tblNiveles.add(nivel[i - 1]).space(50);
            if (i % 3 == 0) {
                tblNiveles.row();
            }
        }
        tblNiveles.pack();

        scrPaneNiveles = new ScrollPane(tblNiveles);
        scrPaneNiveles.setWidth(anchoCamara / 2);
        scrPaneNiveles.setHeight(altoCamara);
        scrPaneNiveles.setPosition(anchoCamara / 2 - scrPaneNiveles.getWidth() / 2, 0);
        escena.addActor(scrPaneNiveles);

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
