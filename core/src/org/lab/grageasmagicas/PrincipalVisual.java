package org.lab.grageasmagicas;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PrincipalVisual extends ApplicationAdapter {

    private Stage escena;
    private Texture texturaFondo;
    private Texture texturaBtnJugar;
    private Texture texturaBtnOpciones;
    private Texture texturaBtnAcercaDe;
    private Texture texturaTitulo;
    private final AssetManager assetManager = new AssetManager();

    @Override
    public void create() {
        cargarTexturas();

        escena = new Stage();

        Gdx.input.setInputProcessor(escena);

        float ancho = Gdx.graphics.getWidth();
        float alto = Gdx.graphics.getHeight();

        System.out.println(ancho+","+alto);

        Image imgFondo = new Image(texturaFondo);
        float escalaX = ancho / imgFondo.getWidth();
        float escalaY = alto / imgFondo.getHeight();
        imgFondo.setScale(escalaX,escalaY);

        escena.addActor(imgFondo);

        TextureRegionDrawable trBtnJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
        ImageButton btnJugar = new ImageButton(trBtnJugar);
        btnJugar.setPosition(ancho/2, alto*0.6f);
        escena.addActor(btnJugar);

        TextureRegionDrawable trBtnOpciones = new TextureRegionDrawable(new TextureRegion(texturaBtnOpciones));
        ImageButton btnOpciones = new ImageButton(trBtnOpciones);
        btnOpciones.setPosition(ancho/2, alto*0.4f);
        escena.addActor(btnOpciones);

        TextureRegionDrawable trBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(texturaBtnAcercaDe));
        ImageButton btnAcercaDe = new ImageButton(trBtnAcercaDe);
        btnAcercaDe.setPosition(ancho/2, alto*0.2f);
        escena.addActor(btnAcercaDe);

        Image imgTitulo = new Image(texturaTitulo);
        imgTitulo.setPosition(ancho/2 - imgTitulo.getWidth()/2, alto*0.8f);
        escena.addActor(imgTitulo);

        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click","Presiono jugar");
            }
        });

        btnOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click","Presiono opciones");
            }
        });

        btnAcercaDe.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click","Presiono acerca de");
            }
        });
    }

    private void cargarTexturas() {
        assetManager.load("fondo.jpg", Texture.class);
        assetManager.load("jugar.png", Texture.class);
        assetManager.load("opciones.png", Texture.class);
        assetManager.load("acerca_de.png", Texture.class);
        assetManager.load("titulo.png", Texture.class);

        assetManager.finishLoading();

        texturaFondo = assetManager.get("fondo.jpg");
        texturaBtnJugar = assetManager.get("jugar.png");
        texturaBtnOpciones = assetManager.get("opciones.png");
        texturaBtnAcercaDe = assetManager.get("acerca_de.png");
        texturaTitulo = assetManager.get("titulo.png");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.draw();
    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaBtnJugar.dispose();
        texturaBtnOpciones.dispose();
        texturaBtnAcercaDe.dispose();
        texturaTitulo.dispose();
        escena.dispose();
    }
}
