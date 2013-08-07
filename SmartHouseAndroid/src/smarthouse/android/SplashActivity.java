package smarthouse.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import smarthouse.android.robosherlock.RoboSherlockActivity;
import smarthouse.android.utils.PairSerializable;
import smarthouse.ejb.service.Area;
import smarthouse.ejb.service.DriverIdentifier;
import smarthouse.ejb.service.Equipment;
import smarthouse.ejb.service.Home;
import smarthouse.ejb.service.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

public class SplashActivity extends RoboSherlockActivity implements Runnable {
	private static final String TAG = SplashActivity.class.getSimpleName();

	/*public static Home generateHome(){
		Random  r = new Random();
		DriverIdentifier drivId1 = new DriverIdentifier(
				"smarthouse.fmk.driver.impl.SimulatedLightLapeyreDriver",
				"BundleDrivers.jar");
		DriverIdentifier drivId2 = new DriverIdentifier(
				"smarthouse.fmk.driver.impl.SimulatedOvenBoschDriver",
				"BundleDrivers.jar");
			
			// Creation de la maison
			List<Area> areas = new ArrayList<Area>();
			for (int i = 0; i < 3; i++) {
				List<Room> rooms = new ArrayList<Room>();
				for (int j = 0; j < 3; j++) {
					List<Equipment> equipments = new ArrayList<Equipment>();
					int idEquipment = r.nextInt();
					equipments.add(new Equipment(drivId1, "lampe" + idEquipment, "id=LAMPE" + idEquipment));
					idEquipment = r.nextInt();
					equipments.add(new Equipment(drivId1, "lampe" + idEquipment, "id=LAMPE" + idEquipment));
					idEquipment = r.nextInt();
					equipments.add(new Equipment(drivId2, "Four" + idEquipment, "addr_ip=FOUR" + idEquipment));
					rooms.add(new Room(equipments, "room" + r.nextInt()));
				}
				areas.add(new Area("area" + r.nextInt(), rooms));
			}
			return new Home(areas, 1, "Home" + r.nextInt(), null);
	}*/
	
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			
			/*Mode test
			 * 
			 * Intent intent = new Intent(SplashActivity.this, ZoneActivity.class);
			Home h = generateHome();
			intent.putExtra("home", generateHome());
			intent.putExtra("homeDesc", new PairSerializable<String, String>("wwwww", h.getName()));
			startActivity(intent);
			finish();*/
			
			Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
			startActivity(intent);
			finish();
		}
	};

	/**
	 * Création de l'activité d'ouverture de l'application Après une certaine
	 * attente --> création de l'activité d'identification
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		launchWait();
	}

	private void launchWait() {
		// On lance le thread
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().detectAll().penaltyLog().build());
		// Test de connection au serveur

		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			//
			e.printStackTrace();
		}
		handler.sendEmptyMessage(0);
		
	}
}
