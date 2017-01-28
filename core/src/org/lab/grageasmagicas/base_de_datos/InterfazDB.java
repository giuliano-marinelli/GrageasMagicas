package org.lab.grageasmagicas.base_de_datos;

import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

import java.util.ArrayList;

public class InterfazDB {

    private String dbNombre;
    private int dbVersion;
    private Database dbAdministrador;

    public InterfazDB() {
        dbNombre = "GrageasMagicasDB.sqlite";
        dbVersion = 1;

        String crearUsuario =
                "CREATE TABLE IF NOT EXISTS usuario (" +
                        "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL, " +
                        "contrasena TEXT NOT NULL" +
                        ");";

        String crearPuntaje =
                "CREATE TABLE IF NOT EXISTS puntaje (" +
                        "id_puntaje INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "valor INTEGER NOT NULL, " +
                        "id_usuario INTEGER REFERENCES usuario(id_usuario)" +
                        ");";

        dbAdministrador = DatabaseFactory.getNewDatabase(dbNombre, dbVersion, null, null);
        dbAdministrador.setupDatabase();

        try {
            dbAdministrador.openOrCreateDatabase();
            dbAdministrador.execSQL(crearUsuario);
            dbAdministrador.execSQL(crearPuntaje);
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        //consultarRanking();
        crearUsuario("yuyo","123");
        crearUsuario("tibu","123");
        crearUsuario("ines","123");
    }

    public void insertarPuntaje(int idUsuario, int puntaje) {
        try {
            dbAdministrador.openOrCreateDatabase();
            String query = "INSERT INTO puntaje(id_usuario, valor) VALUES (" + idUsuario + "," + puntaje + ")";
            dbAdministrador.execSQL(query);
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public boolean crearUsuario(String nombre, String contrasena) {
        boolean exito = false;
        try {
            if (!existeUsuario(nombre)) {
                dbAdministrador.openOrCreateDatabase();
                String query = "INSERT INTO usuario(nombre, contrasena) VALUES ('" + nombre + "','" + contrasena + "')";
                dbAdministrador.execSQL(query);
                dbAdministrador.closeDatabase();
                exito = true;
            }
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return exito;
    }

    //verifica que el usuario y contraseña esten en la db y si es asi envia el id correspondiente
    //al mismo, caso contrario devuelve -1
    public int iniciarSesion(String nombre, String contrasena) {
        int idUsuario = -1;
        try {
            dbAdministrador.openOrCreateDatabase();
            String query = "SELECT * FROM usuario WHERE nombre='" + nombre + "' AND contrasena='" + contrasena + "'";
            DatabaseCursor respuesta = dbAdministrador.rawQuery(query);
            while (respuesta.next()) {
                idUsuario = respuesta.getInt(0);
            }
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return idUsuario;
    }

    //verifica que el nombre de usuario recibido no este registrado en la db
    public boolean existeUsuario(String nombre) {
        boolean res = true;
        try {
            dbAdministrador.openOrCreateDatabase();
            String query = "SELECT * FROM usuario WHERE nombre='" + nombre + "'";
            DatabaseCursor respuesta = dbAdministrador.rawQuery(query);
            if (respuesta.getCount() > 0) {
                res = true;
            } else {
                res = false;
            }
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return res;
    }

    //devuelve un arreglo con los 10 usuarios con puntaje mas alto junto con su puntaje
    public ArrayList<String[]> consultarRanking() {
        ArrayList<String[]> ranking = new ArrayList();
        try {
            dbAdministrador.openOrCreateDatabase();
            String query = "SELECT * FROM puntaje NATURAL JOIN usuario ORDER BY valor DESC";
            DatabaseCursor respuesta = dbAdministrador.rawQuery(query);
            int i = 0;
            String nombre;
            String puntaje;
            while (respuesta.next() && i < 10) {
                nombre = respuesta.getString(3);
                puntaje = respuesta.getString(1);
                String[] fila = {nombre, puntaje};
                ranking.add(fila);
                i++;
            }
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return ranking;
    }

}
