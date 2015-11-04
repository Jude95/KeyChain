package com.jude.keychain.presentation.app;

import android.app.Activity;
import android.os.Bundle;

import com.jude.beam.bijection.ActivityLifeCycleDelegate;
import com.jude.swipbackhelper.SwipeBackHelper;

/**
 * Created by Mr.Jude on 2015/9/9.
 */
public class ActivityDelegate extends ActivityLifeCycleDelegate {

    public ActivityDelegate(Activity act) {
        super(act);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(getActivity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(getActivity());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(getActivity());
    }
}
