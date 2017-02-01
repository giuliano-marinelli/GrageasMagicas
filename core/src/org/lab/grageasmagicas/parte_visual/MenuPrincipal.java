package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuPrincipal implements Screen {

    //visual
    private int anchoCamara;
    private int altoCamara;
    //administradores
    private AssetManager assetManager;
    private AdministradorPantalla adminPantalla;
    private Viewport vista;
    private Stage escena;
    private SpriteBatch batch;
    private I18NBundle strings;
    //actors
    private ImageTextButton btnJugar;
    private ImageTextButton btnOpciones;
    private ImageTextButton btnAcercaDe;
    private ImageTextButton btnTestEffects;
    private ImageButton btnSesion;
    private ImageButton btnRanking;
    private TextButton btnSesionMensaje;
    private Label lblTitulo;
    //assets
    private Texture txtFondo;
    private Texture txtBtnMenuUp;
    private Texture txtBtnMenuDown;
    private Texture txtBtnSesion;
    private Texture txtBtnRanking;
    private BitmapFont fntFuenteBase;
    private BitmapFont fntFuenteTitulo;
    private ParticleEffect parEfcLuz;

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

        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Label.LabelStyle lblStlTitulo = new Label.LabelStyle(fntFuenteTitulo, Color.WHITE);

        lblTitulo = new Label(strings.get("game"), lblStlTitulo);
        lblTitulo.setFontScale(1.25f, 1.25f);
        lblTitulo.setPosition(anchoCamara / 2 - lblTitulo.getPrefWidth() / 2, altoCamara - lblTitulo.getPrefHeight());
        escena.addActor(lblTitulo);

        parEfcLuz.setPosition(100, altoCamara/2);
        parEfcLuz.start();

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
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuNiveles()));
            }
        });
        escena.addActor(btnJugar);

        btnOpciones = new ImageTextButton(strings.get("btn_opciones"), btnStlMenu);
        btnOpciones.setPosition(anchoCamara / 2, altoCamara * 0.4f);
        btnOpciones.getLabel().setFontScale(2f, 2f);
        btnOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuOpciones()));
            }
        });
        escena.addActor(btnOpciones);

        btnAcercaDe = new ImageTextButton(strings.get("btn_acerca_de"), btnStlMenu);
        btnAcercaDe.setPosition(anchoCamara / 2, altoCamara * 0.2f);
        btnAcercaDe.getLabel().setFontScale(2f, 2f);
        btnAcercaDe.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuAcercaDe()));
            }
        });
        escena.addActor(btnAcercaDe);

        TextureRegionDrawable trBtnSesionLog = new TextureRegionDrawable(new TextureRegion(txtBtnSesion));
        Drawable trBtnSesionNoLog = trBtnSesionLog.tint(Color.RED);

        if (!adminPantalla.isSesion()) {
            btnSesion = new ImageButton(trBtnSesionNoLog);
        } else {
            btnSesion = new ImageButton(trBtnSesionLog);
        }
        btnSesion.setPosition(50, 50);
        btnSesion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!adminPantalla.isSesion()) {
                    adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuLogin()));
                } else {
                    adminPantalla.getInterfazDb().borrarSesion();
                    adminPantalla.setIdUsuario(-1);
                    adminPantalla.setSesion(false);
                    adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuPrincipal()));
                }
            }
        });
        escena.addActor(btnSesion);

        TextButton.TextButtonStyle btnStlSesionMensaje = new TextButton.TextButtonStyle();
        btnStlSesionMensaje.font = fntFuenteBase;
        btnStlSesionMensaje.fontColor = Color.BLACK;
        btnSesionMensaje = new TextButton("", btnStlSesionMensaje);
        if (adminPantalla.isSesion()) {
            btnSesionMensaje.setText(strings.get("btn_sesion_log") + " " +
                    adminPantalla.getInterfazDb().consultarNombreUsuario(adminPantalla.getIdUsuario()));
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
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuRanking()));
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

        batch.begin();
        float deltaTime = Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(adminPantalla.getCamara().combined);
        batch.draw(txtFondo, 0, 0, anchoCamara, altoCamara);
        parEfcLuz.draw(batch, deltaTime);
        if (parEfcLuz.isComplete()) {
            parEfcLuz.reset();
        }
        batch.end();
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
        fntFuenteBase.dispose();
        fntFuenteTitulo.dispose();
        parEfcLuz.dispose();
        escena.dispose();
        //assetManager.clear();
        assetManager.unload("imagenes/fondogolosinas.png");
        assetManager.unload("imagenes/menu_btn_up.png");
        assetManager.unload("imagenes/menu_btn_down.png");
        assetManager.unload("imagenes/btn_sesion.png");
        assetManager.unload("imagenes/ranking.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("fuentes/texto_super.fnt");
        assetManager.unload("efectos/luz.effect");
        assetManager.unload("strings/strings");
    }

    private void cargarAssets() {
        //loader para efectos de particulas
        ParticleEffectLoader.ParticleEffectParameter effectParameter = new ParticleEffectLoader.ParticleEffectParameter();
        effectParameter.imagesDir = Gdx.files.internal("imagenes");

        assetManager.load("imagenes/fondogolosinas.png", Texture.class);
        assetManager.load("imagenes/menu_btn_up.png", Texture.class);
        assetManager.load("imagenes/menu_btn_down.png", Texture.class);
        assetManager.load("imagenes/btn_sesion.png", Texture.class);
        assetManager.load("imagenes/ranking.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("fuentes/texto_super.fnt", BitmapFont.class);
        assetManager.load("efectos/luz.effect", ParticleEffect.class, effectParameter);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondogolosinas.png");
        txtBtnMenuUp = assetManager.get("imagenes/menu_btn_up.png");
        txtBtnMenuDown = assetManager.get("imagenes/menu_btn_down.png");
        txtBtnSesion = assetManager.get("imagenes/btn_sesion.png");
        txtBtnRanking = assetManager.get("imagenes/ranking.png");
        parEfcLuz = assetManager.get("efectos/luz.effect");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        fntFuenteTitulo = assetManager.get("fuentes/texto_super.fnt");
        strings = assetManager.get("strings/strings");
    }
}
