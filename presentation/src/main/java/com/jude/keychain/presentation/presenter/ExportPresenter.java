package com.jude.keychain.presentation.presenter;

import com.jude.beam.bijection.Presenter;
import com.jude.keychain.data.model.ImportModel;
import com.jude.keychain.presentation.ui.ExportActivity;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/11/8.
 */
public class ExportPresenter extends Presenter<ExportActivity> {

    public void export(String password){
        String text = ImportModel.getInstance().exportData(password);
        JUtils.Log("Encrypt:"+text);
        if (text==null){
            getView().showFailure();
        }else{
            JUtils.copyToClipboard(text);
            getView().showSuccess();
        }
    }
}
