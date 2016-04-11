package com.jude.keychain.presentation.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.keychain.R;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.presentation.presenter.KeyDetailPresenter;
import com.jude.utils.JTimeTransform;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhuchenxi on 15/11/4.
 */
@RequiresPresenter(KeyDetailPresenter.class)
public class KeyDetailActivity extends BeamDataActivity<KeyDetailPresenter, KeyEntity> {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.account)
    TextView account;
    @Bind(R.id.password)
    TextView password;
    @Bind(R.id.note)
    TextView note;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.account_container)
    LinearLayout accountContainer;
    @Bind(R.id.password_container)
    LinearLayout passwordContainer;
    @Bind(R.id.info_time)
    ImageView infoTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        floatingActionButton.setOnClickListener(v -> {
            Intent i = new Intent(this, AddActivity.class);
            i.putExtra("id", getPresenter().getData().getId());
            startActivity(i);
        });
        accountContainer.setOnClickListener(v -> new MaterialDialog.Builder(this)
                .items(new String[]{v.getContext().getString(R.string.copy_account)})
                .itemsCallback((materialDialog, view, i, charSequence) -> JUtils.copyToClipboard(getPresenter().getData().getAccount()))
                .show());
        passwordContainer.setOnClickListener(v -> new MaterialDialog.Builder(this)
                .items(new String[]{v.getContext().getString(R.string.copy_password)})
                .itemsCallback((materialDialog, view, i, charSequence) -> JUtils.copyToClipboard(getPresenter().getData().getPassword()))
                .show());
    }

    @Override
    public void setData(KeyEntity data) {
        super.setData(data);
        int color = com.jude.keychain.domain.value.Color.getColorByType(data.getType());
        toolbar.setBackgroundColor(color);
        appbar.setBackgroundColor(color);
        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setBackgroundColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);

        collapsingToolbar.setTitle(data.getName());
        account.setText(data.getAccount());
        password.setText(data.getPassword());
        time.setText(new JTimeTransform(data.getTime()).toString(getString(R.string.date_format)));
        note.setText(TextUtils.isEmpty(data.getNote()) ? getString(R.string.empty) : data.getNote());
        infoTime.setOnClickListener(v -> JUtils.Toast(getString(R.string.timeout_info)));
        infoTime.setVisibility(System.currentTimeMillis()/1000 - data.getTime() > 15552000 ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {

            new MaterialDialog.Builder(this)
                    .title(R.string.delete)
                    .content(String.format(getString(R.string.confirm_format), getPresenter().getData().getName()))
                    .positiveText(R.string.delete)
                    .negativeText(R.string.cancel)
                    .positiveColor(Color.RED)
                    .onPositive((materialDialog, dialogAction) -> getPresenter().delete())
                    .show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
