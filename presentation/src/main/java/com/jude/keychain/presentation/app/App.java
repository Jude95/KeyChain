package com.jude.keychain.presentation.app;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.jude.beam.Beam;
import com.jude.keychain.BuildConfig;
import com.jude.keychain.R;
import com.jude.keychain.domain.value.PreferenceKey;
import com.jude.utils.JUtils;

import java.io.File;

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
        String image = JUtils.getSharedPreference().getString(PreferenceKey.KEY_WALLPAPER,null);
        if (image!=null){
            Glide.with(this).load(new File(image)).error(R.drawable.bg_lock).into(JUtils.getScreenWidth(),JUtils.getScreenHeight());
        }


    }


}
