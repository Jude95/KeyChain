package com.jude.keychain.data.tools;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.keychain.domain.entities.KeyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/4.
 */
public class Encryptor {

    public static String encrypt(String data){
        return  data;
    }

    public static String decryption(String source){
        return source;
    }

    @Nullable
    public static ArrayList<KeyEntity> from(String source){
        String json = decryption(source);
        try {
            return new Gson().fromJson(json, new TypeToken<ArrayList<KeyEntity>>(){}.getType());
        }catch (Exception e){
            return null;
        }
    }

    public static String to(List<KeyEntity> data){
        return encrypt(new Gson().toJson(data));
    }


}
