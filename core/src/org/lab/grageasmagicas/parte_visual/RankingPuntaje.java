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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RankingPuntaje implements Screen {


    private AdministradorPantalla adminPantalla;
    private int anchoCamara;
    private int altoCamara;
    private Viewport vista;
    private AssetManager assetManager;
    private Stage escena;


    private  String TABLE_RANKING = "ranking";
    private  String COLUMNA_PUESTO = "_id";
    private  String COLUMNA_NOMBRE = "nombre";
    private  String COLUMNA_PUNTAJE = "puntaje";
    private TextField ingresoNombre;
    private TextButton.TextButtonStyle btnAgregarNombreStyle;
    private Dialog ventanaDialogo;
    private TextButton btnAgregarNombre;
    private Texture txtFondo;
    private BitmapFont fntFuenteBase;
    private int puntaje;
    private TextButton.TextButtonStyle btnStlVolver;

    public RankingPuntaje(AdministradorPantalla adminPantalla, int nuevoPuntaje){
            this.adminPantalla = adminPantalla;
            this.anchoCamara = adminPantalla.getAnchoCamara();
            this.altoCamara = adminPantalla.getAltoCamara();
            this.vista = adminPantalla.getVista();
            this.assetManager = adminPantalla.getAssetManager();
            this.puntaje=nuevoPuntaje;

            cargarAssets();
            escena = new Stage(vista);
            Gdx.input.setInputProcessor(escena);

        btnStlVolver = new TextButton.TextButtonStyle();
        btnStlVolver.font = fntFuenteBase;
    }

    @Override
    public void show() {

        Image imgFondo = new Image(txtFondo);
        imgFondo.setScale(anchoCamara / imgFondo.getWidth(), altoCamara / imgFondo.getHeight());
        escena.addActor(imgFondo);

                        TextField.TextFieldStyle ingresoNombreStyle= new TextField.TextFieldStyle();
                        ingresoNombreStyle.font = fntFuenteBase;
                        ingresoNombreStyle.fontColor = new Color(Color.PINK.r, Color.PINK.g, Color.PINK.b, Color.PINK.a);


                        //Este textfield es el que el usuario tiene que completar con su nombre para subirlo a la base de datos
                        //Quiza deberia hacer esto solo si el puntaje esta entre los 10 mas altos, por ahora lo dejo asi para que
                        //sea mas facil testear
                        ingresoNombre=new TextField("Nombre",ingresoNombreStyle);
                        ingresoNombre.setPosition(anchoCamara / 2, altoCamara * 0.4f);


                        //btnAgregarNombre es el boton que aprieta el usuario cuando termina de escribir su nombre
                        btnAgregarNombreStyle = new TextButton.TextButtonStyle();
                        btnAgregarNombreStyle.font = fntFuenteBase;
                        btnAgregarNombreStyle.fontColor = new Color(Color.PINK.r, Color.PINK.g, Color.PINK.b, Color.PINK.a);

                        Window.WindowStyle ventanaDialogoStl = new Window.WindowStyle();
                        ventanaDialogoStl.titleFont = fntFuenteBase;
                        ventanaDialogoStl.titleFontColor = new Color(Color.PINK.r, Color.PINK.g, Color.PINK.b, Color.PINK.a);

                        ///CAMBIAR FONDO POR OTRO
                        //TextureRegionDrawable imgFondo = new TextureRegionDrawable( new TextureRegion(txtFondo));
                        //ventanaDialogoStl.background = imgFondo;


                        //Esta es la ventana que aparece cuando el usuario aprieta VOLVER para que agregue su nombre al puntaje
                        //Quiza deberia aparecer solo cuando el puntaje este entre los 10 mas altos, por ahora lo dejo asi para testear
                        ventanaDialogo = new Dialog("Ingrese su nombre", ventanaDialogoStl);
                        ventanaDialogo.setSize(25,25);

                        ventanaDialogo.getContentTable().row();
                        ventanaDialogo.getContentTable().add(ingresoNombre).width(135);
                        escena.setKeyboardFocus(ingresoNombre);

                        TextButton btnAgregarNombre = new TextButton("OK", btnAgregarNombreStyle);

                        //Este listener muestra lo que pasa cuando el usuario termina de escribir su nombre y presiona btnAgregarNombre
                        btnAgregarNombre.addListener(new ClickListener() {
                            //Gdx.app.log("Listener btnAgregarNombre", "entro");

                            ////////////INICIO LISTENER BOTON AGREGAR NOMBRE A PUNTAJE(DENTRO DE VENTANA)///////
                            @Override
                            public void clicked(InputEvent event, float x, float y) {

                                //Este constructor crea la base de datos solo si no esta creada. Si esta creada, la prepara para ser usada
                                DBPuntaje db = new DBPuntaje(TABLE_RANKING, COLUMNA_NOMBRE, COLUMNA_PUNTAJE, adminPantalla);

                                db.agregarPuntaje(ingresoNombre.getText(), puntaje);

                                ingresoNombre.setVisible(false);
                                //btnAgregarNombre.setVisible(false);

                                Table contenedorRanking = db.mostrarPuntajes();


                                escena.addActor(contenedorRanking);




                            }});
                        ////////////FIN LISTENER BOTON AGREGAR NOMBRE A PUNTAJE(DENTRO DE VENTANA)///////

        TextButton btnMenu = new TextButton("MENU", btnAgregarNombreStyle);
        btnMenu.getLabel().setFontScale(2, 2);
        btnMenu.setWidth(btnMenu.getPrefWidth());
        btnMenu.setHeight(btnMenu.getPrefHeight());
        btnMenu.setPosition(anchoCamara - btnMenu.getWidth() - 50, altoCamara - btnMenu.getHeight() - 25);
        btnMenu.addListener(new ClickListener() {
            ////////////INICIO LISTENER BTNMENU///////
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Presiono btnMenu");

                //Gdx.gl.glClearColor(1, 1, 1, 1);
                //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                adminPantalla.setScreen(new MenuPrincipal(adminPantalla));

            }
        });



                        ventanaDialogo.button(btnAgregarNombre);
                        escena.addActor(ventanaDialogo);
                        escena.addActor(btnMenu);
                        ventanaDialogo.show(escena);


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.act(delta);
        escena.setViewport(vista);
        escena.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override

    public void dispose() {
        txtFondo.dispose();

        fntFuenteBase.dispose();
        escena.dispose();;
        assetManager.unload("imagenes/fin_btn_fondo.png");
        assetManager.unload("fuentes/texto_bits.fnt");

    }

    public void cargarAssets() {

        assetManager.load("imagenes/fondo.jpg", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);

        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondo.jpg");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");

    }

}
