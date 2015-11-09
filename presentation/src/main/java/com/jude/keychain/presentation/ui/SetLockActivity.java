package com.jude.keychain.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.presentation.presenter.SetLockPresenter;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zhanghai.patternlock.PatternView;

/**
 * Created by Mr.Jude on 2015/11/7.
 */
@RequiresPresenter(SetLockPresenter.class)
public class SetLockActivity extends BeamBaseActivity<SetLockPresenter> {

    @Bind(R.id.hint)
    TextView hint;
    @Bind(R.id.pattern)
    PatternView patternView;

    private String mSeed;
    private int status = 0;

    public static final int FIRST = 1;
    public static final int REPEAT = 2;
    public static final int CONFIRM = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);

        setContentView(R.layout.activity_set_lock);
        ButterKnife.bind(this);
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
            }

            @Override
            public void onPatternDetected(List<PatternView.Cell> pattern) {
                JUtils.Log(mSeed);
                patternView.setInputEnabled(false);
                switch (status){
                    case FIRST:
                        getPresenter().setFirst(mSeed);break;
                    case REPEAT:
                        getPresenter().setRepeat(mSeed);break;
                    case CONFIRM:
                        getPresenter().confirm(mSeed);
                }
            }
        });
    }

    public void setFirstInput(){
        status = 1;
        hint.setText(R.string.seed_set);
        patternView.clearPattern();
        patternView.setInputEnabled(true);

    }

    public void setRepeatInput(){
        status = 2;
        hint.setText(R.string.seed_confirm);
        patternView.clearPattern();
        patternView.setInputEnabled(true);

    }

    public void setConfirmInput(){
        status = 0;
        hint.setText(R.string.seed_check);
        patternView.clearPattern();
        patternView.setInputEnabled(true);

    }

    public void setWrong() {
        patternView.setDisplayMode(PatternView.DisplayMode.Wrong);
        hint.setText(R.string.patter_error);
        new Handler().postDelayed(this::clear, 2000);
    }

    public void setCorrect() {
        patternView.setDisplayMode(PatternView.DisplayMode.Correct);
    }

    public void clear() {
        switch (status){
            case FIRST:
                hint.setText(R.string.seed_set);
                break;
            case REPEAT:
                hint.setText(R.string.seed_confirm);
                break;
            case CONFIRM:
                hint.setText(R.string.seed_check);
                break;
        }
        patternView.clearPattern();
        patternView.setInputEnabled(true);
    }

    private int getNumberByPosition(int column, int row) {
        return row * 3 + column + 1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
