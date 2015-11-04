package com.jude.keychain.data.model;

import com.jude.beam.model.AbsModel;
import com.jude.keychain.domain.entities.KeyEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;

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
    public static KeyModel getInstance() {
        return getInstance(KeyModel.class);
    }

    public Observable<List<KeyEntity>> readKeyEntryByType(int type){
        return Observable.just(createKeyEntry(20));
    }

    public String createKey(int type,int count){
        Random random = new Random(System.nanoTime());
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
