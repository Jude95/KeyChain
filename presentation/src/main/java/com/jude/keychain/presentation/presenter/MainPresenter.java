package com.jude.keychain.presentation.presenter;

import android.app.Activity;
import android.content.Intent;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.ui.KeyDetailActivity;
import com.jude.keychain.presentation.ui.MainActivity;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class MainPresenter extends BeamListActivityPresenter<MainActivity,KeyEntity> {
    public static final int REQUEST_DETAIL = 1001;
    public static final int REQUEST_ADD = 1002;

    private int mType = 0;
    private List<KeyEntity> mData;
    @Override
    protected void onCreateView(MainActivity view) {
        super.onCreateView(view);
        getAdapter().setOnItemClickListener(position -> {
            KeyEntity keyEntity = getAdapter().getItem(position);
            Intent i = new Intent(getView(), KeyDetailActivity.class);
            i.putExtra("id", keyEntity.getId());
            getView().startActivityForResult(i, REQUEST_DETAIL);
        });
        onRefresh();
    }

    public void search(String key){
        KeyModel.getInstance().readKeyEntry()
                .doOnNext(keyEntities -> getView().setCount(keyEntities.size()))
                .doOnNext(keyEntities1 -> mData = keyEntities1)
                .doOnNext(keyEntities -> Collections.sort(keyEntities, Collator.getInstance(java.util.Locale.CHINA)))
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onRefresh() {
        KeyModel.getInstance().readKeyEntry()
                .doOnNext(keyEntities -> getView().setCount(keyEntities.size()))
                .doOnNext(keyEntities1 -> mData = keyEntities1)
                .doOnNext(keyEntities -> Collections.sort(keyEntities, new Comparator<KeyEntity>() {
                    @Override
                    public int compare(KeyEntity lhs, KeyEntity rhs) {
                        int delta1 = lhs.getType()-mType;
                        if (delta1>0){
                            delta1+= Color.values().length;
                            delta1*=-1;
                        }
                        int delta2 = rhs.getType()-mType;
                        if (delta2>0){
                            delta2+= Color.values().length;
                            delta2*=-1;
                        }
                        return delta2-delta1;
                    }
                }))
                .unsafeSubscribe(getRefreshSubscriber());
    }

    public void unDelete(){
        KeyModel.getInstance().undoDelete();
    }

    public void setColorType(int type){
        this.mType = type;
        onRefresh();
    }

    public int getColorType(){
        return mType;
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL && resultCode == Activity.RESULT_OK){
            getView().showDeleteSnackBar();
        }
    }
}
