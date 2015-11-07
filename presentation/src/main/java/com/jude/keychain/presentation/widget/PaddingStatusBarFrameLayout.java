package com.jude.keychain.presentation.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/11/7.
 */
public class PaddingStatusBarFrameLayout extends FrameLayout{
    public PaddingStatusBarFrameLayout(Context context) {
        super(context);
        init();
    }

    public PaddingStatusBarFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaddingStatusBarFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            int height = JUtils.getStatusBarHeight();
            setPadding(0,height,0,0);
        }
    }
}
