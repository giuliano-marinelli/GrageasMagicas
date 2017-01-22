package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class AdministradorPantalla extends Game {

    private int anchoCamara = 1280;
    private int altoCamara = 720;
    private Camera camara;
    private Viewport vista;
    private AssetManager assetManager = new AssetManager();

    @Override
    public void create() {
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
}
