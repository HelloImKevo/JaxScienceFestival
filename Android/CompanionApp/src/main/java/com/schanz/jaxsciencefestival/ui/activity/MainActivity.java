package com.schanz.jaxsciencefestival.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import com.schanz.jaxsciencefestival.App;
import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.ai.ChatCompanion;
import com.schanz.jaxsciencefestival.manager.ChatManager;
import com.schanz.jaxsciencefestival.model.DataBlob;
import com.schanz.jaxsciencefestival.model.NewsEvent;
import com.schanz.jaxsciencefestival.presenter.MainPresenter;
import com.schanz.jaxsciencefestival.presenter.MainView;
import com.schanz.jaxsciencefestival.presenter.PresenterManager;
import com.schanz.jaxsciencefestival.ui.dialog.ProgressDialog;
import com.schanz.jaxsciencefestival.ui.view.ChatDashboardListView;
import com.schanz.jaxsciencefestival.ui.view.NewsDashboardListView;
import com.schanz.jaxsciencefestival.ui.view.QuizDashboardView;
import com.schanz.jaxsciencefestival.util.AnimUtil;
import com.schanz.jaxsciencefestival.util.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer_nav_view)
    NavigationView drawerNavView;

    @BindView(R.id.chat_dashboard_list_view)
    ChatDashboardListView chatDashboardListView;
    @BindView(R.id.news_dashboard_list_view)
    NewsDashboardListView newsDashboardListView;
    @BindView(R.id.quiz_dashboard_view)
    QuizDashboardView quizDashboardView;

    private MainPresenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mPresenter = new MainPresenter();
        } else {
            mPresenter = PresenterManager.instance().restorePresenter(savedInstanceState);
            if (mPresenter == null) {
                mPresenter = new MainPresenter();
            }
        }

        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        initComponents();

        showNewsDashboard();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_news:
                                showNewsDashboard();
                                return true;
                            case R.id.action_chat:
                                showChatDashboard();
                                return true;
                            case R.id.action_quiz:
                                showQuizDashboard();
                                return true;
                        }
                        return false;
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.bindView(this);
        if (mPresenter.loading()) {
            mProgressDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unbindView();
        mProgressDialog.dismiss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.instance().savePresenter(mPresenter, outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        if (App.instance().isDebugBuild()) {
            MenuItem item = menu.findItem(R.id.action_dev_tools);
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_recreate_data) {
            mPresenter.recreateData(this);
            return true;
        } else if (id == R.id.action_dev_tools) {
            startActivity(new Intent(MainActivity.this, DevToolsActivity.class));
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
            // TODO: Handle view / fragment closure, then activity back nav
            if (isDetailViewShowing()) {
                hideDetailView();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void showItems(DataBlob model) {
        Logger.i(TAG, "showItems.... for DataBlob");
        mProgressDialog.dismiss();
        newsDashboardListView.setModel(model.getNewsEvents());
        chatDashboardListView.setModel(model.getChatCompanions());
    }

    @Override
    public void showDetails(ChatCompanion model) {
        Logger.i(TAG, "showDetails....");
        mProgressDialog.dismiss();
        //quoteDetailView.setModel(model);
        if (isTabletConfiguration()) {
            //AnimUtil.fadeIn(this, quoteDetailView);
        } else {
            //AnimUtil.slideInFromRightToReplace(this, quoteDetailView, chatDashboardListView);
        }
    }

    @Override
    public void showLoading() {
        Logger.i(TAG, "showLoading....");
        mProgressDialog.show();
    }

    @Override
    public void showEmpty() {
        // TODO: show empty view / message
        Logger.i(TAG, "showEmpty....");
        mProgressDialog.dismiss();
        newsDashboardListView.setModel(new ArrayList<NewsEvent>());
        chatDashboardListView.setModel(new ArrayList<ChatCompanion>());
    }

    private void initComponents() {
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "FAB Action not implemented", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        drawerNavView.setNavigationItemSelectedListener(this);
        mProgressDialog = new ProgressDialog(this);

        newsDashboardListView.setListener(new NewsDashboardListView.Listener() {
            @Override
            public void onItemSelected(@NonNull NewsEvent item) {
                Logger.i(TAG, "onNewsStory selected.......");
                // TODO: Start the news event details activity
            }
        });
        chatDashboardListView.setListener(new ChatDashboardListView.Listener() {
            @Override
            public void onItemSelected(@NonNull ChatCompanion item) {
                Logger.i(TAG, "onChatCompanion selected.......");
                ChatManager.instance().setSelectedChatCompanion(item);
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });
    }

    private void hideDetailView() {
        if (isTabletConfiguration()) {
            //AnimUtil.fadeOut(this, quoteDetailView);
        } else {
            //AnimUtil.slideInFromLeftToReplace(this, chatDashboardListView, quoteDetailView);
        }
    }

    private boolean isDetailViewShowing() {
        return false;
        //return quoteDetailView.getVisibility() == View.VISIBLE;
    }

    @Nullable
    private View getCurrentlyVisibleDashboard() {
        final int visible = View.VISIBLE;
        if (newsDashboardListView.getVisibility() == visible) {
            return newsDashboardListView;
        } else if (chatDashboardListView.getVisibility() == visible) {
            return chatDashboardListView;
        } else if (quizDashboardView.getVisibility() == visible) {
            return quizDashboardView;
        }
        return null;
    }

    private void showNewsDashboard() {
        View currentlyVisibleDashboard = getCurrentlyVisibleDashboard();
        if (currentlyVisibleDashboard == null) {
            AnimUtil.fadeIn(this, newsDashboardListView);
        } else {
            if (currentlyVisibleDashboard == chatDashboardListView
                    || currentlyVisibleDashboard == quizDashboardView) {
                // If the middle or right views are showing
                AnimUtil.slideInFromLeftToReplace(this, newsDashboardListView, currentlyVisibleDashboard);
            }
        }
    }

    private void showChatDashboard() {
        View currentlyVisibleDashboard = getCurrentlyVisibleDashboard();
        if (currentlyVisibleDashboard == null) {
            AnimUtil.fadeIn(this, chatDashboardListView);
        } else {
            if (currentlyVisibleDashboard == newsDashboardListView) {
                // If the left view is showing
                AnimUtil.slideInFromRightToReplace(this, chatDashboardListView, currentlyVisibleDashboard);
            } else if (currentlyVisibleDashboard == quizDashboardView) {
                // If the right view is showing
                AnimUtil.slideInFromLeftToReplace(this, chatDashboardListView, currentlyVisibleDashboard);
            }
        }
    }

    private void showQuizDashboard() {
        View currentlyVisibleDashboard = getCurrentlyVisibleDashboard();
        if (currentlyVisibleDashboard == null) {
            AnimUtil.fadeIn(this, quizDashboardView);
        } else {
            if (currentlyVisibleDashboard == newsDashboardListView
                    || currentlyVisibleDashboard == chatDashboardListView) {
                // If the left or middle views are showing
                AnimUtil.slideInFromRightToReplace(this, quizDashboardView, currentlyVisibleDashboard);
            }
        }
    }
}
