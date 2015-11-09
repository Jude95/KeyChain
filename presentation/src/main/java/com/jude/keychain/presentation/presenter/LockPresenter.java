package com.jude.keychain.presentation.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jude.beam.bijection.Presenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.presentation.ui.LockActivity;
import com.jude.keychain.presentation.ui.MainActivity;
import com.jude.keychain.presentation.ui.SetLockActivity;
import com.jude.utils.JUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Mr.Jude on 2015/11/7.
 */
public class LockPresenter extends Presenter<LockActivity> {

    public static final int ERROR_TIME_MAX = 5;
    public static final int ERROR_DELAY = 60000;


    private long mLastTime = 0;
    private int mErrorTimes = 0;

    private Subscription mDelaySubscription;


    @Override
    protected void onCreate(LockActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        if (KeyModel.getInstance().isFirst()){
            getView().startActivity(new Intent(getView(), SetLockActivity.class));
            getView().finish();
        }
    }

    @Override
    protected void onCreateView(LockActivity view) {
        super.onCreateView(view);
        readErrorTime();
        checkErrorDelay();
    }

    public void checkSeed(String seed){
         if (KeyModel.getInstance().checkSeed(seed)){
             getView().setCorrect();
             cleanErrorTime();
             new Handler().postDelayed(() -> {
                 getView().startActivity(new Intent(getView(), MainActivity.class));
                 getView().finish();
             }, 0);
         }else{
             getView().setWrong();
             mErrorTimes++;
             mLastTime = System.currentTimeMillis();
             saveErrorTime();
             checkErrorDelay();
         }
    }

    private void saveErrorTime(){
        JUtils.getSharedPreference().edit().putInt("errorTime",mErrorTimes).apply();
        JUtils.getSharedPreference().edit().putLong("lastTime",mLastTime).apply();
    }

    private void readErrorTime(){
        mErrorTimes = JUtils.getSharedPreference().getInt("errorTime",0);
        mLastTime = JUtils.getSharedPreference().getLong("lastTime",0);
    }

    private void cleanErrorTime(){
        JUtils.getSharedPreference().edit().putInt("errorTime", 0).apply();
        JUtils.getSharedPreference().edit().putLong("lastTime",0).apply();
    }

    public void checkErrorDelay(){
        if (mErrorTimes>=ERROR_TIME_MAX && (mDelaySubscription==null||mDelaySubscription.isUnsubscribed())){
            mDelaySubscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .map(aLong -> ERROR_DELAY - (System.currentTimeMillis() - mLastTime))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if (aLong>0)
                            getView().setTryDelay(aLong.intValue()/1000);
                        else{
                            getView().clear();
                            mDelaySubscription.unsubscribe();
                        }
                    });
        }
    }

}
