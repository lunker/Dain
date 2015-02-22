package dev.dain;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.dain.library.SlidingTabLayout;
import dev.dain.library.ViewPagerAdapter;

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
	String[] arCafe = new String[] { "StarBucks", "카페베네", "망고식스", "투썸플레이스",
			"커피빈" };
	String[][] arDetails = new String[][] { { "음료", "푸드", "전체보기", "베스트10평가" },
			{ "음료", "푸드", "전체보기", "베스트10평가" },
			{ "음료", "푸드", "전체보기", "베스트10평가" },
			{ "음료", "푸드", "전체보기", "베스트10평가" },
			{ "음료", "푸드", "전체보기", "베스트10평가" } };


    private Toolbar toolbar = null;

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home","평가하기","랭킹","할인&이벤트"};
    int Numboftabs =4;

    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;



    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_main);

		// mTitle = mDrawerTitle = getTitle(); // 액션바 제목
		mSideList = getResources().getStringArray(R.array.side_array);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.drawer_list_item, mSideList));
//		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

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
                return getResources().getColor(R.color.accent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);
		// 확장리스트
		
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
				new String[] { "cafe" }, new int[] { android.R.id.text1 },
				detailData, android.R.layout.simple_expandable_list_item_1,
				new String[] { "details" }, new int[] { android.R.id.text1 });
//		mList.setAdapter(adapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (mDrawToggle.onOptionsItemSelected(item))
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
