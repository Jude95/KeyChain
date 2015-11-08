package com.jude.keychain.presentation.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jude.beam.bijection.Presenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.presentation.ui.LockActivity;
import com.jude.keychain.presentation.ui.MainActivity;
import com.jude.keychain.presentation.ui.SetLockActivity;

/**
 * Created by Mr.Jude on 2015/11/7.
 */
public class LockPresenter extends Presenter<LockActivity> {

    @Override
    protected void onCreate(LockActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        if (KeyModel.getInstance().isFirst()){
            getView().startActivity(new Intent(getView(), SetLockActivity.class));
            getView().finish();
        }
    }

    public void checkSeed(String seed){
         if (KeyModel.getInstance().checkSeed(seed)){
             getView().setCorrect();
             new Handler().postDelayed(() -> {
                 getView().startActivity(new Intent(getView(), MainActivity.class));
                 getView().finish();
             }, 0);
         }else{
             getView().setWrong();
         }
    }

}
