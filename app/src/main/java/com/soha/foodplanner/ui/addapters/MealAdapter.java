package com.soha.foodplanner.ui.addapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.ui.common.AddToFavourite;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private List<MinMeal> minMeals;
    private static final String Tag = "Recycler";
    private OnItemClickListener<MinMeal> onItemClickListener;
    private final AddToFavourite addToFavourite;
    private Context context;


    public MealAdapter(List<MinMeal> minMeals, AddToFavourite addToFavourite) {
        this.minMeals = minMeals;
        this.addToFavourite = addToFavourite;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_meal_item, parent, false);
        context=parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MinMeal meal = minMeals.get(position);

        holder.mealName.setText(meal.getName());

        Glide.with(holder.itemView)
                .load(meal.getThumbnailUrl())
                .into(holder.mealImage);

        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavourite.addFavouriteMeal(minMeals.get(position));
//                homeAdapterPresenter=new HomeAdapterPresenter();
//                homeAdapterPresenter.insertToFav(context,minMeals.get(position));
                holder.favIcon.setImageResource(R.drawable.fav_checked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return minMeals.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mealName;
        private final ImageView mealImage;
        private ImageButton favIcon;

        public ViewHolder(View v) {
            super(v);
            mealName = v.findViewById(R.id.textViewName);
            mealImage = v.findViewById(R.id.imageViewThumbnail);
            favIcon=v.findViewById(R.id.imageButtonFavourite);
        }


    }

}
