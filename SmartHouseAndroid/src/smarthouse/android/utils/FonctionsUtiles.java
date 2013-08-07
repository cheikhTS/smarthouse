package smarthouse.android.utils;

import smarthouse.android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Classe contenant diverses fonctions utiles à l'application : FonctionsUtiles
 */
public class FonctionsUtiles {

	@SuppressWarnings("unused")
	private static final String TAG = FonctionsUtiles.class.getSimpleName();

	/**
	 * Méthode de création d'une boite de dialogue de type 'AlertDialog'
	 * 
	 * @param titre : titre de la boite de dialogue
	 * @param message : message affichée dans la boite de dialogue
	 * @param builder : contexte dans lequel la boite de dialogue est crée
	 * @return ad : boite de dialogue crée
	 */
	public static AlertDialog getAlertDialog(String titre, String message, Context builder) {
		// création de l'instance de la boite de dialogue
		final AlertDialog ad = new AlertDialog.Builder(builder).create();
		// titre de l'AlertDialog --> Manque d'informations
		ad.setTitle(titre);
		// message --> Certaines informations obligatoires n'ont pas étés
		// renseignées.
		if ( message != null ) {
			ad.setMessage(message);
		}

		return ad;

	}

	/**
	 * Affiche un toast avec le message correspond au resId passé.
	 * 
	 * @param ctx
	 * @param resId
	 */
	public static void toast(final Activity activity, final int resId) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast toast = Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_LONG);
				toast.setGravity(Gravity.BOTTOM, 0, 10);
				toast.show();
			}
		});
	}

	/**
	 * Affiche une alert dialog avec un bouton OK
	 * 
	 * @param activity Contexte associé
	 * @param txtId_title Ressource id vers le titre de l'alert dialog
	 * @param txtId_msg Ressource id vers le message de l'alert dialog
	 */
	public static void alert(final Activity activity, final int txtId_title, final int txtId_msg) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle(txtId_title);
				builder.setMessage(txtId_msg);
				builder.setNeutralButton(R.string.btn_ok, null);
				builder.show();
			}
		});
	}

	/**
	 * Affiche une alert dialog avec un bouton OK
	 * 
	 * @param activity Contexte associé
	 * @param txtId_title Ressource id vers le titre de l'alert dialog
	 * @param msg Message de l'alert dialog
	 */
	public static void alert(final Activity activity, final int txtId_title, final String msg) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle(txtId_title);
				builder.setMessage(msg);
				builder.setNeutralButton(R.string.btn_ok, null);
				builder.show();
			}
		});
	}

	/**
	 * Permet d'afficher un boite type OK, avant de quitter l'application
	 * 
	 * @param activity
	 * @param txtId_title
	 * @param txtId_msg
	 */
	public static void alertToExit(final Activity activity, final int txtId_title, final int txtId_msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(R.string.err_connectServer_title);
		builder.setMessage(R.string.err_connectServer_msg);
		builder.setNeutralButton(R.string.btn_ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
			}
		});
		builder.show();
	}
	
	public static ProgressDialog makeProgressDialog(final Activity activity) {
		final ProgressDialog progressDialog = new ProgressDialog(activity);
		//progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		return progressDialog;
	}
}
