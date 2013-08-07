package smarthouse.android;

import roboguice.inject.InjectView;
import smarthouse.android.adapter.ZonePagerAdapter;
import smarthouse.android.robosherlock.RoboSherlockFragmentActivity;
import smarthouse.android.utils.PairSerializable;
import smarthouse.ejb.service.Area;
import smarthouse.ejb.service.Home;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ZoneActivity extends RoboSherlockFragmentActivity implements OnPageChangeListener, TabListener {

	@InjectView(R.id.zonePager)
	ViewPager pager;
	private Home home;
	private PairSerializable<String, String> homeDesc;

	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(ZoneActivity.this, AccueilActivity.class);
		NavUtils.navigateUpTo(this, intent);
		// finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zone);		
		
		home = (Home) getIntent().getExtras().getSerializable("home");
		homeDesc = (PairSerializable<String, String>) getIntent().getExtras().getSerializable("homeDesc");
		configureViewPager();
		configureActionBar();
	}

	private void configureViewPager() {
		ZonePagerAdapter zonePagerAdapter = new ZonePagerAdapter(getSupportFragmentManager(), home.getAreas());
		pager.setAdapter(zonePagerAdapter);
		pager.setOnPageChangeListener(this);
	}

	public void onPageSelected(int position) {
		Tab tab = getSupportActionBar().getTabAt(position);
		getSupportActionBar().selectTab(tab);
	}

	private void configureActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getSupportActionBar().setTitle("SmartHouse - " + home.getName());
		for ( Area a : home.getAreas() ) {
			Tab tab = getSupportActionBar().newTab();
			tab.setText(a.getName());
			tab.setTabListener(this);
			getSupportActionBar().addTab(tab);
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		pager.setCurrentItem(position);
	}

	@Override
	public void onPageScrollStateChanged(int position) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.option_zone, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
			case R.id.menu_scenarios:
				Intent intent = new Intent(ZoneActivity.this, ScenarioActivity.class);
				intent.putExtra("home", home);
				intent.putExtra("homeDesc", homeDesc);
				startActivity(intent);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public PairSerializable<String, String> getHomeDesc() {
		return homeDesc;
	}

	public void setHomeDesc(PairSerializable<String, String> homeDesc) {
		this.homeDesc = homeDesc;
	}
	
}