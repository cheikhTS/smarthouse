package smarthouse.android.fragment;

import java.util.List;

import smarthouse.android.R;
import smarthouse.android.adapter.TaskExpandableListAdapter;
import smarthouse.android.robosherlock.RoboSherlockFragment;
import smarthouse.ejb.service.Scenario;
import smarthouse.ejb.service.Task;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ScrollView;

public class ScenarioFragment extends RoboSherlockFragment {
	ScrollView scView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_scenarios, container, false);
		Bundle bundle = getArguments();
		Scenario scenario = (Scenario) bundle.getSerializable("scenario");
		List<Task> tasks = scenario.getTasks();

		ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.expandable_list_view_scenarios);

		TaskExpandableListAdapter adapter = new TaskExpandableListAdapter(v.getContext(), tasks);

		listView.setAdapter(adapter);

		return v;
	}

}
