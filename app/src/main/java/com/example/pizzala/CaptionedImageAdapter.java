package com.example.pizzala;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CaptionedImageAdapter extends RecyclerView.Adapter<CaptionedImageAdapter.ViewHolder>  {
    private List<PizzaFragment.Pizzas> pizzasList;
    private Listener listener;

    public CaptionedImageAdapter(List<PizzaFragment.Pizzas> pizzasList, Listener listener) {
        this.pizzasList = pizzasList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image,parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        PizzaFragment.Pizzas pizzas = pizzasList.get(position);
        ImageView image = (ImageView) cardView.findViewById(R.id.info_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), pizzas.getImgId());
        image.setImageDrawable(drawable);
        image.setContentDescription(pizzas.getName());
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(pizzas.getName());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzasList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;


        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            this.cardView = itemView;
        }
    }

    public interface Listener {
        void onClick (int position);
    }
}
