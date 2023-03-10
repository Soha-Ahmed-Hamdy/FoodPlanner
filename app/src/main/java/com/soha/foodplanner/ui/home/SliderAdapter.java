package com.soha.foodplanner.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.ui.common.AddToFavourite;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<MinMeal> minMeals;
    private final AddToFavourite addToFavourite;


    Context context;

    public SliderAdapter(List<MinMeal> minMeals, AddToFavourite addToFavourite) {

        this.minMeals = minMeals;
        this.addToFavourite = addToFavourite;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(parent
                        .getContext())
                .inflate(R.layout.inspiration_item
                        , parent
                        , false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        holder.setMealImage(minMeals.get(position).getThumbnailUrl());
        holder.setMealName(minMeals.get(position).getName());
        if (minMeals.get(position).isFavoured())
            holder.favIcon.setImageResource(R.drawable.fav_checked);


        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToFavourite.addFavouriteMeal(minMeals.get(position).getId());
                holder.favIcon.setImageResource(R.drawable.fav_checked);
            }
        });

        holder.sliderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(HomeFragmentDirections
                        .actionHomeFragmentToMealDetails(minMeals.get(position).getId(),null));

            }
        });


    }

    @Override
    public int getItemCount() {
        return minMeals.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mealImage, favIcon;
        private TextView mealName;
        private ConstraintLayout sliderLayout;


        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImage = itemView.findViewById(R.id.imageViewThumbnail);
            mealName = itemView.findViewById(R.id.textViewName);
            favIcon = itemView.findViewById(R.id.imageButtonFavourite);
            sliderLayout = itemView.findViewById(R.id.slider_item_layout);
        }

        void setMealName(String name) {
            mealName.setText(name);
        }

        void setMealImage(String img) {
            Glide.with(mealImage.getContext()).load(img).into(mealImage);
        }

    }


}
