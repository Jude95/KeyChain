package com.jude.keychain.presentation.viewholder;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.keychain.R;
import com.jude.keychain.domain.entities.KeyEntity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class KeyViewHolder extends BaseViewHolder<KeyEntity> {
    @Bind(R.id.color)
    View color;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.account)
    TextView account;
    @Bind(R.id.password)
    TextView password;
    @Bind(R.id.copy)
    ImageView copy;

    public KeyViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_key);
        ButterKnife.bind(this, itemView);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void setData(KeyEntity data) {
        name.setText(data.getName());
        account.setText(data.getAccount());
        password.setText(data.getPassword());
    }
}
