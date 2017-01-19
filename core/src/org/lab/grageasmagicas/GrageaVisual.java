package org.lab.grageasmagicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class GrageaVisual {

    private int tipo;
    private ImageButton gragea;
    private ImageButton.ImageButtonStyle gragea0Style;
    private ImageButton.ImageButtonStyle gragea1Style;
    private ImageButton.ImageButtonStyle gragea2Style;
    private ImageButton.ImageButtonStyle gragea3Style;
    private ImageButton.ImageButtonStyle gragea4Style;

    public GrageaVisual(int nuevoTipo) {
        tipo = nuevoTipo;
        cargarImageButtonStyle();
        cambiarEstiloBoton();
    }

    public void cambiarEstiloBoton() {
        if (tipo == 0)
            gragea = new ImageButton(gragea0Style);

        else if (tipo == 1)
            gragea = new ImageButton(gragea1Style);
        else if (tipo == 2)
            gragea = new ImageButton(gragea2Style);
        else if (tipo == 3)
            gragea = new ImageButton(gragea3Style);
        else
            gragea = new ImageButton(gragea4Style);
    }

    public ImageButton getBoton() {
        return gragea;
    }

    public void cargarImageButtonStyle() {

        Texture gragea0Texture = new Texture(Gdx.files.internal("banana.png"));
        Texture gragea1Texture = new Texture(Gdx.files.internal("blueBerry.png"));
        Texture gragea2Texture = new Texture(Gdx.files.internal("cinnamon.png"));
        Texture gragea3Texture = new Texture(Gdx.files.internal("lemon.png"));
        Texture gragea4Texture = new Texture(Gdx.files.internal("greenApple.png"));

        Texture gragea0TextureSeleccionada = new Texture(Gdx.files.internal("banana_seleccionada.png"));
        Texture gragea1TextureSeleccionada = new Texture(Gdx.files.internal("blueBerry_seleccionada.png"));
        Texture gragea2TextureSeleccionada = new Texture(Gdx.files.internal("cinnamon_seleccionada.png"));
        Texture gragea3TextureSeleccionada = new Texture(Gdx.files.internal("lemon_seleccionada.png"));
        Texture gragea4TextureSeleccionada = new Texture(Gdx.files.internal("greenApple_seleccionada.png"));

        Texture gragea0TextureSeleccionadaDoble = new Texture(Gdx.files.internal("banana_seleccionadaDoble.png"));
        Texture gragea1TextureSeleccionadaDoble = new Texture(Gdx.files.internal("blueBerry_seleccionadaDoble.png"));
        Texture gragea2TextureSeleccionadaDoble = new Texture(Gdx.files.internal("cinnamon_seleccionadaDoble.png"));
        Texture gragea3TextureSeleccionadaDoble = new Texture(Gdx.files.internal("lemon_seleccionadaDoble.png"));
        Texture gragea4TextureSeleccionadaDoble = new Texture(Gdx.files.internal("greenApple_seleccionadaDoble.png"));

        Button.ButtonStyle gragea0ButtonStyle = new Button.ButtonStyle();
        gragea0ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(gragea0Texture));
        gragea0ButtonStyle.down = new TextureRegionDrawable(new TextureRegion(gragea0TextureSeleccionada));
        gragea0ButtonStyle.checked = new TextureRegionDrawable(new TextureRegion(gragea0TextureSeleccionadaDoble));

        gragea0Style = new ImageButton.ImageButtonStyle(gragea0ButtonStyle);
        gragea0Style.imageDisabled = new TextureRegionDrawable(new TextureRegion(gragea0Texture));


        Button.ButtonStyle gragea1ButtonStyle = new Button.ButtonStyle();
        gragea1ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(gragea1Texture));
        gragea1ButtonStyle.down = new TextureRegionDrawable(new TextureRegion(gragea1TextureSeleccionada));
        gragea1ButtonStyle.checked = new TextureRegionDrawable(new TextureRegion(gragea1TextureSeleccionadaDoble));

        gragea1Style = new ImageButton.ImageButtonStyle(gragea1ButtonStyle);
        gragea1Style.imageDisabled = new TextureRegionDrawable(new TextureRegion(gragea1Texture));

        Button.ButtonStyle gragea2ButtonStyle = new Button.ButtonStyle();
        gragea2ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(gragea2Texture));
        gragea2ButtonStyle.down = new TextureRegionDrawable(new TextureRegion(gragea2TextureSeleccionada));
        gragea2ButtonStyle.checked = new TextureRegionDrawable(new TextureRegion(gragea2TextureSeleccionadaDoble));

        gragea2Style = new ImageButton.ImageButtonStyle(gragea2ButtonStyle);
        gragea2Style.imageDisabled = new TextureRegionDrawable(new TextureRegion(gragea2Texture));

        Button.ButtonStyle gragea3ButtonStyle = new Button.ButtonStyle();
        gragea3ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(gragea3Texture));
        gragea3ButtonStyle.down = new TextureRegionDrawable(new TextureRegion(gragea3TextureSeleccionada));
        gragea3ButtonStyle.checked = new TextureRegionDrawable(new TextureRegion(gragea3TextureSeleccionadaDoble));

        gragea3Style = new ImageButton.ImageButtonStyle(gragea1ButtonStyle);
        gragea3Style.imageDisabled = new TextureRegionDrawable(new TextureRegion(gragea3Texture));


        Button.ButtonStyle gragea4ButtonStyle = new Button.ButtonStyle();
        gragea4ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(gragea4Texture));
        gragea4ButtonStyle.down = new TextureRegionDrawable(new TextureRegion(gragea4TextureSeleccionada));
        gragea4ButtonStyle.checked = new TextureRegionDrawable(new TextureRegion(gragea4TextureSeleccionadaDoble));

        gragea4Style = new ImageButton.ImageButtonStyle(gragea4ButtonStyle);
        gragea4Style.imageDisabled = new TextureRegionDrawable(new TextureRegion(gragea4Texture));
    }

    public void cambiarTipo(int nuevoTipo) {
        tipo = nuevoTipo;
        cambiarEstiloBoton();
    }
}