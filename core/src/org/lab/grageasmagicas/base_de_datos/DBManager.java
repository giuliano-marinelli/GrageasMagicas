package org.lab.grageasmagicas.base_de_datos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseFactory;

import org.lab.grageasmagicas.parte_visual.AdministradorPantalla;

public class DBManager {
    private DBPuntaje dbPuntaje;
    private DBSesion dbSesion;
    private AdministradorPantalla administradorPantalla;
    private Database dbHandler;
    private static final String DATABASE_NAME = "DBgrageasmagicas.db";
    private static final int DATABASE_VERSION = 1;

    // En DATABASE_CREATE va la cadena que es una sentencia CREATE TABLE de SQLite
    private String DATABASE_CREATE;

    public DBManager(AdministradorPantalla administradorPantalla) {
        this.administradorPantalla = administradorPantalla;

        Gdx.app.log("dbPuntaje", "empezo creacion de db");
        dbHandler= DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null);

        dbHandler.setupDatabase();

        dbSesion = new DBSesion(administradorPantalla, dbHandler);
        dbPuntaje = new DBPuntaje(administradorPantalla, dbHandler);

    }

    public void insertarPuntaje(int isUser, int puntaje) {
        dbPuntaje.agregarPuntaje(isUser, puntaje);
    }

    public boolean verificarNickname(String nickname) {
        return dbSesion.verificarUsuario(nickname);
    }

    public int verificarSesion(String nickname, String pass) {
        return dbSesion.verificarSesion(nickname, pass);
    }

    public Table mostrarPuntajes() {
        return new Table();//dbPuntaje.mostrarPuntajes();
    }

    public void upgrade(){

    }

}
