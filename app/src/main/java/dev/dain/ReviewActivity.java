package dev.dain;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by davidha on 2015. 3. 4..
 */
public class ReviewActivity extends ActionBarActivity {
    Toolbar toolbar =null;
    Spinner spinner;
    String[] CafeBrand={"1","2","3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_review);

        toolbar=(Toolbar)findViewById(R.id.review_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner=(Spinner)findViewById(R.id.review_spinner);

        //CafeBrand=getResources().getStringArray(R.array.coffee_brand);

        SpinnerAdapter adapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_item,CafeBrand);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(adapter);


    }
}
