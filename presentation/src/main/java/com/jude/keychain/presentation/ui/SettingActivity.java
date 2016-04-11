package com.jude.keychain.presentation.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.data.model.KeyModel;
import com.jude.keychain.domain.value.PreferenceKey;
import com.jude.utils.JUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Mr.Jude on 2016/4/10.
 * <p>
 * 1. 主界面密码显示
 * 2. 解锁手势显示
 * 3. 解锁背景更换
 */
public class SettingActivity extends BeamBaseActivity {
    @Bind(R.id.switch_display_password)
    Switch switchDisplayPassword;
    @Bind(R.id.switch_display_path)
    Switch switchDisplayPath;
    @Bind(R.id.btn_wallpaper)
    FrameLayout btnWallpaper;
    @Bind(R.id.text_wallpaper)
    TextView textWallpaper;
    @Bind(R.id.btn_help)
    FrameLayout btnHelp;
    @Bind(R.id.btn_about)
    FrameLayout btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        switchDisplayPassword.setChecked(JUtils.getSharedPreference().getBoolean(PreferenceKey.KEY_DISPLAY_PASSWORD, true));
        switchDisplayPath.setChecked(JUtils.getSharedPreference().getBoolean(PreferenceKey.KEY_DISPLAY_PATH, true));
        switchDisplayPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            JUtils.getSharedPreference().edit().putBoolean(PreferenceKey.KEY_DISPLAY_PASSWORD, isChecked).apply();
            KeyModel.getInstance().refresh();
        });
        switchDisplayPath.setOnCheckedChangeListener((buttonView, isChecked) -> JUtils.getSharedPreference().edit().putBoolean(PreferenceKey.KEY_DISPLAY_PATH, isChecked).apply());
        textWallpaper.setText(JUtils.getSharedPreference().getString(PreferenceKey.KEY_WALLPAPER,getString(R.string.default_wallpaper)));
        btnHelp.setOnClickListener(v->startActivity(new Intent(this,HelpActivity.class)));
        btnAbout.setOnClickListener(v->startActivity(new Intent(this,AboutActivity.class)));
        btnWallpaper.setOnClickListener(v->{
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(flag->{
                        if (flag){
                            Intent intent = new Intent(this, MultiImageSelectorActivity.class);
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                            startActivityForResult(intent, 100);
                        }else {
                            JUtils.Toast(getString(R.string.request_permissions));
                        }
                    });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path.size()>0){
                    JUtils.getSharedPreference().edit().putString(PreferenceKey.KEY_WALLPAPER,path.get(0)).apply();
                    textWallpaper.setText(path.get(0));
                }
            }
        }
    }
}
