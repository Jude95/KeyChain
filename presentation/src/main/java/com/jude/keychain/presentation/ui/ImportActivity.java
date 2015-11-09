package com.jude.keychain.presentation.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.presenter.ImportPresenter;
import com.jude.keychain.presentation.widget.PaddingStatusBarFrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/8.
 */
@RequiresPresenter(ImportPresenter.class)
public class ImportActivity extends BeamBaseActivity<ImportPresenter> {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_Container)
    PaddingStatusBarFrameLayout toolbarContainer;
    @Bind(R.id.password)
    TextInputLayout password;
    @Bind(R.id.ok)
    Button ok;
    @Bind(R.id.content)
    TextInputLayout content;
    @Bind(R.id.head_container)
    FrameLayout headContainer;

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
    }
    public void showSuccess(){
        Snackbar.make(ok, R.string.add_success, Snackbar.LENGTH_SHORT).show();
    }

    public void showFailure(){
        password.setError(getString(R.string.error_password));
    }

    private void setColor() {
        int color = Color.getColorByType(KeyModel.getInstance().getDefaultType());
        toolbar.setBackgroundColor(color);
        toolbarContainer.setBackgroundColor(color);
        headContainer.setBackgroundColor(color);
    }
}
