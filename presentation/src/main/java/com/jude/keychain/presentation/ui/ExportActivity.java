package com.jude.keychain.presentation.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.presenter.ExportPresenter;
import com.jude.keychain.presentation.widget.PaddingStatusBarFrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/8.
 */
@RequiresPresenter(ExportPresenter.class)
public class ExportActivity extends BeamBaseActivity<ExportPresenter> {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_Container)
    PaddingStatusBarFrameLayout toolbarContainer;
    @Bind(R.id.password)
    TextInputLayout password;
    @Bind(R.id.copy)
    Button copy;
    @Bind(R.id.head_container)
    FrameLayout headContainer;
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
    }

    public void showSuccess(){
        Snackbar.make(copy, R.string.copy_board, Snackbar.LENGTH_SHORT).show();
    }

    public void showFailure(){
        Snackbar.make(copy , R.string.error_export,Snackbar.LENGTH_SHORT).show();
    }


    private void setColor() {
        int color = Color.getColorByType(KeyModel.getInstance().getDefaultType());
        toolbar.setBackgroundColor(color);
        toolbarContainer.setBackgroundColor(color);
        headContainer.setBackgroundColor(color);
    }
}
