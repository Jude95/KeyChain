package com.jude.keychain.presentation.presenter;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.presentation.ui.AddActivity;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/11/3.
 */
public class AddPresenter extends BeamDataActivityPresenter<AddActivity,KeyEntity>{
    private KeyEntity data;

    @Override
    protected void onCreate(AddActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        data = new KeyEntity();
    }

    public void setColorType(int type){
        data.setType(type);
    }

    public void submit(String name,String account,String password,String note){

    }

    public String createPassword(int type,int count){
        JUtils.Log("type"+type);
        return KeyModel.getInstance().createKey(type,count);
    }
}
