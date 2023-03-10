package com.soha.foodplanner.ui.common.presenter.factory;


import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.common.presenter.Presenter;
import com.soha.foodplanner.ui.common.presenter.store.PresenterStore;

public class PresenterFactory<P extends Presenter>  {
    private final PresenterStore presenterStore;
    private static PresenterFactory instance;

    PresenterFactory(PresenterStore presenterStore) {
        this.presenterStore = presenterStore;
    }

    @SuppressWarnings("unchecked")
    public static <P extends Presenter> PresenterFactory<P> getInstance(PresenterStore presenterStore) {
        if (instance == null)
            instance = new PresenterFactory<P>(presenterStore);
        return instance;
    }

    public P create(int key, Factory<P> factory) {
        Presenter presenter = presenterStore.get(key);
        if (presenter == null) {
            presenter = factory.create();
            presenterStore.store(key, presenter);
        }

        return (P) presenter;
    }

    public void onDestroy(int key, boolean isChangingConfigurations) {
        if (isChangingConfigurations)
            return;

        Presenter presenter = presenterStore.get(key);
        if(presenter==null)
            return;
        presenter.destroy();
        presenterStore.remove(key);

    }
}
