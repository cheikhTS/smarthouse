package smarthouse.android.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import smarthouse.android.R;
import smarthouse.android.utils.PairSerializable;
import smarthouse.android.webservices.WSAccess;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeGridviewAdapter extends BaseAdapter {
	
	private ArrayList<PairSerializable<String, String>> listHome;
	private Activity activity;
	private HashMap<String, Long> hashmap = new HashMap<String, Long>();

	public HomeGridviewAdapter ( Activity activity, ArrayList<PairSerializable<String, String>> listHome ) {
		super();
		this.listHome = listHome;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return listHome.size();
	}

	@Override
	public PairSerializable<String, String> getItem(int position) {
		return listHome.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public static class ViewHolder {
		public TextView txtViewTitle;
		public ImageView ivState;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if ( convertView == null ) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.home_layout, null);
			view.txtViewTitle = (TextView) convertView.findViewById(R.id.nomMaison);
			
			view.ivState = (ImageView) convertView.findViewById(R.id.iv_state);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		view.txtViewTitle.setText(listHome.get(position).getSecond());
		
		//Meth pour eviter la surcharge de ping Si la clé nexiste pas, on la rajoute
		if (hashmap.get(listHome.get(position).getFirst()) == null){
			hashmap.put(listHome.get(position).getFirst(), System.currentTimeMillis()-5000);
		}
		if (System.currentTimeMillis()-hashmap.get(listHome.get(position).getFirst())>1000){
			WSAccess ws = WSAccess.getInstance(listHome.get(position).getFirst());
			ws.new PingTask() {
				@Override
				protected void onPostExecute(Boolean result) {
					if (result != null){
						if (result){
							view.ivState.setImageResource(R.drawable.state_on);
						}else{
							view.ivState.setImageResource(R.drawable.state_off);
						}
					}
					
			    }
			}.execute();
			hashmap.put(listHome.get(position).getFirst(), System.currentTimeMillis());
		}	
		
		return convertView;
	}

}
