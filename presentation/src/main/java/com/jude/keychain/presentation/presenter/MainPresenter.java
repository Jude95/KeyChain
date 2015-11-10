package com.jude.keychain.presentation.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.ui.KeyDetailActivity;
import com.jude.keychain.presentation.ui.MainActivity;
import com.jude.utils.JUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class MainPresenter extends BeamListActivityPresenter<MainActivity,KeyEntity> {
    public static final int REQUEST_DETAIL = 1001;
    public static final int REQUEST_ADD = 1002;


    @Override
    protected void onCreate(MainActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        try {
            KeyModel.getInstance().loadKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreateView(MainActivity view) {
        super.onCreateView(view);
        getAdapter().setOnItemClickListener(position -> {
            JUtils.Log("position:"+position);
            KeyEntity keyEntity = getAdapter().getItem(position);
            Intent i = new Intent(getView(), KeyDetailActivity.class);
            i.putExtra("id", keyEntity.getId());
            getView().startActivityForResult(i, REQUEST_DETAIL);
        });
        onRefresh();
    }

    public void search(String key){
        if (key.isEmpty()){
            onRefresh();
            return;
        }
        KeyModel.getInstance().registerKeyEntry()
                .flatMap(new Func1<List<KeyEntity>, Observable<List<KeyEntity>>>() {
                    @Override
                    public Observable<List<KeyEntity>> call(List<KeyEntity> keyEntities) {
                        return Observable.from(keyEntities)
                                .filter(keyEntity -> (keyEntity.getName().toLowerCase().contains(key.toLowerCase())
                                        || PinyinHelper.getShortPinyin(keyEntity.getName()).contains(key.toLowerCase())))
                                .toList();
                    }
                })
                .doOnNext(keyEntities -> getView().setCount(keyEntities.size()))
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onRefresh() {
        KeyModel.getInstance().registerKeyEntry()
                .doOnNext(keyEntities -> getView().setCount(keyEntities.size()))
                .doOnNext(new Action1<List<KeyEntity>>() {
                    @Override
                    public void call(List<KeyEntity> keyEntities) {
                        JUtils.Log("Begin");

                        for (KeyEntity keyEntity : keyEntities) {
                            JUtils.Log("ID:"+keyEntity.getId());
                        }
                        JUtils.Log("End");

                    }
                })
                .doOnNext(keyEntities -> Collections.sort(keyEntities, new Comparator<KeyEntity>() {
                    @Override
                    public int compare(KeyEntity lhs, KeyEntity rhs) {
                        int delta1 = lhs.getType() - getColorType();
                        if (delta1 > 0) {
                            delta1 += Color.values().length;
                            delta1 *= -1;
                        }
                        int delta2 = rhs.getType() - getColorType();
                        if (delta2 > 0) {
                            delta2 += Color.values().length;
                            delta2 *= -1;
                        }
                        return delta2 - delta1;
                    }
                }))
                .unsafeSubscribe(getRefreshSubscriber());
    }

    public void unDelete(){
        KeyModel.getInstance().undoDelete();
    }

    public void setColorType(int type){
        KeyModel.getInstance().setDefaultType(type);
        onRefresh();
    }

    public int getColorType(){
        return KeyModel.getInstance().getDefaultType();
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DETAIL && resultCode == Activity.RESULT_OK){
            getView().showDeleteSnackBar();
        }
    }
}
