package org.lab.grageasmagicas.base_de_datos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.SQLiteGdxException;

import org.lab.grageasmagicas.parte_visual.AdministradorPantalla;

public class DBSesion {


    private static final String TABLE_USUARIO = "usuario";
    private static final String COLUMNA_ID = "_id";
    private String COLUMNA_USER = "usuario";
    private String COLUMNA_PASSWORD = "password";
    private Database dbHandler;

    // En DATABASE_CREATE va la cadena que es una sentencia CREATE TABLE de SQLite
    private String DATABASE_CREATE;

    public DBSesion(AdministradorPantalla administradorPantalla, Database db) {

        Gdx.app.log("dbSesion", "empezo creacion de db");

        dbHandler = db;

        DATABASE_CREATE = "create table if not exists "
                + TABLE_USUARIO + "(" + COLUMNA_ID
                + " integer primary key autoincrement, " + COLUMNA_USER
                + " integer not null," + COLUMNA_PASSWORD + " integer );";

        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
            dbHandler.execSQL("INSERT INTO " + TABLE_USUARIO + " ( " + COLUMNA_ID + ", " + COLUMNA_USER + ", " + COLUMNA_PASSWORD + ") VALUES ( " + 4 + ", " + 4 + " , " + 1234 + " )");

        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        Gdx.app.log("dbSesion", "Se creo table");

        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

    }


    public boolean agregarUsuario(String usuario, String pass) {

        DatabaseCursor cursor = null;
        boolean exito = false;

        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        try {
            Gdx.app.log("DBSesion", "SELECT * FROM " + TABLE_USUARIO + " WHERE " + COLUMNA_USER + "=" + usuario);
            cursor = dbHandler.rawQuery("SELECT * FROM " + TABLE_USUARIO + " WHERE " + COLUMNA_USER + "=" + usuario);

        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }


        if (cursor.getCount() == 0) {
            Gdx.app.log("DBPuntaje", "INSERT INTO " + TABLE_USUARIO + " ( " + COLUMNA_USER + ", " + COLUMNA_PASSWORD + ") VALUES ( " + usuario + " , " + pass + " )");

            try {
                dbHandler.execSQL("INSERT INTO " + TABLE_USUARIO + " ( " + COLUMNA_USER + ", " + COLUMNA_PASSWORD + ") VALUES ( " + usuario + " , " + pass + " )");
                exito = true;
            } catch (SQLiteGdxException e) {
                e.printStackTrace();
            }
        }

        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        return exito;

    }

    public int verificarSesion(String usuario, String pass) {
        DatabaseCursor cursor = null;
        int valido = -1;


        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        try {
            Gdx.app.log("DBSesion", "SELECT * FROM " + TABLE_USUARIO + " WHERE " + COLUMNA_USER + "=" + usuario + " AND " + COLUMNA_PASSWORD + "=" + pass);
            cursor = dbHandler.rawQuery("SELECT * FROM " + TABLE_USUARIO + " WHERE " + COLUMNA_USER + "=" + usuario + " AND " + COLUMNA_PASSWORD + "=" + pass);

        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }


        //dbHandler = null;
        Gdx.app.log("DatabaseTest", "dispose");

        if (cursor.getCount() == 1) {
            cursor.next();
            valido = Integer.parseInt(String.valueOf(cursor.getString(0)));
        }


        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        return valido;
    }

    public boolean verificarUsuario(String usuario) {

        DatabaseCursor cursor = null;
        boolean existe = false;

        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }


        try {
            Gdx.app.log("DBSesion", "SELECT * FROM " + TABLE_USUARIO + " WHERE " + COLUMNA_USER + "=" + usuario);
            cursor = dbHandler.rawQuery("SELECT * FROM " + TABLE_USUARIO + " WHERE " + COLUMNA_USER + "=" + usuario);

        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        existe = (cursor.getCount() == 1);

        return existe;

    }

    public static String getTableUsuario() {
        return TABLE_USUARIO;
    }

    public static String getColumnaID() {
        return COLUMNA_ID;
    }

    public String getColumnaNombre() {
        return COLUMNA_USER;
    }

    public String getColumnaPuntaje() {
        return COLUMNA_PASSWORD;
    }


    public String getDatabaseCreate() {
        return DATABASE_CREATE;
    }


}
