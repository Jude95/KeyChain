package com.jude.keychain.presentation.presenter;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.presentation.ui.AddActivity;

/**
 * Created by Mr.Jude on 2015/11/3.
 */
public class AddPresenter extends BeamDataActivityPresenter<AddActivity,KeyEntity>{
    private KeyEntity data;

    @Override
    protected void onCreate(AddActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        int id = getView().getIntent().getIntExtra("id",-1);
        data = KeyModel.getInstance().getKeyById(id);
        if (data == null){
            data = new KeyEntity();
            data.setType(KeyModel.getInstance().getDefaultType());
        }
        publishObject(data);
    }

    public void setColorType(int type){
        data.setType(type);
    }
    public int getColorType(){
        return data.getType();
    }
    public void submit(String name,String account,String password,String note){
        if (data.getId() == -1 || !KeyModel.getInstance().containId(data.getId()))
            KeyModel.getInstance().createKey(name,account,password,note,data.getType());
        else
            KeyModel.getInstance().updateKey(data.getId(),name,account,password,note,data.getType());
        getView().finish();
    }

    public String createPassword(int type,int count){
        return KeyModel.getInstance().createKey(type,count);
    }
}
