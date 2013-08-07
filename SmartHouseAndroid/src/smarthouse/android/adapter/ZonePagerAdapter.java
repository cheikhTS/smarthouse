package smarthouse.android.adapter;

import java.util.List;

import smarthouse.android.fragment.ZoneFragment;
import smarthouse.ejb.service.Area;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ZonePagerAdapter extends FragmentPagerAdapter {

	private List<Area> areas;

	public ZonePagerAdapter ( FragmentManager fm, List<Area> _areas ) {
		super(fm);
		areas = _areas;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new ZoneFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("area", areas.get(position));
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return areas.size();
	}

}
