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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import com.schanz.jaxsciencefestival.R;
import com.schanz.jaxsciencefestival.ai.ChatCompanion;
import com.schanz.jaxsciencefestival.ai.Message;
import com.schanz.jaxsciencefestival.manager.ChatManager;
import com.schanz.jaxsciencefestival.presenter.ChatView;
import com.schanz.jaxsciencefestival.presenter.MainPresenter;
import com.schanz.jaxsciencefestival.ui.view.ChatMessageHistoryListView;
import com.schanz.jaxsciencefestival.util.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ChatView {

    private static final String TAG = ChatActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer_nav_view)
    NavigationView drawerNavView;

    @BindView(R.id.profile_color_header)
    View profileColorHeader;
    @BindView(R.id.img_profile_picture)
    ImageView imgProfilePicture;
    @BindView(R.id.lbl_name)
    TextView lblName;
    @BindView(R.id.chat_message_history_list_view)
    ChatMessageHistoryListView chatMessageHistoryListView;

    @BindView(R.id.btn_positive)
    Button btnPositive;
    @BindView(R.id.btn_neutral)
    Button btnNeutral;
    @BindView(R.id.btn_negative)
    Button btnNegative;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (savedInstanceState == null) {
        //    mPresenter = new MainPresenter();
        //} else {
        //    mPresenter = PresenterManager.instance().restorePresenter(savedInstanceState);
        //    if (mPresenter == null) {
        //        mPresenter = new MainPresenter();
        //    }
        //}

        setContentView(R.layout.chat_activity);

        ButterKnife.bind(this);

        initComponents();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //mPresenter.bindView(this);
        //if (mPresenter.loading()) {
        //    mProgressDialog.show();
        //}
        refreshMessageHistory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mPresenter.unbindView();
        //mProgressDialog.dismiss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //PresenterManager.instance().savePresenter(mPresenter, outState);
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
            Toast.makeText(ChatActivity.this, "Chat One not implemented!", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_chat_two) {
            Toast.makeText(ChatActivity.this, "Chat Two not implemented!", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_chat_three) {
            Toast.makeText(ChatActivity.this, "Chat Three not implemented!", Toast.LENGTH_LONG).show();
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
        // mProgressDialog.show();
    }

    @Override
    public void showEmpty() {
        // TODO: show empty view / message
        Logger.i(TAG, "showEmpty....");
        // mProgressDialog.dismiss();
        // newsDashboardListView.setModel(new ArrayList<NewsEvent>());
        // chatDashboardListView.setModel(new ArrayList<ChatCompanion>());
    }

    private void initComponents() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        drawerNavView.setNavigationItemSelectedListener(this);
        // mProgressDialog = new ProgressDialog(this);

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatCompanion chatCompanion = ChatManager.instance().getSelectedChatCompanion();
                if (chatCompanion != null) {
                    int val = new Random().nextInt(4);
                    Message.Type type;
                    if (val == 0) {
                        type = Message.Type.QUESTION;
                    } else if (val == 1) {
                        type = Message.Type.RESPONSE;
                    } else if (val == 2) {
                        type = Message.Type.GREETING;
                    } else {
                        type = Message.Type.STATEMENT;
                    }
                    chatCompanion.onUserResponse(chatCompanion.getMessage(
                            ChatActivity.this,
                            (val < 2 ? Message.Source.AI : Message.Source.USER), type, Message.Mood.POSITIVE));
                    refreshMessageHistory();
                }
            }
        });
        btnNeutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatCompanion chatCompanion = ChatManager.instance().getSelectedChatCompanion();
                if (chatCompanion != null) {
                    int val = new Random().nextInt(4);
                    Message.Type type;
                    if (val == 0) {
                        type = Message.Type.QUESTION;
                    } else if (val == 1) {
                        type = Message.Type.RESPONSE;
                    } else if (val == 2) {
                        type = Message.Type.GREETING;
                    } else {
                        type = Message.Type.STATEMENT;
                    }
                    chatCompanion.onUserResponse(chatCompanion.getMessage(
                            ChatActivity.this,
                            (val < 2 ? Message.Source.AI : Message.Source.USER), type, Message.Mood.NEUTRAL));
                    refreshMessageHistory();
                }
            }
        });
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatCompanion chatCompanion = ChatManager.instance().getSelectedChatCompanion();
                if (chatCompanion != null) {
                    int val = new Random().nextInt(4);
                    Message.Type type;
                    if (val == 0) {
                        type = Message.Type.QUESTION;
                    } else if (val == 1) {
                        type = Message.Type.RESPONSE;
                    } else if (val == 2) {
                        type = Message.Type.GREETING;
                    } else {
                        type = Message.Type.STATEMENT;
                    }
                    chatCompanion.onUserResponse(chatCompanion.getMessage(
                            ChatActivity.this,
                            (val < 2 ? Message.Source.AI : Message.Source.USER), type, Message.Mood.NEGATIVE));
                    refreshMessageHistory();
                }
            }
        });
    }

    private void refreshMessageHistory() {
        ChatCompanion chatCompanion = ChatManager.instance().getSelectedChatCompanion();
        if (chatCompanion != null) {
            profileColorHeader.setBackgroundColor(ContextCompat.getColor(this, chatCompanion.profileColor));
            imgProfilePicture.setImageResource(chatCompanion.profilePicture);
            lblName.setText(chatCompanion.name);
            chatMessageHistoryListView.setModel(chatCompanion.messageHistory);
        }
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
}
