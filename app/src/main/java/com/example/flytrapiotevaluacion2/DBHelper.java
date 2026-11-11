
package com.example.flytrapiotevaluacion2;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flytrap_db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE trampas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "codigo_sector TEXT NOT NULL, " +
                "tipo_trampa TEXT NOT NULL, " +
                "nivel_captura INTEGER NOT NULL, " +
                "observaciones TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS trampas");
        onCreate(db);
    }

    // Insertar una nueva trampa
    public boolean insertarTrampa(String codigo, String tipo, int nivel, String observaciones) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("codigo_sector", codigo);
        valores.put("tipo_trampa", tipo);
        valores.put("nivel_captura", nivel);
        valores.put("observaciones", observaciones);

        long resultado = db.insert("trampas", null, valores);

        db.close();

        return resultado != -1;
    }

    // Obtener todas las trampas registradas
    public List<Trampa> obtenerTrampas() {

        List<Trampa> lista = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM trampas", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id"));

                String codigo = cursor.getString(
                        cursor.getColumnIndexOrThrow("codigo_sector"));

                String tipo = cursor.getString(
                        cursor.getColumnIndexOrThrow("tipo_trampa"));

                int nivel = cursor.getInt(
                        cursor.getColumnIndexOrThrow("nivel_captura"));

                String observaciones = cursor.getString(
                        cursor.getColumnIndexOrThrow("observaciones"));

                Trampa trampa = new Trampa(
                        id, codigo, tipo, nivel, observaciones);

                lista.add(trampa);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }

    // Actualizar una trampa por su ID
    public boolean actualizarTrampa(int id, String codigo, String tipo,
                                    int nivel, String observaciones) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("codigo_sector", codigo);
        valores.put("tipo_trampa", tipo);
        valores.put("nivel_captura", nivel);
        valores.put("observaciones", observaciones);

        int resultado = db.update(
                "trampas",
                valores,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return resultado > 0;
    }

    // Eliminar una trampa por su ID
    public boolean eliminarTrampa(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int resultado = db.delete(
                "trampas",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return resultado > 0;
    }

    // Obtener una trampa por su ID
    public Trampa obtenerTrampaPorId(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM trampas WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        Trampa trampa = null;

        if (cursor.moveToFirst()) {

            String codigo = cursor.getString(
                    cursor.getColumnIndexOrThrow("codigo_sector"));

            String tipo = cursor.getString(
                    cursor.getColumnIndexOrThrow("tipo_trampa"));

            int nivel = cursor.getInt(
                    cursor.getColumnIndexOrThrow("nivel_captura"));

            String observaciones = cursor.getString(
                    cursor.getColumnIndexOrThrow("observaciones"));

            trampa = new Trampa(
                    id, codigo, tipo, nivel, observaciones);
        }

        cursor.close();
        db.close();

        return trampa;
    }
}
