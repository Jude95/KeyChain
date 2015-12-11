package com.jude.keychain.presentation.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.internal.MDButton;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.keychain.R;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.presenter.AddPresenter;
import com.jude.keychain.presentation.widget.FitSystemWindowsFrameLayout;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

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
    TAGView type;
    @Bind(R.id.create)
    Button create;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_Container)
    FitSystemWindowsFrameLayout toolbarContainer;
    @Bind(R.id.select)
    Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        type.setOnClickListener(v -> {
            new ColorChooserDialog.Builder(this, R.string.color_palette)
                    .preselect(Color.getColorByType(getPresenter().getColorType()))
                    .allowUserColorInput(false)
                    .cancelButton(R.string.cancel)
                    .doneButton(R.string.done)
                    .customColors(Color.getColors(), null)
                    .show();
        });
        create.setOnClickListener(v -> {
            createPassword();
        });
        select.setOnClickListener(v->{
            showAccountList();
        });
        name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        account.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                account.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                password.setError("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setData(KeyEntity data) {
        super.setData(data);
        type.setBackgroundColor(Color.getColorByType(data.getType()));
        onColorSelection(null, Color.getColorByType(data.getType()));

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
        type.setBackgroundColor(i);
        toolbarContainer.setBackgroundColor(i);
        toolbar.setBackgroundColor(i);
    }

    private void createPassword() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(getString(R.string.create_password))
                .customView(R.layout.dialog_password, false)
                .negativeText(R.string.cancel)
                .positiveText(R.string.done)
                .show();
        View view = dialog.getCustomView();
        Spinner spinner = $(view, R.id.spinner_mode);
        EditText count = $(view, R.id.count);
        TextView password = $(view, R.id.password);
        View refresh = $(view, R.id.refresh);
        MDButton positiveAction = dialog.getActionButton(DialogAction.POSITIVE);

        spinner.setAdapter(new PasswordTypeAdapter());
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                password.setText(getPresenter().createPassword(spinner.getSelectedItemPosition(), Integer.parseInt(count.getText().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        refresh.setOnClickListener(v -> {
            Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            v.startAnimation(animation);
            int lenght;
            try{
                lenght = Integer.parseInt(count.getText().toString());
            }catch (Throwable e){
                lenght = 0;
            }
            password.setText(getPresenter().createPassword(spinner.getSelectedItemPosition(),lenght ));
        });
        count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    password.setText(getPresenter().createPassword(spinner.getSelectedItemPosition(), Integer.parseInt(count.getText().toString())));
                } catch (Exception e) {

                }
            }
        });


        positiveAction.setOnClickListener(v -> {
            this.password.getEditText().setText(password.getText());
            dialog.dismiss();
        });
        password.setText(getPresenter().createPassword(spinner.getSelectedItemPosition(), Integer.parseInt(count.getText().toString())));
    }


    private void showAccountList(){
        ArrayList<String> arrayList = new ArrayList<>();
        KeyModel.getInstance().registerKeyEntry()
                .first()
                .flatMap(new Func1<List<KeyEntity>, Observable<List<KeyEntity>>>() {
                    @Override
                    public Observable<List<KeyEntity>> call(List<KeyEntity> keyEntities) {
                        return Observable.from(keyEntities)
                                .doOnNext(keyEntity -> {
                                    for (String account : arrayList) {
                                        if (account.equals(keyEntity.getAccount())) {
                                            return;
                                        }
                                    }
                                    arrayList.add(keyEntity.getAccount());
                                })
                                .toList();
                    }
                })
                .filter(keyEntities -> {
                    if (keyEntities.size() > 0) {
                        return true;
                    } else {
                        JUtils.Toast(getString(R.string.error_no_account));
                        return false;
                    }
                })
                .map(keyEntities -> arrayList)
                .map(strings -> strings.toArray(new String[strings.size()]))
                .subscribe(strings -> {
                    new MaterialDialog.Builder(this)
                            .items(strings)
                            .itemsCallback((materialDialog, view, i, charSequence) -> account.getEditText().setText(arrayList.get(i)))
                            .show();
                });

    }


    class PasswordTypeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(AddActivity.this);
            textView.setText(getResources().getStringArray(R.array.password_type)[position]);
            textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JUtils.dip2px(32)));
            textView.setGravity(Gravity.CENTER);
            return textView;
        }
    }
}
