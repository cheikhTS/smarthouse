package smarthouse.android.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import smarthouse.android.EquipmentActivity;
import smarthouse.android.R;
import smarthouse.android.ZoneActivity;
import smarthouse.android.webservices.WSAccess;
import smarthouse.ejb.service.Equipment;
import smarthouse.ejb.service.Room;
import smarthouse.ejb.service.RuntimeEquipmentInfos;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoomExpandableListAdapter extends BaseExpandableListAdapter {
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	private Context context;
	private List<Room> groups;
	private ArrayList<List<Equipment>> children = new ArrayList<List<Equipment>>();
	private HashMap<Integer, Long> hashmap = new HashMap<Integer, Long>();

	public RoomExpandableListAdapter ( Context context, List<Room> groups ) {
		this.context = context;
		this.groups = groups;
		for ( int i = 0; i < groups.size(); i++ ) {
			children.add(groups.get(i).getEquipments());
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return children.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	// Return a child view. You can load your custom layout here.
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Equipment equipment = (Equipment) getChild(groupPosition, childPosition);
		if ( convertView == null ) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.equipment_layout, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tvEquipment);
		tv.setText(equipment.getName());
		
		
		
		final ImageView iv_state = (ImageView) convertView.findViewById(R.id.iv_stateEq);
		ImageButton iv = (ImageButton) convertView.findViewById(R.id.ImageViewEquipment);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, EquipmentActivity.class);
				intent.putExtra("equipment", equipment);
				intent.putExtra("homeDesc", ((ZoneActivity)context).getHomeDesc());
				context.startActivity(intent);
			}
		});
		
		
		//Meth pour eviter la surcharge de ping Si la clé nexiste pas, on la rajoute
		if (hashmap.get(equipment.getId()) == null){
			hashmap.put(equipment.getId(), System.currentTimeMillis()-5000);
		}
		if (System.currentTimeMillis()-hashmap.get(equipment.getId())>1000){
			WSAccess ws = WSAccess.getInstance(((ZoneActivity)context).getHomeDesc().getFirst());
			ws.new EquipmentInfoTask(){
				@Override
				protected void onPostExecute(RuntimeEquipmentInfos result) {
					if ( result != null ){
						if (result.getState().equalsIgnoreCase("ok")){
							iv_state.setImageResource(R.drawable.state_on);
						}else if (result.getState().equalsIgnoreCase("ko")){
							iv_state.setImageResource(R.drawable.state_off);
						}else{
							iv_state.setImageResource(R.drawable.state_un);
						}
					}
			    }
			}.execute(equipment);
			hashmap.put(equipment.getId(), System.currentTimeMillis());
		}
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 100);
		convertView.setLayoutParams(params);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return children.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// Return a group view. You can load your custom layout here.
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		Room room = (Room) getGroup(groupPosition);
		if ( convertView == null ) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.room_layout, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tvRoom);
		tv.setText(room.getName());
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 100);
		convertView.setLayoutParams(params);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}
}