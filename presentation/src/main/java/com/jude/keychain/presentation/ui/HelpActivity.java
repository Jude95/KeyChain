package com.jude.keychain.presentation.ui;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.presentation.presenter.HelpPresenter;

/**
 * Created by Mr.Jude on 2015/11/9.
 */
@RequiresPresenter(HelpPresenter.class)
public class HelpActivity extends BeamBaseActivity<HelpPresenter> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

}
