package dev.dain;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Created by davidha on 2015. 2. 27..
 */
public class SearchViewActivity extends ActionBarActivity {
    ArrayList<SearchList> arItem;
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
        sl = new SearchList("카페베네");
        sl = new SearchList("투썸플레이스");
        SearchViewAdapter searchAdapter = new SearchViewAdapter(this, R.layout.searchview_list_item, arItem);
        ListView SearchList = (ListView) findViewById(R.id.searchview_list);
        SearchList.setAdapter(searchAdapter);
    }
}
