package com.jude.keychain.presentation.app;

import android.app.Activity;
import android.app.Application;

import com.jude.beam.Beam;
import com.jude.beam.bijection.ActivityLifeCycleDelegate;
import com.jude.beam.bijection.ActivityLifeCycleDelegateProvider;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JUtils.initialize(this);
        JUtils.setDebug(true, "KeyChain");
        Beam.init(this);
        Beam.setActivityLifeCycleDelegateProvider(new ActivityLifeCycleDelegateProvider() {
            @Override
            public ActivityLifeCycleDelegate createActivityLifeCycleDelegate(Activity activity) {
                return new ActivityDelegate(activity);
            }
        });
    }
}
