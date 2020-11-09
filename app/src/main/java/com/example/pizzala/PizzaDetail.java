package com.example.pizzala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pizzala.helper.PizzalaDatabaseHelper;

import java.util.Objects;

public class PizzaDetail extends AppCompatActivity {
    public static final String PIZZA_ID = "pizza_id";
    private PizzaFragment.Pizzas pizzas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        Toolbar to = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(to);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            final int pizzaId = (Integer) intent.getExtras().get(PIZZA_ID);
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected void onPreExecute() {
                    findViewById(R.id.pizzaDetailProgressBar).setVisibility(ProgressBar.VISIBLE);
                }

                @Override
                protected Boolean doInBackground(Void... voids) {
                    pizzas = PizzaFragment.Pizzas.getPizzasList(getApplicationContext()).get(pizzaId);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    if (aBoolean){
                        ((TextView) findViewById(R.id.pizzaTitle)).setText(pizzas.getName());
                        ImageView image = (ImageView) findViewById(R.id.pizzaImage);
                        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), pizzas.getImgId());
                        image.setImageDrawable(drawable);
                        image.setContentDescription(pizzas.getName());
                        ((TextView) findViewById(R.id.pizzaDescription)).setText(pizzas.getDescription());
                        ((CheckBox) findViewById(R.id.pizzaFavoriteCB)).setChecked(pizzas.getFavorite() == 1);
                        findViewById(R.id.pizzaDetailProgressBar).setVisibility(ProgressBar.INVISIBLE);
                    }
                }
            }.execute();
        }
    }

    public void onClickIsFavorite(View view) {
        int favorite;
        if (((CheckBox) findViewById(R.id.pizzaFavoriteCB)).isChecked()){
            favorite = 1;
        } else {
            favorite = 0;
        }
        final ContentValues values = new ContentValues();
        values.put("FAVORITE", favorite);
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                PizzalaDatabaseHelper db = new PizzalaDatabaseHelper(PizzaDetail.this);
                db.updateFieldsById(pizzas.getId(), values, "FOOD");
                db.close();
                return true;
            }
        }.execute();
    }
}