package com.jude.keychain.presentation.presenter;

import android.app.Activity;
import android.content.Intent;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.presentation.ui.KeyDetailActivity;
import com.jude.keychain.presentation.ui.MainActivity;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class MainPresenter extends BeamListActivityPresenter<MainActivity,KeyEntity> {
    public static final int REQUEST_DETAIL = 1001;
    public static final int REQUEST_ADD = 1002;


    @Override
    protected void onCreateView(MainActivity view) {
        super.onCreateView(view);
        KeyModel.getInstance().readKeyEntryByType(0)
                .doOnNext(keyEntities -> getView().setCount(keyEntities.size()))
                .unsafeSubscribe(getRefreshSubscriber());

        getAdapter().setOnItemClickListener(position -> {
            KeyEntity keyEntity = getAdapter().getItem(position);
            Intent i = new Intent(getView(), KeyDetailActivity.class);
            i.putExtra("id", keyEntity.getId());
            getView().startActivityForResult(i, REQUEST_DETAIL);
        });
    }


    public void unDelete(){
        KeyModel.getInstance().undoDelete();
    }


    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL && resultCode == Activity.RESULT_OK){
            getView().showDeleteSnackBar();
        }
    }
}
