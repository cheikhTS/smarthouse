package smarthouse.android;

import java.lang.reflect.Type;
import java.util.ArrayList;

import roboguice.inject.InjectView;
import smarthouse.android.adapter.HomeGridviewAdapter;
import smarthouse.android.robosherlock.RoboSherlockActivity;
import smarthouse.android.utils.FonctionsUtiles;
import smarthouse.android.utils.PairSerializable;
import smarthouse.android.webservices.WSAccess;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AccueilActivity extends RoboSherlockActivity {

	public final static String TAG = AccueilActivity.class.getSimpleName();

	@InjectView(R.id.GridViewHome)
	private GridView gridView;
	private ActionBar actionBar;
	private ArrayList<PairSerializable<String, String>> homes = new ArrayList<PairSerializable<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
		configureActionBar();

		// recuperation des info
		SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE).edit();
		String t = getPreferences(MODE_PRIVATE).getString("homes", "");
		if (!t.isEmpty()) {
			try {
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<PairSerializable<String, String>>>() {
				}.getType();
				homes = gson.fromJson(t, type);
				Log.d("WS", homes.toString());
			} catch (Exception e) {
				Toast.makeText(this, R.string.err_msg_retriveHomes, Toast.LENGTH_SHORT).show();
			}

		}

		drawGridView();
	}

	public void drawGridView() {
		// prepared arraylist and passed it to the Adapter class
		HomeGridviewAdapter mAdapter = new HomeGridviewAdapter(this, homes);
		// Setting an adapter containing images to the GridView
		gridView.setAdapter(mAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AccueilActivity.this, IdentificationActivity.class);
				intent.putExtra("homeDesc", homes.get(position));
				startActivity(intent);
			}
		});

		registerForContextMenu(gridView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.option_accueil, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.add(0, info.position, 0, "Modifier");
		menu.add(0, info.position, 0, "Supprimer");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
			case R.id.menu_add:
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle(R.string.alert_homeAdd_title);

				LinearLayout ly = new LinearLayout(this);
				ly.setPadding(5, 5, 5, 5);
				ly.setOrientation(LinearLayout.VERTICAL);
				final EditText et_name = new EditText(this);
				final EditText et_dns = new EditText(this);
				final TextView tv = new TextView(this);
				tv.setText(R.string.alert_homeAdd_msgName);
				final TextView tv2 = new TextView(this);
				tv2.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
				tv2.setText(R.string.alert_homeAdd_msgDNS);
				ly.addView(tv);
				ly.addView(et_name);
				ly.addView(tv2);
				ly.addView(et_dns);
				alert.setView(ly);

				alert.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						if (!et_dns.getText().toString().isEmpty() && !et_name.getText().toString().isEmpty()){
							final WSAccess ws = WSAccess.getInstance(et_dns.getText().toString());
							ws.new PingTask() {
								ProgressDialog progressDialog;
								@Override
								protected void onPreExecute() {
									progressDialog = FonctionsUtiles.makeProgressDialog(AccueilActivity.this);
									progressDialog.setMessage("Tentative de connexion ... " + ws.getBase_URL());
									progressDialog.show();
								}
								
								@Override
								protected void onPostExecute(Boolean result) {
									if (result){
										homes.add(new PairSerializable<String, String>(et_dns.getText().toString(), et_name.getText().toString()));
										drawGridView();
									}
									else{
										Toast.makeText(AccueilActivity.this, R.string.err_msg_accessToHome, Toast.LENGTH_SHORT).show();
									}
									
									if (progressDialog.isShowing()) {
										progressDialog.dismiss();
									}
							    }
							}.execute();
						}
					}
				});

				alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public boolean onContextItemSelected(android.view.MenuItem item) {
		if (item.getTitle().equals("Modifier")) {
			alertDialogModify(item.getItemId());
			return true;
		} else if (item.getTitle().equals("Supprimer")) {
			alertDialogDelete(item.getItemId());
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void alertDialogDelete(final int position) {
		AlertDialog.Builder alert = new AlertDialog.Builder(AccueilActivity.this);
		alert.setTitle(R.string.alert_homeDelete_title);
		alert.setMessage(R.string.alert_homeDelete_msg);
		alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				homes.remove(position);
				drawGridView();
			}
		});
		alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});
		alert.show();
	}

	private void alertDialogModify(final int position) {
		PairSerializable<String, String> pair = homes.get(position);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(R.string.alert_homeAdd_title);

		LinearLayout ly = new LinearLayout(this);
		ly.setPadding(5, 5, 5, 5);
		ly.setOrientation(LinearLayout.VERTICAL);
		final EditText et_name = new EditText(this);
		et_name.setText(pair.getSecond());
		final EditText et_dns = new EditText(this);
		et_dns.setText(pair.getFirst());
		final TextView tv = new TextView(this);
		tv.setText(R.string.alert_homeAdd_msgName);
		final TextView tv2 = new TextView(this);
		tv2.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
		tv2.setText(R.string.alert_homeAdd_msgDNS);
		ly.addView(tv);
		ly.addView(et_name);
		ly.addView(tv2);
		ly.addView(et_dns);
		alert.setView(ly);

		alert.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if (!et_dns.getText().toString().isEmpty() && !et_name.getText().toString().isEmpty()) {
					WSAccess ws = WSAccess.getInstance(et_dns.getText().toString());
					ws.new PingTask() {
						ProgressDialog progressDialog;
						
						@Override
						protected void onPreExecute() {
							progressDialog = FonctionsUtiles.makeProgressDialog(AccueilActivity.this);
							progressDialog.setMessage("Tentative de connexion ...");
							progressDialog.show();
						}

						@Override
						protected void onPostExecute(Boolean result) {
							if (result) {
								homes.add(new PairSerializable<String, String>(et_dns.getText().toString(), et_name.getText().toString()));
								homes.remove(position);
								drawGridView();
							} else {
								Toast.makeText(AccueilActivity.this, R.string.err_msg_accessToHome, Toast.LENGTH_SHORT).show();
							}

							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
						}
					}.execute();
				}
			}
		});

		alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE).edit();
		Gson gson = new Gson();
		edit.putString("homes", gson.toJson(homes));
		edit.commit();

	}

	private void configureActionBar() {
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}
}
