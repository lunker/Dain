package dev.dain;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by davidha on 2015. 2. 27..
 */
public class SearchViewActivity extends ActionBarActivity {
    private ArrayList<SearchList> arItem;
    Toolbar toolbar = null;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.expandActionView();
        SearchView searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("검색어를 입력하세요");
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setIconifiedByDefault(true);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return false;
    }
    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            ArrayAdapter<CharSequence> Adapter;
            Adapter=ArrayAdapter.createFromResource(SearchViewActivity.this,R.array.side_array,android.R.layout.simple_list_item_1);
            TextView text =(TextView)findViewById(R.id.recommend);


            ListView SearchList = (ListView) findViewById(R.id.searchview_list);
            SearchList.setAdapter(Adapter);
            text.setVisibility(View.GONE);
            return true;
        }
    };
}
 class SearchList {
    String Text;
    public SearchList(String mText)
    {
        Text=mText;
    }
}