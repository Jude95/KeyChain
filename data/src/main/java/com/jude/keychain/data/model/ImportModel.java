package com.jude.keychain.data.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.beam.model.AbsModel;
import com.jude.keychain.data.tools.Encryptor;
import com.jude.keychain.domain.entities.KeyEntity;

import java.util.ArrayList;

/**
 * Created by Mr.Jude on 2015/11/8.
 */
public class ImportModel extends AbsModel {
    public static ImportModel getInstance() {
        return getInstance(ImportModel.class);
    }

    public String exportData(final String password){
        String json = new Gson().toJson(KeyModel.getInstance().getKeyEntry());
        try {
            return Encryptor.encrypt(password,json);
        } catch (Exception e) {
        }
        return null;
    }

    public boolean importData(String data,String password){
        try{
            String json = Encryptor.decrypt(password,data);
            ArrayList<KeyEntity> arr = new Gson().fromJson(json, new TypeToken<ArrayList<KeyEntity>>(){}.getType());
            KeyModel.getInstance().add(arr);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
