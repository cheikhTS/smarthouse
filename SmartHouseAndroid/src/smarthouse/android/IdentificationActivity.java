package smarthouse.android;

import roboguice.inject.InjectView;
import smarthouse.android.robosherlock.RoboSherlockActivity;
import smarthouse.android.utils.FonctionsUtiles;
import smarthouse.android.utils.PairSerializable;
import smarthouse.android.webservices.WSAccess;
import smarthouse.ejb.service.Home;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;

public class IdentificationActivity extends RoboSherlockActivity implements OnClickListener {
	public final static String TAG = IdentificationActivity.class.getSimpleName();

	@InjectView(R.id.edt_identifiant)
	private EditText edtIdentifiant;
	@InjectView(R.id.edt_password)
	private EditText edtPassword;
	@InjectView(R.id.btn_connexion)
	private Button btnConnexion;
	@InjectView(R.id.cb_memorizeId)
	private CheckBox cbIdentifiant;

	private ActionBar actionBar;
	private PairSerializable<String, String> homeDesc;

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent indent = new Intent(IdentificationActivity.this, AccueilActivity.class);
		NavUtils.navigateUpTo(this, indent);
		finish();
	}

	/** 
	 *
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identification);
		
		// Récupération des extras passés par l'activité précédente
		homeDesc = (PairSerializable<String, String>) getIntent().getExtras().getSerializable("homeDesc");
		
		// init data
		edtIdentifiant.setText(getPreferences(MODE_PRIVATE).getString("identifiant."+homeDesc.getFirst(), ""));
		cbIdentifiant.setChecked((edtIdentifiant.getText().toString().isEmpty()) ? false : true);
		// Ajout des listeners
		btnConnexion.setOnClickListener(this);
		
		//restauration de la session de (3H)
		long time = getPreferences(MODE_PRIVATE).getLong("session."+homeDesc.getFirst(), 0);
		if (System.currentTimeMillis()-time<3*60*60*1000){
			WSAccess ws = WSAccess.getInstance(homeDesc.getFirst());
			ws.new HomeTask(){
				ProgressDialog progressDialog;
				@Override
				protected void onPreExecute() {
					progressDialog = FonctionsUtiles.makeProgressDialog(IdentificationActivity.this);
					progressDialog.setMessage("Restauration de la session ...");
					progressDialog.show();
				}
				
				@Override
				protected void onPostExecute(Home result) {
					if ( result != null ){
						//progressDialog.setMessage("Chargement des données ...");
						Intent intent = new Intent(IdentificationActivity.this, ZoneActivity.class);
						intent.putExtra("home", result);
						intent.putExtra("homeDesc", homeDesc);
						startActivity(intent);
						finish();
						progressDialog.dismiss();
					}
				}
			}.execute();
		}

		configureActionBar();
	}

	private void configureActionBar() {
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(getString(R.string.connexion) + " " + homeDesc.getSecond());
		actionBar.setIcon(null);
	}

	/**
	 * Enregistre ou non l'identifiant dans la lib partagee
	 */
	private void doMemorizeID() {
		SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE).edit();
		if ( cbIdentifiant.isChecked() ) {
			edit.putString("identifiant."+homeDesc.getFirst(), edtIdentifiant.getText().toString());
			edit.commit();
		}
	}

	private void doAuthentication(){
		final WSAccess ws = WSAccess.getInstance(homeDesc.getFirst());
		
		ws.new AuthenticationTask() {
			ProgressDialog progressDialog;
			
			@Override
			protected void onPreExecute() {
				progressDialog = FonctionsUtiles.makeProgressDialog(IdentificationActivity.this);
				progressDialog.setMessage("Authentification avec le serveur ...");
				progressDialog.show();
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if (result != null && result){
					//Auth OK , on enregistre une session
					SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE).edit();
					edit.putLong("session."+homeDesc.getFirst(), System.currentTimeMillis());
					edit.commit();
					
					progressDialog.setMessage("Chargement des données ...");
					ws.new HomeTask(){
						@Override
						protected void onPostExecute(Home result) {
							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
							
							if ( result != null ){
								Intent intent = new Intent(IdentificationActivity.this, ZoneActivity.class);
								intent.putExtra("home", result);
								intent.putExtra("homeDesc", homeDesc);
								startActivity(intent);
								finish();
							}
						}
					}.execute();
				}
				else {
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					
					Toast.makeText(IdentificationActivity.this, R.string.err_msg_authentication, Toast.LENGTH_SHORT).show();
				}
		    }
		}.execute(edtIdentifiant.getText().toString(), edtPassword.getText().toString());
	}
	
	@Override
	public void onClick(View v) {
		if ( v == btnConnexion ) {
			doMemorizeID();
			doAuthentication();
		}

	}
}
