package com.jude.keychain.presentation.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.jude.beam.bijection.ActivityLifeCycleDelegate;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fitsystemwindowlayout.FitSystemWindowsFrameLayout;
import com.jude.fitsystemwindowlayout.FitSystemWindowsLinearLayout;
import com.jude.fitsystemwindowlayout.FitSystemWindowsRelativeLayout;
import com.jude.keychain.BuildConfig;
import com.jude.keychain.R;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.value.Color;
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
        if (!BuildConfig.DEBUG)
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

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
        int color = Color.getColorByType(KeyModel.getInstance().getDefaultType());
        if (((BeamBaseActivity)getActivity()).getToolbar()!=null){
            ((BeamBaseActivity)getActivity()).getToolbar().setBackgroundColor(color);
        }
        View view = getActivity().findViewById(R.id.container);
        if (view!=null){
            if (view instanceof FitSystemWindowsFrameLayout){
                ((FitSystemWindowsFrameLayout) view).setStatusBarColor(color);
            }else if(view instanceof FitSystemWindowsLinearLayout) {
                ((FitSystemWindowsLinearLayout) view).setStatusBarColor(color);
            }else if (view instanceof FitSystemWindowsRelativeLayout){
                ((FitSystemWindowsRelativeLayout) view).setStatusBarColor(color);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        getActivity().finish();
    }
}
