package com.jude.keychain.presentation.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.presenter.ProtocolPresenter;
import com.jude.keychain.presentation.widget.PaddingStatusBarFrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/9.
 */
@RequiresPresenter(ProtocolPresenter.class)
public class ProtocolActivity extends BeamBaseActivity<ProtocolPresenter> {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_Container)
    PaddingStatusBarFrameLayout toolbarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        ButterKnife.bind(this);
        setColor();
    }

    private void setColor() {
        int color = Color.getColorByType(KeyModel.getInstance().getDefaultType());
        toolbar.setBackgroundColor(color);
        toolbarContainer.setBackgroundColor(color);
    }
}
