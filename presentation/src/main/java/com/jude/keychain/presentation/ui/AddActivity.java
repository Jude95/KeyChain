package com.jude.keychain.presentation.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.keychain.R;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.presenter.AddPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mr.Jude on 2015/11/3.
 */
@RequiresPresenter(AddPresenter.class)
public class AddActivity extends BeamDataActivity<AddPresenter, KeyEntity> implements ColorChooserDialog.ColorCallback {

    @Bind(R.id.name)
    TextInputLayout name;
    @Bind(R.id.account)
    TextInputLayout account;
    @Bind(R.id.password)
    TextInputLayout password;
    @Bind(R.id.note)
    TextInputLayout note;
    @Bind(R.id.type)
    View type;
    @Bind(R.id.create)
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        type.setOnClickListener(v -> {
            new ColorChooserDialog.Builder(this, R.string.color_palette)
                    .preselect(Color.Green.getColor())
                    .allowUserColorInput(false)
                    .cancelButton(R.string.cancel)
                    .doneButton(R.string.done)
                    .customColors(Color.getColors(), null)
                    .show();
        });
        create.setOnClickListener(v->{
            new MaterialDialog.Builder(this)
                    .title(getString(R.string.create_password))
                    .customView(R.layout.dialog_password,false)
                    .negativeText(R.string.cancel)
                    .positiveText(R.string.done)
                    .show();
        });
    }

    @Override
    public void setData(KeyEntity data) {
        super.setData(data);
        type.setBackgroundColor(Color.getColorByType(data.getType()));
        name.getEditText().setText(data.getName());
        account.getEditText().setText(data.getAccount());
        password.getEditText().setText(data.getPassword());
        note.getEditText().setText(data.getNote());
    }

    public void checkInput() {
        if (name.getEditText().getText().length() == 0) {
            name.setError(getString(R.string.error_name_empty));
            return;
        }
        if (account.getEditText().getText().length() == 0) {
            account.setError(getString(R.string.error_account_empty));
            return;
        }
        if (password.getEditText().getText().length() == 0) {
            password.setError(getString(R.string.error_password_empty));
            return;
        }
        getPresenter().submit(
                name.getEditText().getText().toString(),
                account.getEditText().getText().toString(),
                password.getEditText().getText().toString(),
                note.getEditText().getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ok) {
            checkInput();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorSelection(ColorChooserDialog colorChooserDialog, int i) {
        getPresenter().setColorType(Color.getTypeByColor(i));
    }
}
