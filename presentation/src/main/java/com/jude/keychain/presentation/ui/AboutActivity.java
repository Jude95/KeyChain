package com.jude.keychain.presentation.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.presenter.AboutPresenter;
import com.jude.keychain.presentation.widget.FitSystemWindowsFrameLayout;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/9.
 */
@RequiresPresenter(AboutPresenter.class)
public class AboutActivity extends BeamBaseActivity<AboutPresenter> {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_Container)
    FitSystemWindowsFrameLayout toolbarContainer;
    @Bind(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setColor();
        version.setText(getString(R.string.app_name)+" v"+ JUtils.getAppVersionName());
    }


    private void setColor() {
        int color = Color.getColorByType(KeyModel.getInstance().getDefaultType());
        toolbar.setBackgroundColor(color);
        toolbarContainer.setBackgroundColor(color);
    }
}
