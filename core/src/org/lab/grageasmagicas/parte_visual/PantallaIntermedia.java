package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Screen;

public class PantallaIntermedia implements Screen {
    private AdministradorPantalla adminPantalla;
    private int pantallaNueva;

    public PantallaIntermedia(AdministradorPantalla adminPantalla, int pantallaACrear) {
        this.adminPantalla = adminPantalla;
        pantallaNueva = pantallaACrear;
    }

    @Override
    public void show() {
        switch (pantallaNueva) {
            case 1:
                adminPantalla.setScreen(new MenuPrincipal(adminPantalla));
                break;
            case 2:
                adminPantalla.setScreen(new JuegoVisual(adminPantalla));
                break;
            case 3:
                adminPantalla.setScreen(new MenuRegistrarse(adminPantalla));
                break;
            case 4:
                adminPantalla.setScreen(new MenuLogin(adminPantalla));
                break;
            case 5:
                adminPantalla.setScreen(new MenuOpciones(adminPantalla));
                break;
            case 6:
                adminPantalla.setScreen(new MenuAcercaDe(adminPantalla));
                break;
            case 7:
                adminPantalla.setScreen(new MenuComoJugar(adminPantalla));
                break;
            case 8:
                adminPantalla.setScreen(new MenuRanking(adminPantalla));
                break;
            default:
                adminPantalla.setScreen(new MenuPrincipal(adminPantalla));
        }
    }


    @Override
    public void render(float delta) {

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
