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
import android.widget.SearchView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;
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
    MenuItem mSearch;
    TabHost mTab;

    DrawerLayout mDrawerLayout; // 주기능
    ListView mDrawerList; // 내용
    ActionBarDrawerToggle mDrawToggle; // 주기능
    FrameLayout contentMain;
    // CharSequence mDrawerTitle; // ActionBar의 제목을 변경하기 위한 변수
    // CharSequence mTitle; // ActionBar의 제목을 변경하기 위한 변수
    String[] mSideList; // 사이드 내용

    ExpandableListView mList;
    String[] arCafe = new String[]{"StarBucks", "카페베네", "망고식스", "투썸플레이스",
            "커피빈"};
    String[][] arDetails = new String[][]{{"음료", "푸드", "전체보기", "베스트10평가"},
            {"음료", "푸드", "전체보기", "베스트10평가"},
            {"음료", "푸드", "전체보기", "베스트10평가"},
            {"음료", "푸드", "전체보기", "베스트10평가"},
            {"음료", "푸드", "전체보기", "베스트10평가"}};


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

        //facebook_Id
        Intent intent = getIntent();
        Facebook_id = intent.getStringExtra("facebookId");
        Facebook_name = intent.getStringExtra("facebookName");
        //

       // LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view = inflater.inflate(R.layout.left_menu_profile, null);

        view =(View)getLayoutInflater().inflate(R.layout.left_menu_profile,null);
        pf_img = (ImageView) view.findViewById(R.id.pf_img);




        // mTitle = mDrawerTitle = getTitle(); // 액션바 제목
        //mSideList = getResources().getStringArray(R.array.side_array);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        LeftMenuAdapter leftmenuadapter = new LeftMenuAdapter(this, R.layout.left_menu_profile, Facebook_id, Facebook_name);
        mDrawerList.setAdapter(leftmenuadapter);
        //mDrawerList.setAdapter(new ArrayAdapter<String>(MainActivity.this,
        //		R.layout.left_menu_profile, mSideList));
//		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


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

        mList = (ExpandableListView) findViewById(R.id.list);
        List<Map<String, String>> cafeData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> detailData = new ArrayList<List<Map<String, String>>>();

        for (int i = 0; i < arCafe.length; i++) {
            Map<String, String> Cafe = new HashMap<String, String>();
            Cafe.put("cafe", arCafe[i]);
            cafeData.add(Cafe);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();

            for (int j = 0; j < arDetails[i].length; j++) {
                Map<String, String> Details = new HashMap<String, String>();
                Details.put("details", arDetails[i][j]);
                children.add(Details);
            }
            detailData.add(children);
        }
        ExpandableListAdapter adapter = new SimpleExpandableListAdapter(this,
                cafeData, android.R.layout.simple_list_item_1,
                new String[]{"cafe"}, new int[]{android.R.id.text1},
                detailData, android.R.layout.simple_expandable_list_item_1,
                new String[]{"details"}, new int[]{android.R.id.text1});
//		mList.setAdapter(adapter);
        //뒤로가기 버튼

        backPressCloseHandler = new BackPressCloseHandler(this);
       // (new DownThread("https://graph.facebook.com/dhha22/picture?type=large")).start();
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
            pf_img.setImageBitmap((Bitmap)data.getExtras().get("data"));
            ((LeftMenuAdapter)mDrawerList.getAdapter()).notifyDataSetChanged();
        }
        if (requestCode == PICK_FROM_ALBUM) {

            try {
                String path="data/data/dev.dain/files/profile.png";
                value=1;

                Bitmap pf_bit=MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                try {
                    File file = new File("profile.png");
                    FileOutputStream fos = openFileOutput("profile.png", 0);
                    pf_bit.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(this,"이미지 파일이 있습니다",0).show();
                }catch (Exception e)
                {
                    Toast.makeText(this,"이미지 파일이 없습니다",0).show();
                }
                SharedPreferencesActivity pref = new SharedPreferencesActivity(MainActivity.this);
                pref.savePreferences("imagepath",path);
                pref.savePreferences("value",value);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (dtToggle.onOptionsItemSelected(item))
            return true;
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
/*


	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				contentMain.setBackgroundColor(Color.parseColor("#A52A2A"));
				break;
			case 1:
				contentMain.setBackgroundColor(Color.parseColor("#5F9EA0"));
				break;

			}
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}
	*/

}
