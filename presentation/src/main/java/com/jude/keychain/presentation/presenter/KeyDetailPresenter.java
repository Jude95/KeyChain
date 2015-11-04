package com.jude.keychain.presentation.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.presentation.ui.KeyDetailActivity;

/**
 * Created by zhuchenxi on 15/11/4.
 */
public class KeyDetailPresenter extends BeamDataActivityPresenter<KeyDetailActivity,KeyEntity> {
    private int id;

    @Override
    protected void onCreate(KeyDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id",-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        publishObject(KeyModel.getInstance().getKeyById(id));
    }

    public void delete(){
        KeyModel.getInstance().delete(id);
        getView().setResult(Activity.RESULT_OK);
        getView().finish();
    }
}
