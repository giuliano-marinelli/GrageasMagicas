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
    private int idUser;
    private boolean session;
    private String user;
    private Camera camara;
    private Viewport vista;
    private AssetManager assetManager;
    private InterfazDB interfazDb;
    private final int NUMERO_VERSION = 1;

    @Override
    public void create() {
        assetManager = new AssetManager();
        interfazDb = new InterfazDB();
        session = false;
        camara = new OrthographicCamera(anchoCamara, altoCamara);
        vista = new StretchViewport(anchoCamara, altoCamara, camara);
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isSession() {
        return session;
    }

    public void setSession(boolean session) {
        this.session = session;
    }

    public int getNumVersion(){
        return NUMERO_VERSION;
    }
}
