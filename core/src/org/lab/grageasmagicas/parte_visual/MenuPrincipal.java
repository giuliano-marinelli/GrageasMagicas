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

        //Esta instancia de la clase stage es la encargada de recibir los eventos de input y coordinar que los reciban
        //los actores correspondientes(por ej, un botón). Para esto requiere ser usada como argumento del
        //método setInputProcessor
        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);

        //la clase SpriteBatch sirve de intermediario entre el código y Open Graphics Library, la especificación estándar de
        // una interfaz que permite producir gráficos en Android

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
        //El botón jugar le da el control de la pantalla a una instancia de PantallaIntermedia que a su vez
        // le da el control a una instancia de MenuNiveles, que consulta SQLite para
        //encontrar el nivel logrado por el usuario y permite elegir al usuario qué nivel quiere jugar
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuModoJuego()));
            }
        });
        escena.addActor(btnJugar);

        //El botón Opciones le da el control de la pantalla a una instancia de Pantalla Intermedia, que a su vez le da el
        //control de la pantalla a una instancia de MenuOpciones,  que permite configurar opciones de sonido y vibración
        // y leer las reglas del juego.
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

        //El botón Opciones le da el control de la pantalla a una instancia de Pantalla Intermedia, que a su vez le da el
        //control de la pantalla a una instancia de MenuAcercaDe,  que muestra información sobre la aplicación y sus
        // desarrolladores.
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

        //Si la variable boolean Sesion de adminPantalla es false, la instancia de PantallaIntermedia que crea el botón Sesión
        //le da el control de la pantalla a una instancia de MenuLogin, que permite iniciar sesión con usuario y contraseña
        // o dirigirse al menú de registro de cuenta.
        // Si es true, cierra  la sesión de usuario usando el método borrarSesion de interfazDb, que modifica
        // la tabla sesión, setea las variables de adminPantalla de sesion en false y de idUsuario en -1 y
        // le da el control de la pantalla a la instancia de MenuPrincipal


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
            //Si la variable sesion de adminPantalla es true, se muestra un mensaje de bienvenida con el nombre
            //del usuario en el idioma correspondiente. Para eso se usa el método consultarNombreUsuario de interfazDb
            //que tiene la query correspondiente para conseguir el nombre correspondiente al id de usuario
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

        //El botón Ranking permite consultar los 10  puntajes más altos dandole el control de la pantalla a una instancia de
        //PantallaIntermedia que a su vez le da el control a una instancia de MenuRanking. MenuRanking consulta los puntajes
        //más altos a través del método consultarRanking de interfazDb, que contiene una query a la tabla Ranking.

        btnRanking = new ImageButton(trBtnRanking);
        btnRanking.setPosition(50, 100 + btnSesion.getHeight());
        btnRanking.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuRanking()));
            }
        });
        escena.addActor(btnRanking);

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
    //El método render genera las imágenes necesarias(fondo, botones, etc)
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
