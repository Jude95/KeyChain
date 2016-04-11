package com.jude.keychain.presentation.app;

import android.app.Application;

import com.jude.beam.Beam;
import com.jude.keychain.BuildConfig;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JUtils.initialize(this);
        JUtils.setDebug(BuildConfig.DEBUG, "KeyChain");
        Beam.init(this);
        Beam.setActivityLifeCycleDelegateProvider(ActivityDelegate::new);
    }


}
