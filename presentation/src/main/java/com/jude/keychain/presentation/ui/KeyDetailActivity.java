package com.jude.keychain.presentation.ui;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.presentation.presenter.KeyDetailPresenter;

/**
 * Created by zhuchenxi on 15/11/4.
 */
public class KeyDetailActivity extends BeamDataActivity<KeyDetailPresenter,KeyEntity> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
