package com.schanz.jaxsciencefestival.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.manager.NewsManager;
import com.schanz.jaxsciencefestival.model.NewsEvent;
import com.schanz.jaxsciencefestival.presenter.ChatView;
import com.schanz.jaxsciencefestival.presenter.MainPresenter;
import com.schanz.jaxsciencefestival.util.Logger;
import com.schanz.jaxsciencefestival.util.StringHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ChatView {

    private static final String TAG = NewsActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer_nav_view)
    NavigationView drawerNavView;

    @BindView(R.id.news_color_header)
    View newsColorHeader;
    @BindView(R.id.news_image_header)
    ImageView newsImageHeader;

    @BindView(R.id.lbl_headline)
    TextView lblHeadline;
    @BindView(R.id.lbl_date)
    TextView lblDate;
    @BindView(R.id.lbl_full_story)
    TextView lblFullStory;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        ButterKnife.bind(this);
        initComponents();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshNewsEventStory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chat_one) {
            Toast.makeText(NewsActivity.this, "Chat One not implemented!", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_chat_two) {
            Toast.makeText(NewsActivity.this, "Chat Two not implemented!", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_chat_three) {
            Toast.makeText(NewsActivity.this, "Chat Three not implemented!", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showLoading() {
        Logger.i(TAG, "showLoading....");
    }

    @Override
    public void showEmpty() {
        // TODO: show empty view / message
        Logger.i(TAG, "showEmpty....");
    }

    private void initComponents() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        drawerNavView.setNavigationItemSelectedListener(this);
        // mProgressDialog = new ProgressDialog(this);
    }

    private void refreshNewsEventStory() {
        NewsEvent newsEvent = NewsManager.instance().getSelectedNewsEvent();
        if (newsEvent != null) {
            // TODO: Associate colors with news categories (Tech, Bio, Math, Engineering)
            newsColorHeader.setBackgroundColor(ContextCompat.getColor(this, newsEvent.color));
            newsImageHeader.setImageResource(newsEvent.headerImage);
            lblHeadline.setText(newsEvent.headline);
            lblDate.setText(StringHelper.toDateAtTime(new Date()));
            lblFullStory.setText(newsEvent.fullStory);
        }
    }
}
