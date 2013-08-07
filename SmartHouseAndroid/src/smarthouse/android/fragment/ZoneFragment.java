package smarthouse.android.fragment;

import java.util.List;

import smarthouse.android.R;
import smarthouse.android.adapter.RoomExpandableListAdapter;
import smarthouse.android.robosherlock.RoboSherlockFragment;
import smarthouse.ejb.service.Area;
import smarthouse.ejb.service.Room;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ScrollView;

public class ZoneFragment extends RoboSherlockFragment {
	ScrollView scView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_zone, container, false);
		Bundle bundle = getArguments();
		Area area = (Area) bundle.getSerializable("area");
		List<Room> rooms = area.getRooms();
		
		ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.expandable_list_view_zones);
		
		RoomExpandableListAdapter adapter = new RoomExpandableListAdapter(v.getContext(), rooms);
		listView.setAdapter(adapter);

		return v;
	}
	
}
