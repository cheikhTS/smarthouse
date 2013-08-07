package smarthouse.android.adapter;

import java.util.ArrayList;
import java.util.List;

import smarthouse.android.R;
import smarthouse.ejb.service.ATrigger;
import smarthouse.ejb.service.Action;
import smarthouse.ejb.service.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TaskExpandableListAdapter extends BaseExpandableListAdapter {
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	private Context context;
	private List<Task> groups;
	private ArrayList<ArrayList<Object>> children = new ArrayList<ArrayList<Object>>();

	public TaskExpandableListAdapter ( Context context, List<Task> groups ) {
		this.context = context;
		this.groups = groups;
		for ( int i = 0; i < groups.size(); i++ ) {
			ArrayList<Object> tmp = new ArrayList<Object>();
			tmp.addAll(groups.get(i).getTriggers());
			tmp.addAll(groups.get(i).getActions());
			children.add(tmp);
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
		Object object = getChild(groupPosition, childPosition);
		if ( convertView == null ) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.action_trigger_layout, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tvActionTrigger);

		if ( object instanceof Action ) {
			Action action = (Action) object;
			tv.setText(action.getMethod());
		} else if ( object instanceof ATrigger ) {
			ATrigger trigger = (ATrigger) object;
			tv.setText(trigger.getName());
		}
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 70);
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
		Task task = (Task) getGroup(groupPosition);
		if ( convertView == null ) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.task_layout, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tvTask);
		tv.setText(task.getName());
		
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