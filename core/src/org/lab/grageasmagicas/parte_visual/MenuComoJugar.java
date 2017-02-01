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

public class MenuComoJugar implements Screen {

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
    private Label lblMovBasico;
    private Label lblMovCruz;
    private Label lblExplicacion1;
    private Label lblExplicacion2;
    private Table tblContenido;
    private ScrollPane scrPaneRanking;
    //assets
    private Texture txtFondo;
    private BitmapFont fntFuenteBase;
    private Texture txtMov1;
    private Texture txtMov3;
    private Texture txtMovCruz;
    private Texture txtElimCruz;
    private Texture txtFlecha;

    public MenuComoJugar(AdministradorPantalla adminPantalla) {
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

        imgMov1 = new Image(txtMov1);

        imgMov3 = new Image(txtMov3);

        imgMovCruz = new Image(txtMovCruz);

        imgFlecha = new Image(txtFlecha);

        imgElimCruz = new Image(txtElimCruz);

        Label.LabelStyle lblStlMov = new Label.LabelStyle(fntFuenteBase, Color.GOLD);

        lblMovBasico = new Label(strings.get("lblMovBasico"), lblStlMov);
        lblMovBasico.setFontScale(1.5f, 1.5f);
        lblMovBasico.setWrap(true);
        lblMovBasico.setAlignment(1);

        lblMovCruz = new Label(strings.get("lblMovCruz"), lblStlMov);
        lblMovCruz.setFontScale(1.5f, 1.5f);
        lblMovCruz.setWrap(true);
        lblMovCruz.setAlignment(1);

        Label.LabelStyle lblStlExplicacion = new Label.LabelStyle(fntFuenteBase, Color.WHITE);

        lblExplicacion1 = new Label(strings.get("lblExplicacionMovimiento1") + ": ", lblStlExplicacion);
        lblExplicacion1.setWrap(true);
        lblExplicacion1.setAlignment(1);

        lblExplicacion2 = new Label(strings.get("lblExplicacionMovimiento2") + ": ", lblStlExplicacion);
        lblExplicacion2.setWrap(true);
        lblExplicacion2.setAlignment(1);

        tblContenido = new Table();
        tblContenido.row();
        tblContenido.add(lblMovBasico).pad(50f);
        tblContenido.row();
        tblContenido.add(lblExplicacion1).width(anchoCamara / 2).padBottom(50f);
        tblContenido.row();
        tblContenido.add(imgMov1).size(imgMov1.getWidth() * 2, imgMov1.getHeight() * 2).padBottom(50f);
        tblContenido.row();
        tblContenido.add(imgMov3).size(imgMov3.getWidth() * 2, imgMov3.getHeight() * 2).padBottom(50f);
        tblContenido.row();
        tblContenido.add(lblMovCruz).padBottom(50f);
        tblContenido.row();
        tblContenido.add(lblExplicacion2).width(anchoCamara / 2).padBottom(50f);
        tblContenido.row();
        tblContenido.add(imgMovCruz).padBottom(50f);
        tblContenido.row();
        tblContenido.add(imgFlecha).size(imgFlecha.getWidth() * 0.5f, imgFlecha.getHeight() * 0.5f).padBottom(50f);
        tblContenido.row();
        tblContenido.add(imgElimCruz).padBottom(50f);

        //tblContenido.debug();
        tblContenido.pack();

        scrPaneRanking = new ScrollPane(tblContenido);
        scrPaneRanking.setScrollingDisabled(true, false);
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
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuOpciones()));
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
            adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuOpciones()));
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
        assetManager.unload("imagenes/movimientos/ElimCruzv.png");
        assetManager.unload("imagenes/movimientos/mov1Ok.png");
        assetManager.unload("imagenes/movimientos/mov3Ok.png");
        assetManager.unload("imagenes/movimientos/movCruzOk.png");
        assetManager.unload("imagenes/movimientos/flechaPerspectivaBot.png");
    }

    private void cargarAssets() {
        assetManager.load("imagenes/fondo_tablero.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.load("imagenes/movimientos/ElimCruzv.png", Texture.class);
        assetManager.load("imagenes/movimientos/mov1Ok.png", Texture.class);
        assetManager.load("imagenes/movimientos/mov3Ok.png", Texture.class);
        assetManager.load("imagenes/movimientos/movCruzOk.png", Texture.class);
        assetManager.load("imagenes/movimientos/flechaPerspectivaBot.png", Texture.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondo_tablero.png");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        strings = assetManager.get("strings/strings");
        txtElimCruz = assetManager.get("imagenes/movimientos/ElimCruzv.png");
        txtMov1 = assetManager.get("imagenes/movimientos/mov1Ok.png");
        txtMov3 = assetManager.get("imagenes/movimientos/mov3Ok.png");
        txtMovCruz = assetManager.get("imagenes/movimientos/movCruzOk.png");
        txtFlecha = assetManager.get("imagenes/movimientos/flechaPerspectivaBot.png");
    }


}
