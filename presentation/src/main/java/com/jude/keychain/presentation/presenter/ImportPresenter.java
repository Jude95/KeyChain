package com.jude.keychain.presentation.presenter;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.keychain.data.model.ImportModel;
import com.jude.keychain.presentation.ui.ImportActivity;

/**
 * Created by Mr.Jude on 2015/11/8.
 */
public class ImportPresenter extends Presenter<ImportActivity> {

    @Override
    protected void onCreate(ImportActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    public void importData(String data,String password){
        if (ImportModel.getInstance().importData(data,password)){
            getView().showSuccess();
        }else{
            getView().showFailure();
        }
    }
}
