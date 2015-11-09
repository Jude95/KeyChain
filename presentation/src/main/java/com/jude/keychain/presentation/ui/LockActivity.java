package com.jude.keychain.presentation.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.keychain.R;
import com.jude.keychain.presentation.presenter.LockPresenter;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zhanghai.patternlock.PatternView;

/**
 * Created by Mr.Jude on 2015/11/7.
 */
@RequiresPresenter(LockPresenter.class)
public class LockActivity extends BeamBaseActivity<LockPresenter> {

    @Bind(R.id.pattern)
    PatternView patternView;
    @Bind(R.id.hint)
    TextView hint;

    private String mSeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.bind(this);
        patternView.setInputEnabled(true);
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

                getPresenter().checkSeed(mSeed);
            }
        });
    }


    public void setCorrect() {
        JUtils.Log("正确");
        patternView.setDisplayMode(PatternView.DisplayMode.Correct);
        patternView.setInputEnabled(false);

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

    public void setTryDelay(int time){
        patternView.setInputEnabled(false);
        hint.setText(String.format(getString(R.string.error_overload),time));
    }

    private int getNumberByPosition(int column, int row) {
        return row * 3 + column + 1;
    }
}
