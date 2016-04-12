package com.jude.keychain.presentation.ui;

import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.domain.value.PreferenceKey;
import com.jude.keychain.presentation.presenter.PatternLockPresenter;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JUtils;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zhanghai.patternlock.PatternView;

/**
 * Created by Mr.Jude on 2015/11/7.
 */
@RequiresPresenter(PatternLockPresenter.class)
public class PatternLockActivity extends BeamBaseActivity<PatternLockPresenter> {

    @Bind(R.id.pattern)
    PatternView patternView;
    @Bind(R.id.hint)
    TextView hint;
    @Bind(R.id.wallpaper)
    ImageView wallpaper;

    private String mSeed;
    Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pattern);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.bind(this);
        String image = JUtils.getSharedPreference().getString(PreferenceKey.KEY_WALLPAPER,null);
        JUtils.Log("wallpaper"+(wallpaper==null));
        JUtils.Log("wallpaper"+(findViewById(R.id.wallpaper)==null));
        if (image!=null){
            Glide.with(this).load(new File(image)).error(R.drawable.bg_lock).into(wallpaper);
        }
        vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        patternView.setInputEnabled(true);
        patternView.setInStealthMode(!JUtils.getSharedPreference().getBoolean(PreferenceKey.KEY_DISPLAY_PATH, true));
        patternView.setOnPatternListener(new PatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {
                mSeed = "";
            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<PatternView.Cell> pattern) {
                PatternView.Cell cell = pattern.get(pattern.size() - 1);
                mSeed += getNumberByPosition(cell.getColumn(), cell.getRow());
                vib.vibrate(10);
            }

            @Override
            public void onPatternDetected(List<PatternView.Cell> pattern) {
                getPresenter().checkSeed(mSeed);
            }
        });
    }


    public void setCorrect() {
        patternView.setDisplayMode(PatternView.DisplayMode.Correct);
        patternView.setInputEnabled(false);
        hint.setText(R.string.pattern_success);
    }

    public void setWrong() {
        patternView.setDisplayMode(PatternView.DisplayMode.Wrong);
        hint.setText(R.string.patter_error);
        new Handler().postDelayed(this::clear, 1000);
    }

    public void clear() {
        hint.setText("");
        patternView.clearPattern();
        patternView.setInputEnabled(true);
    }

    public void setTryDelay(int time) {
        patternView.setInputEnabled(false);
        hint.setText(String.format(getString(R.string.error_overload), time));
    }

    private int getNumberByPosition(int column, int row) {
        return row * 3 + column + 1;
    }
}
