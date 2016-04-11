package com.jude.keychain.presentation.ui;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.presentation.presenter.ProtocolPresenter;

import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/9.
 */
@RequiresPresenter(ProtocolPresenter.class)
public class ProtocolActivity extends BeamBaseActivity<ProtocolPresenter> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        ButterKnife.bind(this);
    }


}
