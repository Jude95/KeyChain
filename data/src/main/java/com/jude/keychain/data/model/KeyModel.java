package com.jude.keychain.data.model;

import android.content.Context;
import android.support.annotation.Nullable;

import com.jude.beam.model.AbsModel;
import com.jude.keychain.data.tools.Recorder;
import com.jude.keychain.data.tools.SeedManager;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.BehaviorSubject;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class KeyModel extends AbsModel {

    public static final char[] NUMBER = {
            '0','1','2','3','4','5','6','7','8','9'
    };
    public static final char[] LAW_LETTER = {
            '0','1','2','3','4','5','6','7','8','9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z'
    };
    public static final char[] HIGH_LETTER = {
            '0','1','2','3','4','5','6','7','8','9',

            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z',

            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'
    };

    private List<KeyEntity> mData;
    private KeyEntity mLastDelete;
    private BehaviorSubject<List<KeyEntity>> mKeyEntitiesSubject = BehaviorSubject.create();

    public static KeyModel getInstance() {
        return getInstance(KeyModel.class);
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
    }

    public boolean isFirst(){
        return SeedManager.isEmpty();
    }

    public void setSeed(String seed){
        SeedManager.setSeed(seed);
    }

    public boolean checkSeed(String seed){
        return SeedManager.checkSeed(seed);
    }

    public void loadKey() throws Exception {
        mData = Recorder.read();
        if (mData == null)mData = new ArrayList<>();
        Collections.sort(mData, new Comparator<KeyEntity>() {
            @Override
            public int compare(KeyEntity lhs, KeyEntity rhs) {
                return lhs.getId()-rhs.getId();
            }
        });
        mKeyEntitiesSubject.unsafeSubscribe(new Subscriber<List<KeyEntity>>() {
            @Override
            public void onCompleted() {
                JUtils.Log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<KeyEntity> keyEntities) {
                try {
                    Recorder.save(keyEntities);
                } catch (Exception e) {
                    mKeyEntitiesSubject.onError(e);
                }
            }
        });
        mKeyEntitiesSubject.onNext(mData);
    }


    public Observable<List<KeyEntity>> readKeyEntry(){
        return mKeyEntitiesSubject;
    }

    public List<KeyEntity> getData(){
        return mData;
    }

    @Nullable
    public KeyEntity getKeyById(int id){
        for (KeyEntity keyEntity : mData) {
            if (keyEntity.getId() == id){
                return keyEntity.clone();
            }
        }
        return null;
    }

    public boolean containId(int id){
        for (KeyEntity keyEntity : mData) {
            if(keyEntity.getId() == id)return true;
        }
        return false;
    }

    public void createKey(String name,String account,String password,String note,int type){
        KeyEntity keyEntity = new KeyEntity();
        keyEntity.setName(name);
        keyEntity.setTime(System.currentTimeMillis() / 1000);
        keyEntity.setAccount(account);
        keyEntity.setPassword(password);
        keyEntity.setNote(note);
        keyEntity.setId(getNewId());
        keyEntity.setType(type);
        mData.add(keyEntity);
        mKeyEntitiesSubject.onNext(mData);
    }

    public void updateKey(int id,String name,String account,String password,String note,int type){
        for (KeyEntity keyEntity : mData) {
            if (keyEntity.getId() == id){
                keyEntity.setName(name);
                keyEntity.setAccount(account);
                keyEntity.setPassword(password);
                keyEntity.setNote(note);
                keyEntity.setType(type);
            }
        }
        mKeyEntitiesSubject.onNext(mData);
    }

    public void add(List<KeyEntity> data){
        out:for (KeyEntity newEntity : data) {
            for (KeyEntity entity : mData) {
                if (newEntity.equals(entity)){
                    continue out;
                }
            }
            newEntity.setId(getNewId());
            mData.add(newEntity);
        }
        mKeyEntitiesSubject.onNext(mData);
    }

    public void delete(int id){
        for (KeyEntity keyEntity : new ArrayList<>(mData)) {
            if (keyEntity.getId() == id){
                mLastDelete = keyEntity;
                mData.remove(keyEntity);
            }
        }
        mKeyEntitiesSubject.onNext(mData);
    }

    public void undoDelete(){
        mLastDelete.setId(getNewId());
        mData.add(mLastDelete);
        mKeyEntitiesSubject.onNext(mData);
    }

    private int getNewId(){
        int id = 0;
        if (mData.size()>0){
            id = mData.get(mData.size() - 1).getId() + 1;
        }
        return id;
    }

    public int getDefaultType(){
        return JUtils.getSharedPreference().getInt("Type",2);
    }

    public void setDefaultType(int type){
        JUtils.getSharedPreference().edit().putInt("Type",type).apply();
    }


    public String createKey(int type,int count){
        Random random = new Random(System.nanoTime());
        if (count<=0||count>100){
            return "";
        }
        String key = "";
        for (int i = 0; i < count; i++) {
            char cur = '0';
            switch (type){
                case 0://字母数字
                    cur = LAW_LETTER[random.nextInt(LAW_LETTER.length)];
                    break;
                case 1://字母数字大小写
                    cur = HIGH_LETTER[random.nextInt(HIGH_LETTER.length)];
                    break;
                case 2://纯数字
                    cur = NUMBER[random.nextInt(NUMBER.length)];
                    break ;
            }
            key+=cur;
        }
        return key;
    }
}
