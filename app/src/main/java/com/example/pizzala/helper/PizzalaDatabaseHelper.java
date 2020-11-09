package com.example.pizzala.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pizzala.PizzaFragment;

import java.util.ArrayList;
import java.util.List;

public class PizzalaDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pizzala";
    private static final int DB_VERSION = 3;

    public PizzalaDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FOOD ("
        + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "NAME TEXT, "
        + "DESCRIPTION TEXT, "
        + "IMAGE_RESOURCE_ID INTEGER,"
        + "FAVORITE INTEGER DEFAULT 0);");
    }

    public void insertFood (String foodTitle, int imgId, String description) {
        ContentValues values = new ContentValues();
        values.put("NAME", foodTitle);
        values.put("DESCRIPTION", description);
        values.put("IMAGE_RESOURCE_ID", imgId);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("FOOD", null, values);
        db.close();
    }

    public void updateFieldsById (int fieldId, ContentValues newData, String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(tableName, newData, "_id = ?", new String[]{Integer.toString(fieldId)});
        db.close();
    }

    public List<PizzaFragment.Pizzas> getPizzasList (){
        List<PizzaFragment.Pizzas> pizzas = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("FOOD",
                new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE", "_id"},
                null, null, null, null, null);
        if (cursor.moveToFirst()){
            pizzas.add(getPizza(cursor));
        }

        while (cursor.moveToNext()) {
            pizzas.add(getPizza(cursor));
        }
        cursor.close();
        return pizzas;
    }

    private PizzaFragment.Pizzas getPizza (Cursor cursor){
        return new PizzaFragment.Pizzas(cursor.getString(0)
                , cursor.getInt(2)
                , cursor.getString(1)
                , cursor.getInt(3)
                , cursor.getInt(4));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            db.execSQL("ALTER TABLE FOOD ADD COLUMN FAVORITE INTEGER DEFAULT 0");
        }
    }
}
