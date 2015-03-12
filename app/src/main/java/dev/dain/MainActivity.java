package dev.dain;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.dain.library.SlidingTabLayout;
import dev.dain.library.ViewPagerAdapter;
import dev.dain.sms.SmsReceiver;

public class MainActivity extends ActionBarActivity {

    ListView mDrawerList; // 내용


    private Toolbar toolbar = null;

    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Home", "평가하기", "랭킹", "할인&이벤트"};
    private int Numboftabs = 4;

    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;

    private SmsReceiver smsReceiver = null;

    String Facebook_id;
    String Facebook_name;
    String[] Facebook_friends_names;
    String[] Facebook_friends_id;
    int friends_size;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;

    ImageView pf_img;
    View view;

    private BackPressCloseHandler backPressCloseHandler;


    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        //facebook_Id & NAME
        Intent intent = getIntent();
        Facebook_id = intent.getStringExtra("facebookId");
        Facebook_name = intent.getStringExtra("facebookName");
        //facebook friend list
        ArrayList<FacebookFriends> list = (ArrayList<FacebookFriends>) getIntent().getSerializableExtra("facebookFriends");
        Facebook_friends_id = new String[list.size()];
        Facebook_friends_names = new String[list.size()];

        //list값을 각각 string배열에 저장 id,name 분리시킴
        for (int i = 0; i < list.size(); i++) {
            Facebook_friends_id[i] = list.get(i).getFriend_ID();
            Facebook_friends_names[i] = list.get(i).getFriend_NAME();
        }

        //분리된 string배열을 SharedPreference에 저장
        SharedPreferencesActivity pref = new SharedPreferencesActivity(MainActivity.this);
        pref.savePreferences("friends_names", Facebook_friends_names);
        pref.savePreferences("friends_id", Facebook_friends_id);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        LeftMenuAdapter leftmenuadapter = new LeftMenuAdapter(this, R.layout.left_menu_profile, Facebook_id, Facebook_name);
        mDrawerList.setAdapter(leftmenuadapter);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.primary_dark);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name);
        dtToggle.setDrawerIndicatorEnabled(true);
        dlDrawer.setDrawerListener(dtToggle);
        // 확장리스트

        /*
                sms
         */
//        smsReceiver
        smsReceiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        registerReceiver(smsReceiver, intentFilter);


        //뒤로가기 버튼

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    public void mOnClick(View v) {

        if (v.getId() == R.id.pf_img) {
            new AlertDialog.Builder(this)
                    .setTitle("사진선택").setItems(R.array.select_picture, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        doTakeAlbumAction();
                        dialog.dismiss();
                    } else if (which == 1) {
                        doTakePhotoAction();
                        dialog.dismiss();
                    }

                }
            }).show();

        }

        if (v.getId() == R.id.notice) {
            Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.review) {
            Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
            startActivity(intent);
        }
    }

    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_FROM_CAMERA);

    }

    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_FROM_CAMERA) {
            pf_img.setImageBitmap((Bitmap) data.getExtras().get("data"));
            ((LeftMenuAdapter) mDrawerList.getAdapter()).notifyDataSetChanged();
        }
        if (requestCode == PICK_FROM_ALBUM) {

            try {
                String path = "data/data/dev.dain/files/profile.png";
                value = 1;

                Bitmap pf_bit = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                try {
                    File file = new File("profile.png");
                    FileOutputStream fos = openFileOutput("profile.png", 0);
                    pf_bit.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();

                } catch (Exception e) {

                }
                SharedPreferencesActivity pref = new SharedPreferencesActivity(MainActivity.this);
                pref.savePreferences("imagepath", path);
                pref.savePreferences("value", value);
                ((LeftMenuAdapter) mDrawerList.getAdapter()).notifyDataSetChanged();
            } catch (Exception e) {
                ;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dain, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (dtToggle.onOptionsItemSelected(item))
            return true;
        if (item.getItemId() == R.id.search_icon) {
            Intent intent = new Intent(MainActivity.this, SearchViewActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

// Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }


}
