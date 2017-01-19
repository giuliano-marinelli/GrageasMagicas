package org.lab.grageasmagicas;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GrageaVisualListener extends InputListener {

    public GrageaVisual grageavisual;
    public Gragea grageaLogica;
    public TableroVisual tablero;

    public GrageaVisualListener(GrageaVisual gv, Gragea gl, TableroVisual tv){

       grageavisual=gv;
        grageaLogica=gl;
        tablero=tv;



    }

    //Called when a mouse button or a finger touch goes down on the actor.

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        super.touchDown(event, x, y, pointer, button);


        return true;
    }

    // Called when a mouse button or a finger touch goes up anywhere,
    // but only if touchDown previously returned true for the mouse button or touch.
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
    }
}
