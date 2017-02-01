package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lab.grageasmagicas.base_de_datos.InterfazDB;


public class AdministradorPantalla extends Game {

    private int anchoCamara = 1280;
    private int altoCamara = 720;
    private int idUsuario;
    private boolean sesion;
    private Camera camara;
    private Viewport vista;
    private AssetManager assetManager;
    private InterfazDB interfazDb;
    private final int NUMERO_VERSION = 1;

    //valores para usar en pantalla intermedia
    private final int menuPrincipal = 1;
    private final int juegoVisual = 2;
    private final int menuRegistrarse = 3;
    private final int menuLogin = 4;
    private final int menuOpciones = 5;
    private final int menuAcercaDe = 6;
    private final int menuComoJugar = 7;
    private final int menuRanking = 8;
    private final int menuNiveles = 9;


    @Override
    public void create() {
        assetManager = new AssetManager();
        interfazDb = new InterfazDB();
        camara = new OrthographicCamera(anchoCamara, altoCamara);
        vista = new StretchViewport(anchoCamara, altoCamara, camara);
        idUsuario = interfazDb.consultarSesion();
        if (idUsuario != -1) {
            sesion = true;
        } else {
            sesion = false;
        }
        setScreen(new MenuPrincipal(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.clear();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public InterfazDB getInterfazDb() {
        return interfazDb;
    }

    public int getAnchoCamara() {
        return anchoCamara;
    }

    public int getAltoCamara() {
        return altoCamara;
    }

    public void setAnchoCamara(int anchoCamara) {
        this.anchoCamara = anchoCamara;
    }

    public void setAltoCamara(int altoCamara) {
        this.altoCamara = altoCamara;
    }

    public Camera getCamara() {
        return camara;
    }

    public void setCamara(Camera camara) {
        this.camara = camara;
    }

    public Viewport getVista() {
        return vista;
    }

    public void setVista(Viewport vista) {
        this.vista = vista;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isSesion() {
        return sesion;
    }

    public void setSesion(boolean sesion) {
        this.sesion = sesion;
    }

    public int getNumVersion() {
        return NUMERO_VERSION;
    }

    public int getMenuLogin() {
        return menuLogin;
    }

    public int getMenuPrincipal() {
        return menuPrincipal;
    }

    public int getMenuRegistrarse() {
        return menuRegistrarse;
    }

    public int getMenuOpciones() {
        return menuOpciones;
    }

    public int getMenuAcercaDe() {
        return menuAcercaDe;
    }

    public int getMenuRanking() {
        return menuRanking;
    }

    public int getJuegoVisual() {
        return juegoVisual;
    }

    public int getMenuComoJugar() {
        return menuComoJugar;
    }

    public int getMenuNiveles() {
        return menuNiveles;
    }
}
