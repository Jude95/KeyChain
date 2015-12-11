package com.jude.keychain.presentation.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jude.keychain.R;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/11/7.
 */
public class FitSystemWindowsFrameLayout extends FrameLayout{
    private boolean mPaddingStatusBar;
    private boolean mPaddingNavigationBar;


    public FitSystemWindowsFrameLayout(Context context) {
        super(context);
        init();
    }

    public FitSystemWindowsFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public FitSystemWindowsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.fit_system_windows);
        try {
            mPaddingStatusBar = a.getBoolean(R.styleable.fit_system_windows_padding_status, false);
            mPaddingNavigationBar = a.getBoolean(R.styleable.fit_system_windows_padding_navigation, false);
        } finally {
            a.recycle();
        }
    }

    private void init(){
        int statusBarHeight = 0;
        int navigationBarHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            if (mPaddingStatusBar) statusBarHeight = JUtils.getStatusBarHeight();
            if (mPaddingNavigationBar) navigationBarHeight = JUtils.getNavigationBarHeight();
        }
        setPadding(0,statusBarHeight,0,navigationBarHeight);
    }
}
