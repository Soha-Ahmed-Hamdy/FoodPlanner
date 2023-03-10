package com.soha.foodplanner.data.data_source.remote.backup;

import com.soha.foodplanner.data.local.entities.PlannedMeals;

import io.reactivex.rxjava3.core.Completable;

public interface BackupStrategy {
    Completable addFavouriteMeal(long mealId);
    Completable addPlannedMeal(long id, long date, String mealTime);

    Completable deleteFavouriteMeal(long mealId);

    Completable deleteFromPlannedMeal(long id);
}
