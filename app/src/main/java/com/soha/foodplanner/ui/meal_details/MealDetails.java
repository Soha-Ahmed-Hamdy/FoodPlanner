package com.soha.foodplanner.ui.meal_details;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.mapper.CategoryMapper;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.remote.dto.MealDto;
import com.soha.foodplanner.data.remote.dto.MealsItem;
import com.soha.foodplanner.data.remote.dto.category.CategoryDto;
import com.soha.foodplanner.data.remote.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.remote.webservice.Webservice;
import com.soha.foodplanner.ui.login.LoginFragmentDirections;
import com.soha.foodplanner.ui.signup.SignUpFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetails extends Fragment {
    TextView mealName,areaName,instructions;
    ImageView mealPhoto;
    YouTubePlayerView mealVideo;
    TheMealDBWebService theMealDBWebService;
    private Button planButton;
    protected NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(MealDetails.this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String mealIdStr=MealDetailsArgs.fromBundle(requireArguments()).getMealId();


        theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
        Single<MealDto> single = theMealDBWebService.getMealDetailsById(Long.parseLong(mealIdStr));
        single
                .subscribeOn(Schedulers.io())
                .map(t->t.getMeals().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(e->Log.e("error",e.getMessage()))
                .subscribe(e->
                        {
                            setMealValues(e,view);
                        }
                        );

        initViews(view);
        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(MealDetailsDirections.actionMealDetailsToDayOfWeekFragment());

            }
        });



    }


    private void initViews(View view) {
        mealName=view.findViewById(R.id.meal_detailed_name);
        areaName=view.findViewById(R.id.meal_detailed_area);
        instructions=view.findViewById(R.id.meal_detailed_instructions);
        mealPhoto=view.findViewById(R.id.meal_img);
        mealVideo=view.findViewById(R.id.video);
        planButton=view.findViewById(R.id.plan_btn);

    }
    private void setMealValues(MealsItem mealsItem,View view){

        mealName.setText(mealsItem.getStrMeal());
        Log.e("error",mealsItem.getStrMeal()+"");
        instructions.setText(mealsItem.getStrInstructions());
        areaName.setText(mealsItem.getStrArea());
        if(mealsItem.getStrYoutube()!=null){
            setVideo(mealsItem);
        }

        Glide.with(view.getContext()).load(mealsItem.getStrMealThumb()).into(mealPhoto);
    }
    private void setVideo(MealsItem mealsItem){
        getLifecycle().addObserver((LifecycleObserver) mealVideo);
        String[] split = mealsItem.getStrYoutube().split("=");

        mealVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                try{
                    String videoId = split[1];
                    youTubePlayer.loadVideo(videoId, 0);

                }catch (Exception e){
                    Log.e("TAG", "onReady: "+ e.getMessage() );
                }

            }
        });
    }
}