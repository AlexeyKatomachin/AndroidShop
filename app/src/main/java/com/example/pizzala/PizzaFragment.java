package com.example.pizzala;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.pizzala.helper.PizzalaDatabaseHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PizzaFragment extends Fragment implements CaptionedImageAdapter.Listener {
    private List<Pizzas> pizzas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pizza, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.pizzaRecycleView);
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                view.findViewById(R.id.pizzaProgressBar).setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                pizzas = Pizzas.getPizzasList(getContext());
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                recyclerView.setAdapter(new CaptionedImageAdapter(pizzas, PizzaFragment.this));

                view.findViewById(R.id.pizzaProgressBar).setVisibility(ProgressBar.INVISIBLE);
            }
        }.execute();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), PizzaDetail.class);
        intent.putExtra(PizzaDetail.PIZZA_ID, position);
        requireContext().startActivity(intent);
    }

    public static class Pizzas {
        private String name;
        private int imgId;
        private String description;
        private int favorite;
        private int id;

        public static final List<Pizzas> pizzas = Arrays
                .asList(new Pizzas("Cheeses", R.drawable.cheespizza,"Homemade pizza crust may sound difficult to you. Why waste the time when you can just buy frozen pizza dough? Frozen pizza dough is definitely more convenient, but homemade pizza crust has a delicious flavor and texture that only comes from YOU!"),
                        new Pizzas("Pepperoni", R.drawable.pepperonipizza,"This Homemade Pepperoni Pizza has everything you want—a great crust, gooey cheese, and tons of pepperoni. The secret to great pepperoni flavor? Hide extra under the cheese! Who needs delivery?"),
                        new Pizzas("Gavai", R.drawable.havaipizza,"Classic Hawaiian Pizza combines pizza sauce, cheese, cooked ham, and pineapple. This crowd-pleasing pizza recipe starts with my homemade pizza crust and is finished with a sprinkle of crispy bacon. It’s salty, sweet, cheesy, and undeniably delicious!"));

        public Pizzas(String name, int imgId, String description) {
            this.name = name;
            this.imgId = imgId;
            this.description = description;
        }

        public Pizzas(String name, int imgId, String description, int favorite, int id) {
            this.name = name;
            this.imgId = imgId;
            this.description = description;
            this.favorite = favorite;
            this.id = id;
        }

        public static List<Pizzas> getPizzasList (Context context) {
            PizzalaDatabaseHelper db = new PizzalaDatabaseHelper(context);
            List<Pizzas> pizzas = db.getPizzasList();
            if (pizzas.isEmpty()){
                init(context);
                pizzas = db.getPizzasList();
            }
            return pizzas;
        }

        private static void init(Context context) {
            PizzalaDatabaseHelper db = new PizzalaDatabaseHelper(context);
            db.insertFood("Cheeses", R.drawable.cheespizza,"Homemade pizza crust may sound difficult to you. Why waste the time when you can just buy frozen pizza dough? Frozen pizza dough is definitely more convenient, but homemade pizza crust has a delicious flavor and texture that only comes from YOU!");
            db.insertFood("Pepperoni", R.drawable.pepperonipizza,"This Homemade Pepperoni Pizza has everything you want—a great crust, gooey cheese, and tons of pepperoni. The secret to great pepperoni flavor? Hide extra under the cheese! Who needs delivery?");
            db.insertFood("Gavai", R.drawable.havaipizza,"Classic Hawaiian Pizza combines pizza sauce, cheese, cooked ham, and pineapple. This crowd-pleasing pizza recipe starts with my homemade pizza crust and is finished with a sprinkle of crispy bacon. It’s salty, sweet, cheesy, and undeniably delicious!");
            db.close();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}