package com.google.sample.cloudvision.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.google.sample.cloudvision.model.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas Marchi on 15/11/2017.
 */

public class AnimalDao extends SQLiteOpenHelper {

    public AnimalDao(Context context) {
        super(context, "AnimalDex", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Animals(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, descricao TEXT, nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Animals;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Animal animal) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAnimal(animal);

        db.insert("Animals", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoAnimal(Animal animal) {
        ContentValues dados = new ContentValues();
        dados.put("nome", animal.getNome());
        dados.put("descricao", animal.getDescricao());
        dados.put("nota", animal.getNota());
        return dados;
    }

    public List<Animal> buscaAnimals() {
        String sql = "SELECT * FROM Animals;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Animal> animals = new ArrayList<Animal>();

        while (c.moveToNext()){
            Animal animal = new Animal();
            animal.setId(c.getLong(c.getColumnIndex("id")));
            animal.setNome(c.getString(c.getColumnIndex("nome")));
            animal.setDescricao(c.getString(c.getColumnIndex("descricao")));
            animal.setNota(c.getDouble(c.getColumnIndex("nota")));

            animals.add(animal);
        }
        c.close();

        return animals;
    }

    public String buscaAnimalPorNome(String nomeAnimal) {
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {nomeAnimal};
        Cursor c = db.query("Animals", null, "nome = ?", params, null, null, null);
        String descricao = null;
        while (c.moveToNext()){
            descricao = c.getString(c.getColumnIndex("descricao"));
        }
        c.close();

        return descricao;

    }

    public void deleta(Animal animal) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {animal.getId().toString()};
        db.delete("Animals", "id = ?", params);
    }

    public void altera(Animal animal) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues dados = pegaDadosDoAnimal(animal);
        String[] params = {animal.getId().toString()};
        db.update("Animals", dados, "id = ?", params);
    }
}
