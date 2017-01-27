package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import static java.lang.Thread.sleep;

public class TestEffect implements Screen {
    //visual
    private int anchoCamara;
    private int altoCamara;
    //administradores
    private AssetManager assetManager;
    private AdministradorPantalla adminPantalla;
    private Viewport vista;
    private Stage escena;
    private SpriteBatch batch;
    //assets
    private Texture txtFondo;
    private ParticleEffect parEfcExplosion;
    //actors
    Image imgFondo;
    //efectos
    private ParticleEffectPool parEfcPoolExplosion;
    private Array<ParticleEffectPool.PooledEffect> actEfcExplosion;
    private ParticleEffectPool.PooledEffect poolEfcExplosion;

    public TestEffect(AdministradorPantalla adminPantalla) {
        this.adminPantalla = adminPantalla;
        this.anchoCamara = adminPantalla.getAnchoCamara();
        this.altoCamara = adminPantalla.getAltoCamara();
        this.vista = adminPantalla.getVista();
        this.assetManager = adminPantalla.getAssetManager();

        cargarAssets();

        escena = new Stage(vista);
        Gdx.input.setInputProcessor(escena);

        batch = new SpriteBatch();
        parEfcPoolExplosion = new ParticleEffectPool(parEfcExplosion, 25, 100);
        actEfcExplosion = new Array<ParticleEffectPool.PooledEffect>();
    }

    private void cargarAssets() {
        ParticleEffectLoader.ParticleEffectParameter effectParameter = new ParticleEffectLoader.ParticleEffectParameter();
        effectParameter.imagesDir = Gdx.files.internal("imagenes");
        assetManager.load("efectos/explosion.effect", ParticleEffect.class, effectParameter);
        assetManager.load("imagenes/fondogolosinas.png", Texture.class);
        assetManager.finishLoading();
        parEfcExplosion = assetManager.get("efectos/explosion.effect");
        txtFondo = assetManager.get("imagenes/fondogolosinas.png");
    }

    @Override
    public void show() {
        imgFondo = new Image(txtFondo);
        imgFondo.setScale(anchoCamara / imgFondo.getWidth(), altoCamara / imgFondo.getHeight());
        escena.addActor(imgFondo);
        imgFondo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                poolEfcExplosion = parEfcPoolExplosion.obtain();
                poolEfcExplosion.setPosition(anchoCamara / 2, altoCamara / 2);
                actEfcExplosion.add(poolEfcExplosion);
            }
        });

        //Test con Efecto extends Actor
        /*Efecto efecto = new Efecto(parEfcExplosion);
        efecto.setPosition(anchoCamara / 2, altoCamara / 2);
        escena.addActor(efecto);
        efecto.start();*/

        //Test efecto basico
        /*batch = new SpriteBatch();
        parEfcExplosion = new ParticleEffect();
        parEfcExplosion.load(Gdx.files.internal("efectos/parEfcExplosion.parEfcExplosion"), Gdx.files.internal("imagenes"));
        parEfcExplosion.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        parEfcExplosion.start();*/
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        escena.act(delta);
        escena.setViewport(vista);
        escena.draw();

        batch.begin();
        float deltaTime = Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(adminPantalla.getCamara().combined);
        for (int i = 0; i < actEfcExplosion.size; ) {
            ParticleEffectPool.PooledEffect effect = actEfcExplosion.get(i);
            if (effect.isComplete()) {
                parEfcPoolExplosion.free(effect);
                actEfcExplosion.removeIndex(i);
            } else {
                effect.draw(batch, deltaTime);
                ++i;
            }
        }
        batch.end();

        /*batch.begin();
        parEfcExplosion.draw(batch, delta);
        batch.end();
        if (parEfcExplosion.isComplete()) {
            parEfcExplosion.reset();
        }*/
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

    }
}
