package com.jude.keychain.presentation.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.presenter.ExportPresenter;
import com.jude.keychain.presentation.widget.FitSystemWindowsFrameLayout;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/8.
 */
@RequiresPresenter(ExportPresenter.class)
public class ExportActivity extends BeamBaseActivity<ExportPresenter> implements View.OnFocusChangeListener{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_Container)
    FitSystemWindowsFrameLayout toolbarContainer;
    @Bind(R.id.password)
    TextInputLayout password;
    @Bind(R.id.copy)
    Button copy;
    @Bind(R.id.head_container)
    FrameLayout headContainer;

    private int mContainerHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        ButterKnife.bind(this);
        setColor();
        copy.setOnClickListener(v -> {
            if (password.getEditText().getText().toString().isEmpty()){
                password.setError(getString(R.string.error_password_empty));
                return;
            }
            getPresenter().export(password.getEditText().getText().toString());
        });
        password.getEditText().setOnFocusChangeListener(this);
        headContainer.post(() -> mContainerHeight = headContainer.getHeight());
    }

    public void showSuccess(){
        JUtils.Toast(getString(R.string.copy_board));
    }

    public void showFailure(){
        JUtils.Toast(getString(R.string.error_export));
    }


    private boolean isShrink = false;
    private ValueAnimator anim;
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(anim == null){
            anim = ValueAnimator.ofInt(mContainerHeight,0);
            anim.setDuration(300);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    headContainer.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    (Integer) animation.getAnimatedValue()));
                }
            });
        }

        if (!isShrink){
            anim.start();
        }else {
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            anim.reverse();
        }
        isShrink = !isShrink;
    }

    private void setColor() {
        int color = Color.getColorByType(KeyModel.getInstance().getDefaultType());
        toolbar.setBackgroundColor(color);
        toolbarContainer.setBackgroundColor(color);
        headContainer.setBackgroundColor(color);
    }
}
