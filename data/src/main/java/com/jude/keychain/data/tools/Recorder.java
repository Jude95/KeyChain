package com.jude.keychain.data.tools;

import android.support.annotation.Nullable;

import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.utils.JUtils;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/4.
 */
public class Recorder {

    @Nullable
    public static List<KeyEntity> read(){
        return Encryptor.from(JUtils.getSharedPreference().getString("data", ""));
    }

    public static void save(List<KeyEntity> data){
        String content = Encryptor.to(data);
        JUtils.getSharedPreference().edit().putString("data",content).apply();
    }




}
