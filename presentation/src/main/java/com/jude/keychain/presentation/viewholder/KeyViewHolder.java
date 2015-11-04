package com.jude.keychain.presentation.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.keychain.R;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.domain.value.Color;
import com.jude.utils.JUtils;

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

    private KeyEntity data;

    public KeyViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_key);
        ButterKnife.bind(this, itemView);
        copy.setOnClickListener(v -> new MaterialDialog.Builder(v.getContext())
                .items(new String[]{v.getContext().getString(R.string.copy_account), v.getContext().getString(R.string.copy_password)})
                .itemsCallback((materialDialog, view, i, charSequence) -> JUtils.copyToClipboard(i == 0 ? data.getAccount() : data.getPassword()))
                .show());
    }

    @Override
    public void setData(KeyEntity data) {
        this.data = data;
        color.setBackgroundColor(Color.getColorByType(data.getType()));
        name.setText(data.getName());
        account.setText(data.getAccount());
        password.setText(data.getPassword());
    }
}
