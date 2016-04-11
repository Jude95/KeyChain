package com.jude.keychain.presentation.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.jude.keychain.presentation.presenter.ImportPresenter;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/8.
 */
@RequiresPresenter(ImportPresenter.class)
public class ImportActivity extends BeamBaseActivity<ImportPresenter> implements View.OnFocusChangeListener{

    @Bind(R.id.password)
    TextInputLayout password;
    @Bind(R.id.ok)
    Button ok;
    @Bind(R.id.content)
    TextInputLayout content;
    @Bind(R.id.head_container)
    FrameLayout headContainer;

    private int mContainerHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        ButterKnife.bind(this);
        setColor();
        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ok.setOnClickListener(v->{
            if (content.getEditText().getText().toString().isEmpty()){
                content.setError(getString(R.string.error_data_empty));
                return;
            }
            if (password.getEditText().getText().toString().isEmpty()){
                password.setError(getString(R.string.error_password_empty));
                return;
            }
            getPresenter().importData(content.getEditText().getText().toString(),password.getEditText().getText().toString());
        });
        content.getEditText().setOnFocusChangeListener(this);
        password.getEditText().setOnFocusChangeListener(this);
        headContainer.post(() -> mContainerHeight = headContainer.getHeight());
    }
    public void showSuccess(){
        JUtils.Toast(getString(R.string.add_success));
    }

    public void showFailure(){
        password.setError(getString(R.string.error_password));
    }

    private void setColor() {
        int color = Color.getColorByType(KeyModel.getInstance().getDefaultType());
        headContainer.setBackgroundColor(color);
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

        if (!isShrink&&(content.getEditText().hasFocus()||password.getEditText().hasFocus())){
            anim.start();
            isShrink = !isShrink;
        }else if (isShrink&&!content.getEditText().hasFocus()&&!password.getEditText().hasFocus()){
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            anim.reverse();
            isShrink = !isShrink;
        }
    }
}
