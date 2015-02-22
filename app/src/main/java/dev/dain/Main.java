package dev.dain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;

public class Main extends Activity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.left_menu);

		// mTitle = mDrawerTitle = getTitle(); // 액션바 제목
		mSideList = getResources().getStringArray(R.array.side_array);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		contentMain = (FrameLayout) findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList.setAdapter(new ArrayAdapter<String>(Main.this,
				R.layout.drawer_list_item, mSideList));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setHomeButtonEnabled(true);

		mDrawToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		/*
		 * if (savedInstanceState == null) { selectItem(0); }
		 */

		mTab = (TabHost) findViewById(android.R.id.tabhost);
		mTab.setup();

		mTab.addTab(mTab.newTabSpec("tag").setIndicator("Home")
				.setContent(R.id.tab1));

		mTab.addTab(mTab.newTabSpec("tag").setIndicator("평가하기")
				.setContent(R.id.tab2));

		mTab.addTab(mTab.newTabSpec("tag").setIndicator("다인랭킹")
				.setContent(R.id.tab3));

		mTab.addTab(mTab.newTabSpec("tag").setIndicator("할인/이벤트")
				.setContent(R.id.tab4));
		
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
		mList.setAdapter(adapter);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		mDrawToggle.syncState();
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
		if (mDrawToggle.onOptionsItemSelected(item))
			return true;
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawToggle.onConfigurationChanged(newConfig);
	}

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
	/*
	 * private void selectItem(int position) {
	 * 
	 * }
	 * 
	 * public void setTitle(CharSequence title) { mTitle = title;
	 * getActionBar().setTitle(mTitle); }
	 */
}
