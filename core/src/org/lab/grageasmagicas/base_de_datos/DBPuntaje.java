package org.lab.grageasmagicas.base_de_datos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

import org.lab.grageasmagicas.parte_visual.AdministradorPantalla;

public class DBPuntaje {


    public  String TABLE_RANKING = "ranking";
    public  String COLUMNA_PUESTO = "_id";
    public  String COLUMNA_NOMBRE = "nombre";
    public  String COLUMNA_PUNTAJE = "puntaje";
    private Database dbHandler;
    private AssetManager assetManager;
    private BitmapFont fntFuenteBase;

    private static final String DATABASE_NAME = "ranking.db";
    private static final int DATABASE_VERSION = 1;

    // En DATABASE_CREATE va la cadena que es una sentencia CREATE TABLE de SQLite
    private String DATABASE_CREATE;

    public DBPuntaje(String nombreTable, String nombreColumnaNombre, String nombreColumnaPuntaje, AdministradorPantalla administradorPantalla) {
        Gdx.app.log("dbPuntaje", "empezo creacion de db");

        TABLE_RANKING = nombreTable;
        COLUMNA_NOMBRE = nombreColumnaNombre;
        COLUMNA_PUNTAJE = nombreColumnaPuntaje;
        assetManager = administradorPantalla.getAssetManager();

        DATABASE_CREATE = "create table if not exists "
                + TABLE_RANKING + "(" + COLUMNA_PUESTO
                + " integer primary key autoincrement, " + COLUMNA_NOMBRE
                + " text not null," + COLUMNA_PUNTAJE  + " int );";


        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null);


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


    public void agregarPuntaje(String nombre, int nuevoPuntaje) {

        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }


        Gdx.app.log("DBPuntaje", "INSERT INTO "+ TABLE_RANKING +" ( "+COLUMNA_NOMBRE+ ") VALUES ( '"+nombre+"' )");
        Gdx.app.log("DBPuntaje", "INSERT INTO "+ TABLE_RANKING +" ( "+COLUMNA_PUNTAJE+ ") VALUES ( "+nuevoPuntaje+" )");

        try {
            dbHandler.execSQL("INSERT INTO "+ TABLE_RANKING +" ( "+COLUMNA_NOMBRE+ ", "+COLUMNA_PUNTAJE+ ") VALUES ( '"+nombre+"' , "+nuevoPuntaje+" )");
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
            Gdx.app.log("DBPuntaje", "SELECT * FROM "+ TABLE_RANKING+" ORDER BY "+COLUMNA_PUNTAJE+ " DESC ");
            cursor = dbHandler.rawQuery("SELECT * FROM "+ TABLE_RANKING+" ORDER BY "+COLUMNA_PUNTAJE+ " DESC ");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }





        Table ranking = new Table();

        TextButton.TextButtonStyle tuplaStl = new TextButton.TextButtonStyle();
        tuplaStl.font = fntFuenteBase;
        tuplaStl.fontColor = new Color(Color.PINK.r,Color.PINK.g, Color.PINK.b, Color.PINK.a );



        ranking.row();

        int i=0;
        while (i<10 && cursor.next()) {

            ranking.add(new TextButton((i+1)+".  "+String.valueOf(cursor.getString(1))+"  "+String.valueOf(cursor.getString(2)), tuplaStl));

            ranking.row();

            ranking.padBottom(5f);
            ranking.setFillParent(true);
            ranking.pack();
            i++;

        }


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
        return COLUMNA_NOMBRE;
    }

    public String getColumnaPuntaje() {
        return COLUMNA_PUNTAJE;
    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    public int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public String getDatabaseCreate() {
        return DATABASE_CREATE;
    }

    private void cargarAssets() {
        assetManager.load("fuentes/texto_bits.fnt", BitmapFont.class);
        fntFuenteBase = assetManager.get("fuentes/texto_bits.fnt");
    }
}
