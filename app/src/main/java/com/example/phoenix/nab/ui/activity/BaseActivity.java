package com.example.phoenix.nab.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {


//    Navigator navigator;

//    private UserComponent userComponent;

    public static void start(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getApplicationComponent().inject(this);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent}
     */
//    protected ApplicationComponent getApplicationComponent() {
//        return ((AndroidApplication) getApplication()).getApplicationComponent();
//    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule}
     */
//    protected ActivityModule getActivityModule() {
//        return new ActivityModule(this);
//    }

//    protected void initializeInjector() {
//        this.userComponent = DaggerUserComponent.builder()
//                .applicationComponent(getApplicationComponent())
//                .activityModule(getActivityModule())
//                .build();
//
//    }

//    public UserComponent getUserComponent() {
//        return userComponent;
//    }
}
