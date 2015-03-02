package dev.dain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by davidha on 2015. 2. 27..
 */
public class SearchViewActivity extends ActionBarActivity {
    private ArrayList<SearchList> arItem;
    Toolbar toolbar = null;
    List<String> mSideList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_searchview_activity);

        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        arItem = new ArrayList<SearchList>();
        SearchList sl;
        sl = new SearchList("스타벅스");
        arItem.add(sl);
        sl = new SearchList("카페베네");
        arItem.add(sl);
        sl = new SearchList("투썸플레이스");
        arItem.add(sl);

        SearchViewAdapter searchAdapter = new SearchViewAdapter(this, R.layout.searchview_list_item, arItem);
        ListView SearchList = (ListView) findViewById(R.id.searchview_list);
        SearchList.setAdapter(searchAdapter);

        mSideList = Arrays.asList(getResources().getStringArray(R.array.coffee_array));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.expandActionView();

        searchView = (SearchView)searchItem.getActionView();
        searchView.setQueryHint("검색어를 입력하세요");
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return false;
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {

            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {

            ListView SearchList = (ListView) findViewById(R.id.searchview_list);

            TextView text = (TextView) findViewById(R.id.recommend);
            text.setVisibility(View.GONE);

            if (TextUtils.isEmpty(s)) {

                SearchList.setVisibility(View.GONE);
            } else {
                SearchList.setVisibility(View.VISIBLE);
                displayResult(s);
            }
            return true;
        }
    };

    private void displayResult(String text) {
        String Text = text;
        AutoSearchAdapter searchViewAdapter = new AutoSearchAdapter(SearchViewActivity.this, R.layout.searchview_list_item, mSideList, Text);
        ListView SearchList = (ListView) findViewById(R.id.searchview_list);
        SearchList.setAdapter(searchViewAdapter);
        ((AutoSearchAdapter) SearchList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        SearchViewActivity.this.finish();
    }
}

class SearchList {
    String Text;

    public SearchList(String mText) {
        Text = mText;
    }
}