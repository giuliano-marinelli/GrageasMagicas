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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class MenuRanking implements Screen {

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
    private Label lblRanking;
    private Table tblRanking;
    private ScrollPane scrPaneRanking;
    //assets
    private Texture txtFondo;
    private BitmapFont fntFuenteBase;

    public MenuRanking(AdministradorPantalla adminPantalla) {
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

        ArrayList<String[]> ranking = adminPantalla.getInterfazDb().consultarRanking();

        Label.LabelStyle lblStlRanking = new Label.LabelStyle(fntFuenteBase, Color.GOLD);

        lblRanking = new Label(strings.get("btn_ranking"), lblStlRanking);
        lblRanking.setFontScale(2f, 2f);
        lblRanking.setWrap(true);
        lblRanking.setAlignment(1);

        tblRanking = new Table();
        tblRanking.row();
        tblRanking.add(lblRanking).colspan(4);

        for (int i = 0; i < ranking.size(); i++) {
            tblRanking.row();
            Label lblPuesto = new Label("(" + (i + 1) + ")", lblStlRanking);
            //lblPuesto.setFontScale(1.5f, 1.5f);
            Label lblNombre = new Label(ranking.get(i)[0], lblStlRanking);
            lblNombre.setEllipsis(true);
            //lblNombre.setFontScale(1.5f, 1.5f);
            Label lblPuntaje = new Label(ranking.get(i)[1], lblStlRanking);
            lblPuntaje.setEllipsis(true);
            //lblPuntaje.setFontScale(1.5f, 1.5f);
            Label lblDificultad = new Label("N", lblStlRanking);
            //lblDificultad.setFontScale(1.5f, 1.5f);
            switch (Integer.parseInt(ranking.get(i)[2])) {
                case 0:
                    lblDificultad.setText("F");
                    lblDificultad.setColor(Color.GREEN);
                    break;
                case 1:
                    lblDificultad.setText("M");
                    lblDificultad.setColor(Color.YELLOW);
                    break;
                case 2:
                    lblDificultad.setText("H");
                    lblDificultad.setColor(Color.RED);
                    break;
            }
            tblRanking.add(lblPuesto).space(50);
            tblRanking.add(lblNombre).space(50).width(250);
            tblRanking.add(lblDificultad).space(50);
            tblRanking.add(lblPuntaje).space(50).width(175);
        }
        tblRanking.pad(50f);
        tblRanking.top();
        //tblRanking.debug();
        tblRanking.pack();

        scrPaneRanking = new ScrollPane(tblRanking);
        scrPaneRanking.setWidth(anchoCamara / 2);
        scrPaneRanking.setHeight(altoCamara);
        scrPaneRanking.setPosition(anchoCamara / 2 - scrPaneRanking.getWidth() / 2, 0);
        scrPaneRanking.setScrollingDisabled(true, false);
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