package com.jude.keychain.data.model;

import com.jude.beam.model.AbsModel;
import com.jude.keychain.domain.entities.KeyEntity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class KeyModel extends AbsModel {

    public static KeyModel getInstance() {
        return getInstance(KeyModel.class);
    }

    public Observable<List<KeyEntity>> readKeyEntryByType(int type){
        return Observable.just(createKeyEntry(20));
    }

    private List<KeyEntity> createKeyEntry(int count){
        List<KeyEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            KeyEntity entity = new KeyEntity();
            entity.setName("淘宝");
            entity.setAccount("15683384295");
            entity.setPassword("ffdsanjsjak");
            entity.setType(i%6);
            list.add(entity);
        }
        return list;
    }
}
