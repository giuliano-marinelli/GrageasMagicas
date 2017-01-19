package org.lab.grageasmagicas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class GrageasMagicasGame extends Game {
    @Override
    public void create() {


        setScreen(new PrincipalVisual(this));
    }

    public void borrarPantalla(){
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }

}
