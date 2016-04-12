package com.jude.keychain.presentation.presenter;

import android.content.Intent;
import android.os.Handler;

import com.jude.beam.bijection.Presenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.presentation.ui.MainActivity;
import com.jude.keychain.presentation.ui.SetLockActivity;

/**
 * Created by Mr.Jude on 2015/11/7.
 */
public class SetLockPresenter extends Presenter<SetLockActivity> {

    @Override
    protected void onCreateView(SetLockActivity view) {
        super.onCreateView(view);
        if (KeyModel.getInstance().isFirst()){
            getView().setFirstInput();
        }else{
            getView().setConfirmInput();
        }
    }

    String preSeed;
    public void setFirst(String first){
        preSeed = first;
        new Handler().postDelayed(() -> getView().setRepeatInput(), 500);
    }


    public void setRepeat(String first){
        if(first.equals(preSeed)){
            getView().setCorrect();
            KeyModel.getInstance().setSeed(preSeed);
            new Handler().postDelayed(() -> {
                getView().startActivity(new Intent(getView(), MainActivity.class));
                getView().finish();
            }, 200);
        }else{
            getView().setWrong();
        }
    }


    public void confirm(String first){
        if(KeyModel.getInstance().checkSeed(first)){
            getView().setCorrect();
            new Handler().postDelayed(() -> getView().setFirstInput(), 500);
        }else{
            getView().setWrong();
        }
    }

    public void back(){
        if (!KeyModel.getInstance().isFirst()){
            getView().startActivity(new Intent(getView(),MainActivity.class));
        }
        getView().finish();
    }
}
