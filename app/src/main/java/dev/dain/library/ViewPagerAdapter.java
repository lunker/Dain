package dev.dain.library;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dev.dain.event.EventFragment;
import dev.dain.home.HomeFragment;
import dev.dain.ranking.RankingFragment;
import dev.dain.rating.RatingFragment;

/**
 * Created by lunker on 15. 2. 22..
 */




public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0 : HomeFragment tab1 = new HomeFragment();return tab1;
            case 1 : RatingFragment tab2 = new RatingFragment();return tab2;
            case 2 : RankingFragment tab3 = new RankingFragment(); return tab3;
            case 3 : EventFragment tab4 = new EventFragment(); return tab4;
        }

        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}



