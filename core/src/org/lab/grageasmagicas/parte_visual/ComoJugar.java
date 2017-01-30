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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class ComoJugar implements Screen {

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
    private Image imgFondo;
    private Image imgMov1;
    private Image imgMov3;
    private Image imgMovCruz;
    private Image imgElimCruz;
    private Image imgFlecha;
    private Table tblRanking;
    private ScrollPane scrPaneRanking;
    //assets
    private Texture txtFondo;
    private BitmapFont fntFuenteBase;
    private Texture txtMov1;
    private Texture txtMov3;
    private Texture txtMovCruz;
    private Texture txtElimCruz;
    private Texture txtFlecha;

    public ComoJugar(AdministradorPantalla adminPantalla) {
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

        TextButton.TextButtonStyle btnStlDatos = new TextButton.TextButtonStyle();
        btnStlDatos.font = fntFuenteBase;
        btnStlDatos.fontColor = Color.BLACK;

        tblRanking = new Table();
        tblRanking.row();
        TextButton btnMovBasico = new TextButton("Movimientos basicos" + ": ", btnStlDatos);
        btnMovBasico.getLabel().setFontScale(1.5f, 1.5f);

        tblRanking.add(btnMovBasico);
        tblRanking.row();
        Label lblExplicacion1 = new Label(strings.get("lblExplicacionMovimiento1") + ": ", new Label.LabelStyle(fntFuenteBase, Color.BLACK));
        lblExplicacion1.setWrap(true);
        lblExplicacion1.setAlignment(1);
        lblExplicacion1.setFontScale(1f, 1f);
        tblRanking.add(lblExplicacion1).width(anchoCamara / 2 - 5);
        tblRanking.row();
        //aca va imagen
        imgMov1 = new Image(txtMov1);
        imgMov1.setScale(2f, 2f);
        tblRanking.add(imgMov1).pad(200, -100, 0, 0);
        tblRanking.row();
        imgMov3 = new Image(txtMov3);
        imgMov3.setScale(2f, 2f);
        tblRanking.add(imgMov3).pad(200,-100,0,0);
        tblRanking.row();
        TextButton btnMovCruz = new TextButton("Movimientos en cruz" + ": ", btnStlDatos);
        btnMovCruz.getLabel().setFontScale(1.5f, 1.5f);
        tblRanking.add(btnMovCruz);
        tblRanking.row();
        Label lblExplicacion2 = new Label(strings.get("lblExplicacionMovimiento1") + ": ", new Label.LabelStyle(fntFuenteBase, Color.BLACK));
        lblExplicacion2.setFontScale(1f, 1f);
        lblExplicacion2.setAlignment(1);
        lblExplicacion2.setWrap(true);
        tblRanking.add(lblExplicacion2).width(anchoCamara / 2 - 5);
        tblRanking.row();
        //aca va imagen
        imgMovCruz = new Image(txtMovCruz);
        imgMovCruz.setScale(1f, 1f);
        tblRanking.add(imgMovCruz);
        tblRanking.row();
        imgFlecha = new Image(txtFlecha);
        imgFlecha.setScale(0.5f, 0.5f);
        tblRanking.add(imgFlecha).pad(-150, 500, 0, 0);
        tblRanking.row();
        imgElimCruz = new Image(txtElimCruz);
        imgElimCruz.setScale(1f, 1f);
        tblRanking.add(imgElimCruz);
        tblRanking.row();
        tblRanking.pad(50f);
        tblRanking.pack();
        tblRanking.setWidth(anchoCamara / 2);

        scrPaneRanking = new ScrollPane(tblRanking);
        scrPaneRanking.setWidth(anchoCamara / 2);
        scrPaneRanking.setHeight(altoCamara);
        scrPaneRanking.setPosition(anchoCamara / 2 - scrPaneRanking.getWidth() / 2, 0);

        escena.addActor(scrPaneRanking);

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
        fntFuenteBase.dispose();
        escena.dispose();
        //assetManager.clear();
        assetManager.unload("imagenes/fondogolosinas.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("strings/strings");
        assetManager.unload("imagenes/movimientos/ElimCruzv.png");
        assetManager.unload("imagenes/movimientos/mov1Ok.png");
        assetManager.unload("imagenes/movimientos/mov3Ok.png");
        assetManager.unload("imagenes/movimientos/movCruzOk.png");
        assetManager.unload("imagenes/movimientos/flechaPerspectivaBot.png");
    }

    private void cargarAssets() {
        assetManager.load("imagenes/fondogolosinas.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.load("imagenes/movimientos/ElimCruzv.png", Texture.class);
        assetManager.load("imagenes/movimientos/mov1Ok.png", Texture.class);
        assetManager.load("imagenes/movimientos/mov3Ok.png", Texture.class);
        assetManager.load("imagenes/movimientos/movCruzOk.png", Texture.class);
        assetManager.load("imagenes/movimientos/flechaPerspectivaBot.png", Texture.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondogolosinas.png");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        strings = assetManager.get("strings/strings");
        txtElimCruz = assetManager.get("imagenes/movimientos/ElimCruzv.png");
        txtMov1 = assetManager.get("imagenes/movimientos/mov1Ok.png");
        txtMov3 = assetManager.get("imagenes/movimientos/mov3Ok.png");
        txtMovCruz = assetManager.get("imagenes/movimientos/movCruzOk.png");
        txtFlecha = assetManager.get("imagenes/movimientos/flechaPerspectivaBot.png");
    }


}
