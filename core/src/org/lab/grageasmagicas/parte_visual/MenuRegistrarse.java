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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;

import static java.lang.Thread.sleep;

public class MenuRegistrarse implements Screen {

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
    private TextField fieldUser;
    private TextField fieldPassword;
    private TextField fieldPasswordRepeat;
    private ImageTextButton btnSignUp;
    private TextButton btnMensajeError;
    private TextButton btnVolver;
    private Image imgFondo;
    private Table tblSignUp;
    //assets
    private Texture txtFondo;
    private Texture txtBtnMenuUp;
    private Texture txtBtnMenuDown;
    private Texture txtFieldLogin;
    private BitmapFont fntFuenteBase;

    public MenuRegistrarse(AdministradorPantalla adminPantalla) {
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

        TextureRegionDrawable trBtnMenuUp = new TextureRegionDrawable(new TextureRegion(txtBtnMenuUp));
        TextureRegionDrawable trBtnMenuDown = new TextureRegionDrawable(new TextureRegion(txtBtnMenuDown));
        ImageTextButton.ImageTextButtonStyle btnStlMenu = new ImageTextButton.ImageTextButtonStyle(
                trBtnMenuUp, trBtnMenuDown, trBtnMenuUp, fntFuenteBase
        );

        TextureRegionDrawable trFieldLogin = new TextureRegionDrawable(new TextureRegion(txtFieldLogin));

        TextField.TextFieldStyle fieldStlLogin = new TextField.TextFieldStyle();
        fieldStlLogin.font = fntFuenteBase;
        fieldStlLogin.fontColor = Color.ORANGE;
        fieldStlLogin.background = trFieldLogin;

        fieldUser = new TextField("", fieldStlLogin);
        fieldUser.setMessageText(strings.get("field_user"));
        fieldUser.setAlignment(1);
        fieldUser.setWidth(500);
        fieldUser.setHeight(100);

        fieldPassword = new TextField("", fieldStlLogin);
        fieldPassword.setMessageText(strings.get("field_password"));
        fieldPassword.setAlignment(1);
        fieldPassword.setWidth(500);
        fieldPassword.setHeight(100);
        fieldPassword.setPasswordCharacter('*');
        fieldPassword.setPasswordMode(true);

        fieldPasswordRepeat = new TextField("", fieldStlLogin);
        fieldPasswordRepeat.setMessageText(strings.get("field_password_repeat"));
        fieldPasswordRepeat.setAlignment(1);
        fieldPasswordRepeat.setWidth(500);
        fieldPasswordRepeat.setHeight(100);
        fieldPasswordRepeat.setPasswordCharacter('*');
        fieldPasswordRepeat.setPasswordMode(true);

        TextButton.TextButtonStyle btnStlMensajeError = new TextButton.TextButtonStyle();
        btnStlMensajeError.font = fntFuenteBase;
        btnStlMensajeError.fontColor = Color.RED;
        btnMensajeError = new TextButton("", btnStlMensajeError);

        btnSignUp = new ImageTextButton(strings.get("btn_crear_cuenta"), btnStlMenu);
        btnSignUp.getLabel().setFontScale(1.5f, 1.5f);
        btnSignUp.setWidth(btnSignUp.getPrefWidth());
        btnSignUp.setHeight(btnSignUp.getPrefHeight());
        btnSignUp.pad(25);
        btnSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String usuario = fieldUser.getText();
                String contrasena = fieldPassword.getText();
                String contrasenaRep = fieldPasswordRepeat.getText();

                if (!usuario.equals("") && !contrasena.equals("") && !contrasenaRep.equals("")) {
                    boolean existe = adminPantalla.getInterfazDb().existeUsuario(usuario);
                    if (!existe) {
                        if (contrasena.equals(contrasenaRep)) {
                            adminPantalla.getInterfazDb().crearUsuario(usuario, contrasena);
                            int idUsuario = adminPantalla.getInterfazDb().iniciarSesion(usuario, contrasena);
                            adminPantalla.setIdUsuario(idUsuario);
                            adminPantalla.setSesion(true);
                            adminPantalla.setScreen(new PantallaIntermedia(adminPantalla, adminPantalla.getMenuPrincipal()));
                        } else {
                            btnMensajeError.setText(strings.get("btn_msj_error_pass_dif"));
                        }
                    } else {
                        btnMensajeError.setText(strings.get("btn_msj_error_nom_user"));
                    }
                } else {
                    btnMensajeError.setText(strings.get("btn_msj_error_campos_obl"));
                }
            }
        });


        tblSignUp = new Table();
        tblSignUp.row().padTop(50);
        Table internal = new Table();
        internal.row();
        internal.add(fieldUser).width(350).height(75).pad(25);
        internal.row();
        internal.add(fieldPassword).width(350).height(75).pad(25);
        internal.row();
        internal.add(fieldPasswordRepeat).width(350).height(75).pad(25);
        tblSignUp.add(internal);
        tblSignUp.add(btnSignUp);
        tblSignUp.row();
        tblSignUp.add(btnMensajeError).colspan(2);
        tblSignUp.pack();
        tblSignUp.setPosition(anchoCamara / 2 - tblSignUp.getWidth() / 2, altoCamara - tblSignUp.getHeight());
        escena.addActor(tblSignUp);

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
        txtBtnMenuUp.dispose();
        txtBtnMenuDown.dispose();
        txtFieldLogin.dispose();
        fntFuenteBase.dispose();
        escena.dispose();
        //assetManager.clear();
        assetManager.unload("imagenes/fondo_tablero.png");
        assetManager.unload("imagenes/login_btn_up.png");
        assetManager.unload("imagenes/login_btn_down.png");
        assetManager.unload("imagenes/fin_btn_fondo.png");
        assetManager.unload("fuentes/texto_bits.fnt");
        assetManager.unload("strings/strings");
    }

    private void cargarAssets() {
        assetManager.load("imagenes/fondo_tablero.png", Texture.class);
        assetManager.load("imagenes/login_btn_up.png", Texture.class);
        assetManager.load("imagenes/login_btn_down.png", Texture.class);
        assetManager.load("imagenes/fin_btn_fondo.png", Texture.class);
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        assetManager.load("strings/strings", I18NBundle.class);
        assetManager.finishLoading();
        txtFondo = assetManager.get("imagenes/fondo_tablero.png");
        txtBtnMenuUp = assetManager.get("imagenes/login_btn_up.png");
        txtBtnMenuDown = assetManager.get("imagenes/login_btn_down.png");
        txtFieldLogin = assetManager.get("imagenes/fin_btn_fondo.png");
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
        strings = assetManager.get("strings/strings");
    }

}
