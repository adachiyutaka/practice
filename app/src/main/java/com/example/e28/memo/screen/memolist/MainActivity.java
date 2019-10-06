package com.example.e28.memo.screen.memolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.e28.memo.R;
import com.example.e28.memo.model.Memo;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Realm realm;

    TaggedRecyclerViewAdapter adapter;

    // DB変更の有無を受信してrecyclerViewを更新するレシーバー
    private BroadcastReceiver listUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //recyclerView
        realm = Realm.getDefaultInstance();
        RealmResults<Memo> memoRealmResults = realm.where(Memo.class).findAll();
        adapter = new TaggedRecyclerViewAdapter(memoRealmResults);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_memos);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(adapter);

        // recyclerView更新用のレシーバーを作成
        LocalBroadcastManager.getInstance(this).registerReceiver(listUpdateReceiver, new IntentFilter("LIST_UPDATE"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_trash) {
            return true;
        }

        if (id == R.id.action_reminder) {
            //new ReminderDialogFragment().show(getSupportFragmentManager(), "reminder");
            Intent intent = new Intent(this, com.example.e28.memo.screen.reminder.ReminderDialogActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_highlight) {
            return true;
        }

        if (id == R.id.action_tag) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_drawer) {

        } else if (id == R.id.switch_style_column) {

        } else if (id == R.id.switch_style_sort) {

        } else if (id == R.id.switch_style_tag_group) {

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, com.example.e28.memo.screen.manage.ManageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_delete) {
            Intent intent = new Intent(this, com.example.e28.memo.screen.WriteActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        // realmのインスタンスを閉じる
        realm.close();
        // recyclerView更新用のレシーバーを破棄
        LocalBroadcastManager.getInstance(this).unregisterReceiver(listUpdateReceiver);
    }
}