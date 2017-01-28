package org.lab.grageasmagicas.base_de_datos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.SQLiteGdxException;

import org.lab.grageasmagicas.parte_visual.AdministradorPantalla;

public class DBPuntaje {


    private String TABLE_RANKING = "ranking";
    private String COLUMNA_PUESTO = "_id";
    private String COLUMNA_ID_USUARIO = "_idUsuario";
    private String COLUMNA_PUNTAJE = "puntaje";
    private Database dbHandler;
    private AssetManager assetManager;
    private BitmapFont fntFuenteBase;


    // En DATABASE_CREATE va la cadena que es una sentencia CREATE TABLE de SQLite
    private String DATABASE_CREATE;


    public DBPuntaje(AdministradorPantalla administradorPantalla, Database db) {


        assetManager = administradorPantalla.getAssetManager();
        dbHandler=db;

        DATABASE_CREATE = "create table if not exists "
                + TABLE_RANKING + "(" + COLUMNA_PUESTO
                + " integer primary key autoincrement, " + COLUMNA_ID_USUARIO
                + " integer not null," + COLUMNA_PUNTAJE + " integer " +
                "foreign key " + COLUMNA_ID_USUARIO + " references " + DBSesion.getTableUsuario() + "(" + DBSesion.getColumnaID() + "));";

        Gdx.app.log("database create dbpuntaje", DATABASE_CREATE);


        dbHandler.setupDatabase();
        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        Gdx.app.log("dbPuntaje", "Se creo db");

        cargarAssets();

    }


    public void agregarPuntaje(int idUsuario, int nuevoPuntaje) {

        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }


        Gdx.app.log("DBPuntaje", "INSERT INTO " + TABLE_RANKING + " ( " + COLUMNA_ID_USUARIO + ", " + COLUMNA_PUNTAJE + ") VALUES ( '" + idUsuario + "' , " + nuevoPuntaje + " )");


        try {
            dbHandler.execSQL("INSERT INTO " + TABLE_RANKING + " ( " + COLUMNA_ID_USUARIO + ", " + COLUMNA_PUNTAJE + ") VALUES ( '" + idUsuario + "' , " + nuevoPuntaje + " )");
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }


    }

    public Table mostrarPuntajes() {
        DatabaseCursor cursor = null;


        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        try {
            Gdx.app.log("asql", "SELECT * FROM " + TABLE_RANKING + " r INNER JOIN "+DBSesion.getTableUsuario()+" u on (r."+COLUMNA_PUESTO+" = u."+DBSesion.getColumnaID()+") ORDER BY " + COLUMNA_PUNTAJE + " DESC ");
            cursor = dbHandler.rawQuery("SELECT * FROM " + TABLE_RANKING + " r INNER JOIN "+DBSesion.getTableUsuario()+" u on (r."+COLUMNA_PUESTO+" = u."+DBSesion.getColumnaID()+") ORDER BY " + COLUMNA_PUNTAJE + " DESC ");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        Gdx.app.log("join ", "cursor: "+cursor.getCount());

        Table ranking = new Table();

        TextButton.TextButtonStyle tuplaStl = new TextButton.TextButtonStyle();
        tuplaStl.font = fntFuenteBase;
        tuplaStl.fontColor = Color.PINK;


        ranking.row();

        int i = 0;
        while (i < 10 && cursor.next()) {

            ranking.add(new TextButton((i + 1) + ".  " + String.valueOf(cursor.getString(4)) + "  " + String.valueOf(cursor.getString(2)), tuplaStl));

            ranking.row();
            i++;

        }

        ranking.padBottom(5f);
        ranking.setFillParent(true);
        ranking.pack();


        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        dbHandler = null;
        Gdx.app.log("DatabaseTest", "dispose");

        return ranking;
    }

    public String getTableRanking() {
        return TABLE_RANKING;
    }

    public String getColumnaPuesto() {
        return COLUMNA_PUESTO;
    }

    public String getColumnaNombre() {
        return COLUMNA_ID_USUARIO;
    }

    public String getColumnaPuntaje() {
        return COLUMNA_PUNTAJE;
    }

    public String getDatabaseCreate() {
        return DATABASE_CREATE;
    }

    private void cargarAssets() {
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
    }
}
