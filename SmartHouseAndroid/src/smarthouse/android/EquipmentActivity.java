package smarthouse.android;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import smarthouse.android.robosherlock.RoboSherlockActivity;
import smarthouse.android.utils.FonctionsUtiles;
import smarthouse.android.utils.PairSerializable;
import smarthouse.android.webservices.WSAccess;
import smarthouse.ejb.service.ActionRequest;
import smarthouse.ejb.service.ActionResponse;
import smarthouse.ejb.service.Equipment;
import smarthouse.ejb.service.RuntimeActionDef;
import smarthouse.ejb.service.RuntimeEquipmentInfos;
import smarthouse.ejb.service.RuntimeParamDef;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EquipmentActivity extends RoboSherlockActivity implements OnItemSelectedListener, OnClickListener {

	@InjectView(R.id.txt_equipmentName)
	private TextView txtequipmentName;
	
	@InjectView(R.id.tv_params)
	private TextView tv_Params;
	
	@InjectView(R.id.spi_methods)
	private Spinner spi_Methods;
	
	@InjectView(R.id.ll_params)
	private LinearLayout llparams;
	
	@InjectView(R.id.bt_sendAction)
	private Button bt_send;

	private Equipment equipment;
	private RuntimeEquipmentInfos equipmentInfo;
	private PairSerializable<String, String> homeDesc;
	private ArrayAdapter<RuntimeActionDef> aa;
	private ArrayList<View> paramsView = new ArrayList<View>();

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment);
		
		aa = new ArrayAdapter<RuntimeActionDef>(EquipmentActivity.this, android.R.layout.simple_spinner_item);
		// Show the Up button in the action bar.
		setupActionBar();

		homeDesc = (PairSerializable<String, String>) getIntent().getExtras().getSerializable("homeDesc");
		equipment = (Equipment) getIntent().getExtras().get("equipment");
		
		setTitle(getText(R.string.equipmentName) + equipment.getName());
		
		
		WSAccess ws = WSAccess.getInstance(homeDesc.getFirst());
		ws.new EquipmentInfoTask() {
			ProgressDialog progressDialog;
			
			@Override
			protected void onPreExecute() {
				progressDialog = FonctionsUtiles.makeProgressDialog(EquipmentActivity.this);
				progressDialog.setMessage("Récupération des informations ...");
				progressDialog.show();
			}
			
			@Override
			protected void onPostExecute(RuntimeEquipmentInfos result) {
				if ( result != null ){
					equipmentInfo = result;
					txtequipmentName.setText(Html.fromHtml("<b>Nom du matériel </b>: " + equipmentInfo.getHardwareName() + " v" + equipmentInfo.getDriverVersion()
							+ "<br><b>Fabriquant</b> : " + equipmentInfo.getHardwareManufacturer() 
							+ "<br><b>Description</b> : " + equipmentInfo.getHardwareDescription()
							+ "<br>"));
					//Ajouts des methods dans le spinner
					aa.addAll(result.getActions());
					aa.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
					spi_Methods.setAdapter(aa);
					
					
					progressDialog.dismiss();
				}
		    }
		}.execute(equipment);
		
		spi_Methods.setOnItemSelectedListener(this);
		bt_send.setOnClickListener(this);
	}

	private void genererParams(RuntimeActionDef acDef){
		llparams.removeAllViews();
		paramsView.clear();
		if (acDef.getParams().isEmpty()){
			tv_Params.setVisibility(View.GONE);
		}else{
			tv_Params.setVisibility(View.VISIBLE);
		}
		for (RuntimeParamDef rpd : acDef.getParams()){
			llparams.addView(genererViewParam(rpd));
		}
	}
	
	private LinearLayout genererViewParam(RuntimeParamDef rpd){
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		TextView tv_label = new TextView(this);
		tv_label.setText(Html.fromHtml("<b>"+rpd.getName() +"</b> - " + rpd.getDescription()));
		
		ll.addView(tv_label);
		SeekBar sb = new SeekBar(this);
		sb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		final TextView tv_value = new TextView(this);
		tv_value.setText("Valeur - " );
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				tv_value.setText("Valeur - " + progress);
			}
		});
		
		if (rpd.getType().equals("MULTI_VALUES")){
			Spinner spi = new Spinner(this);
			ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rpd.getLabels());
			spi.setAdapter(aa);
			//spi.setMinimumHeight(50);
			ll.addView(spi);
			paramsView.add(spi);
		}else if (rpd.getType().equals("LIMITED_INTEGER")){
			//sb.setMax(rpd.getMax());
			ll.addView(tv_value);
			ll.addView(sb);
			paramsView.add(sb);
		}else if (rpd.getType().equals("LIMITED_FLOAT")){
			//sb.setMax(rpd.getMax());
			//sb.setKeyProgressIncrement(Integer.parseInt(""+rpd.getStep()));
			ll.addView(tv_value);
			ll.addView(sb);
			paramsView.add(sb);
		}else if (rpd.getType().equals("LIMITED_STRING")){
			EditText et = new EditText(this);
			et.setFilters(new InputFilter[] {new InputFilter.LengthFilter(rpd.getMax())});
			et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			ll.addView(et);
			paramsView.add(et);
		}
		return ll;
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (arg0 == spi_Methods){
			RuntimeActionDef rad = aa.getItem(position);
			if (rad != null){
				genererParams(rad);
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
		switch ( itemId ) {
			case android.R.id.home:
				Intent indent = new Intent(EquipmentActivity.this, AccueilActivity.class);
				indent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, indent);
				finish();
			break;
		}

		return true;
	}
	
	private List<String> convertAllViewToData(RuntimeActionDef acDef){
		List<String> ret = new ArrayList<String>();
		int index = 0;
		for (RuntimeParamDef rpd : acDef.getParams()){
			if (rpd.getType().equals("MULTI_VALUES")){
				ret.add(((Spinner)paramsView.get(index)).getSelectedItem().toString());
			}else if (rpd.getType().equals("LIMITED_INTEGER")){
				ret.add(""+((SeekBar)paramsView.get(index)).getProgress());
			}else if (rpd.getType().equals("LIMITED_FLOAT")){
				ret.add(""+((SeekBar)paramsView.get(index)).getProgress());
			}else if (rpd.getType().equals("LIMITED_STRING")){
				ret.add(((EditText)paramsView.get(index)).getText().toString());
			}
			index++;
		}
		return ret;
	}

	@Override
	public void onClick(View v) {
		if (v == bt_send){
			final WSAccess ws = WSAccess.getInstance(homeDesc.getFirst());
			ActionRequest ar = new ActionRequest();
			ar.setIdEquipment(equipment.getId());
			ar.setAction(aa.getItem(spi_Methods.getSelectedItemPosition()).getActionName());
			ar.setParams(convertAllViewToData(aa.getItem(spi_Methods.getSelectedItemPosition())));
			ws.new ExecuteActionTask(){
				ProgressDialog progressDialog;
				
				@Override
				protected void onPreExecute() {
					progressDialog = FonctionsUtiles.makeProgressDialog(EquipmentActivity.this);
					progressDialog.setMessage("Envoi de l'action...");
					progressDialog.show();
				}
				
				@Override
				protected void onPostExecute(ActionResponse result) {
					if ( result != null ){
						Toast.makeText(EquipmentActivity.this, result.getResult(), Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(EquipmentActivity.this, "Une erreur c'est produite !", Toast.LENGTH_SHORT).show();
					}
					progressDialog.dismiss();
			    }
			}.execute(ar);
		}
	}
}
