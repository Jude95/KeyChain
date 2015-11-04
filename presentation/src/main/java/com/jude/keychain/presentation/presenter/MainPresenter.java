package com.jude.keychain.presentation.presenter;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.presentation.ui.MainActivity;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class MainPresenter extends BeamListActivityPresenter<MainActivity,KeyEntity> {

    @Override
    protected void onCreateView(MainActivity view) {
        super.onCreateView(view);
        KeyModel.getInstance().readKeyEntryByType(0)
                .unsafeSubscribe(getRefreshSubscriber());
    }
}
