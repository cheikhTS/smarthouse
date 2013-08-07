package smarthouse.android.adapter;

import java.util.List;

import smarthouse.android.fragment.ScenarioFragment;
import smarthouse.ejb.service.Scenario;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ScenarioPagerAdapter extends FragmentPagerAdapter {

	private List<Scenario> scenarios;

	public ScenarioPagerAdapter ( FragmentManager fm, List<Scenario> _scenarios ) {
		super(fm);
		scenarios = _scenarios;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new ScenarioFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("scenario", scenarios.get(position));
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return scenarios.size();
	}

}
