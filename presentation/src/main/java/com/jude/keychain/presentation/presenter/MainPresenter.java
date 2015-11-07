package com.jude.keychain.presentation.presenter;

import android.app.Activity;
import android.content.Intent;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.presentation.ui.KeyDetailActivity;
import com.jude.keychain.presentation.ui.MainActivity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class MainPresenter extends BeamListActivityPresenter<MainActivity,KeyEntity> {
    public static final int REQUEST_DETAIL = 1001;
    public static final int REQUEST_ADD = 1002;

    private int mType = -1;
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

    @Override
    public void onRefresh() {
        KeyModel.getInstance().readKeyEntry()
                .doOnNext(keyEntities -> getView().setCount(keyEntities.size()))
                .flatMap(new Func1<List<KeyEntity>, Observable<List<KeyEntity>>>() {
                    @Override
                    public Observable<List<KeyEntity>> call(List<KeyEntity> keyEntities) {
                        return Observable.from(keyEntities)
                                .filter(keyEntity -> mType==-1||keyEntity.getType() == mType)
                                .toList();
                    }
                })
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
