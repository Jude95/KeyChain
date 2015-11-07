package com.jude.keychain.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.keychain.R;
import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.keychain.domain.value.Color;
import com.jude.keychain.presentation.presenter.MainPresenter;
import com.jude.keychain.presentation.viewholder.KeyViewHolder;
import com.jude.keychain.presentation.widget.PaddingStatusbarFrameLayout;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BeamListActivity<MainPresenter, KeyEntity>
        implements NavigationView.OnNavigationItemSelectedListener, ColorChooserDialog.ColorCallback {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_Container)
    PaddingStatusbarFrameLayout toolbarContainer;
    private TextView mLastView;
    private FloatingActionButton mFabAdd;

    @Override
    protected ListConfig getConfig() {
        mLastView = new TextView(this);
        mLastView.setGravity(Gravity.CENTER);
        mLastView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, JUtils.dip2px(48)));
        return super.getConfig().setNoMoreView(mLastView);
    }

    public void setCount(int count) {
        mLastView.setText(String.format(getString(R.string.total_format), count));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFabAdd = (FloatingActionButton) findViewById(R.id.fab);
        mFabAdd.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddActivity.class)));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onColorSelection(null,Color.getColorByType(getPresenter().getColorType()));
    }

    public void showDeleteSnackBar() {
        final Snackbar snackbar = Snackbar.make(mFabAdd, R.string.delete_done, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.backout, v -> {
            getPresenter().unDelete();
            snackbar.dismiss();
        });
        snackbar.show();
    }


    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new KeyViewHolder(parent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.color) {
            ColorChooserDialog dialog = new ColorChooserDialog.Builder(this, R.string.color_palette)
                    .preselect(Color.Green.getColor())
                    .allowUserColorInput(false)
                    .customButton(R.string.all)
                    .cancelButton(R.string.cancel)
                    .doneButton(R.string.done)
                    .customColors(Color.getColors(), null)
                    .show();
            
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorSelection(ColorChooserDialog colorChooserDialog, int i) {
        getPresenter().setColorType(Color.getTypeByColor(i));
        toolbar.setBackgroundColor(i);
        toolbarContainer.setBackgroundColor(i);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
