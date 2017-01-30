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
                        "contrasena TEXT NOT NULL, " +
                        "nivel_logrado INTEGER DEFAULT 0" +
                        ");";

        String crearPuntaje =
                "CREATE TABLE IF NOT EXISTS puntaje (" +
                        "id_puntaje INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "valor INTEGER NOT NULL, " +
                        "id_usuario INTEGER REFERENCES usuario(id_usuario)" +
                        ");";

        String crearSesion =
                "CREATE TABLE IF NOT EXISTS sesion (" +
                        "id_usuario INTEGER PRIMARY KEY REFERENCES usuario(id_usuario)" +
                        ");";

        String crearOpciones =
                "CREATE TABLE IF NOT EXISTS opciones (" +
                        "id_opciones INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_usuario INTEGER REFERENCES usuario(id_usuario), " +
                        "vibracion BOOLEAN DEFAULT true, " +
                        "sonido BOOLEAN DEFAULT true, " +
                        "musica BOOLEAN DEFAULT true" +
                        ");";

        dbAdministrador = DatabaseFactory.getNewDatabase(dbNombre, dbVersion, null, null);
        dbAdministrador.setupDatabase();

        try {
            dbAdministrador.openOrCreateDatabase();
            dbAdministrador.execSQL(crearUsuario);
            dbAdministrador.execSQL(crearPuntaje);
            dbAdministrador.execSQL(crearSesion);
            dbAdministrador.execSQL(crearOpciones);
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        //consultarRanking();
        crearUsuario("yuyo", "123");
        crearUsuario("tibu", "123");
        crearUsuario("ines", "123");
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

    //devuelve el nombre que corresponde tal idUsuario, si no lo encuentra devuelve cadena vacia
    public String consultarNombreUsuario(int idUsuario) {
        String nombreUsuario = "";
        try {
            dbAdministrador.openOrCreateDatabase();
            String query = "SELECT nombre FROM usuario WHERE id_usuario=" + idUsuario;
            DatabaseCursor respuesta = dbAdministrador.rawQuery(query);
            while (respuesta.next()) {
                nombreUsuario = respuesta.getString(0);
            }
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return nombreUsuario;
    }

    //devuelve el nivel logrado por el usuario, si no lo encuentra devuelve -1
    public int consultarNivelLogrado(int idUsuario) {
        int nivelLogrado = -1;
        try {
            dbAdministrador.openOrCreateDatabase();
            String query = "SELECT nivel_logrado FROM usuario WHERE id_usuario=" + idUsuario;
            DatabaseCursor respuesta = dbAdministrador.rawQuery(query);
            while (respuesta.next()) {
                nivelLogrado = respuesta.getInt(0);
            }
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return nivelLogrado;
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
            if (idUsuario != -1) {
                modificarSesion(idUsuario);
            }
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return idUsuario;
    }

    //crea o modifica la sesion existente con el nuevo idUsuario que se va a loguear
    public void modificarSesion(int idUsuario) {
        try {
            String query;
            if (consultarSesion() != -1) {
                query = "UPDATE sesion SET id_usuario=" + idUsuario;
            } else {
                query = "INSERT INTO sesion(id_usuario) VALUES (" + idUsuario + ")";
            }
            dbAdministrador.openOrCreateDatabase();
            dbAdministrador.execSQL(query);
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    //borra el dato de sesion
    public void borrarSesion() {
        try {
            dbAdministrador.openOrCreateDatabase();
            String query = "DELETE FROM sesion";
            dbAdministrador.execSQL(query);
            dbAdministrador.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    //si hay sesion devuelve el idUsuario correspondiente, sino devuelve -1
    public int consultarSesion() {
        int idUsuario = -1;
        try {
            dbAdministrador.openOrCreateDatabase();
            String query = "SELECT * FROM sesion";
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
