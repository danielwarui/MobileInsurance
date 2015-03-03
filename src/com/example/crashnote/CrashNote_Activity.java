package com.example.crashnote;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.crashnote.sqlitedb.DBHandler;
import com.mobile.insurance.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CrashNote_Activity extends Activity implements OnClickListener,
		LocationListener {
	private ImageButton imgbutton;
	private ImageView imgView1, imgView2, imgView3, imgView4;
	private Calendar cal;
	private int day;
	private int month;
	private int year;

	public String strnLicence, strnFName, strnAddress, strnContact, strnRegNo,
			strnCarMake, strnCarModel, strnInsurance, strnPoliceinc,
			strnFireInc;
	/*
	 * String strnLicence = edtlicence.getText().toString(); String strnFName =
	 * edtfullname.getText().toString(); String strnAddress =
	 * edtaddress.getText().toString(); String strnContact =
	 * edtcontactnumber.getText().toString(); String strnRegNo =
	 * edtregno.getText().toString(); String strnCarMake =
	 * edtcarmake.getText().toString(); String strnCarModel =
	 * edtcarmodel.getText().toString(); String strnInsurance =
	 * edtinsuranceco.getText().toString(); String strnPoliceinc =
	 * edtpoliceinc.getText().toString(); String strnFireInc =
	 * edtfireinc.getText().toString();
	 */
	private EditText edtdate, edtlicence, edtfullname, edtaddress,
			edtcontactnumber, edtregno, edtcarmake, edtcarmodel,
			edtinsuranceco, edtpoliceinc, edtfireinc;
	Intent intent;
	public double latitude;
	public double longitude;
	Date now = new Date();
	String strNow = new SimpleDateFormat("yyyy/MM/dd\n HH:mm:ss").format(now);
	String SQDate, SQName, SQCarName, SQModel, SQInsurance, SQRegistrationNo;
	long SQPolice, SQFire;
	public int intAddress;
	/*
	 * Long lngLiecence = Long.parseLong(strnLicence); Long lngContact =
	 * Long.parseLong(strnContact); Long lngPoliceInc =
	 * Long.parseLong(strnPoliceinc); Long lngAccidentInc =
	 * Long.parseLong(strnFireInc); int intAddress =
	 * Integer.parseInt(strnAddress);
	 */
	int SQaddress;
	TextView tvLoc;
	View act_crash;
	Button btnTackPic, btnsave;
	TextView tvHasCamera, tvHasCameraApp;
	ImageView ivThumbnailPhoto;
	Bitmap bitMap;
	String jbg = ".jpg";
	File file;
	Uri photoPath;
	String strnphotopath4, strnphotopath3, strnphotopath2, strnphotopath1;

	/**
	 * @return the photoPath
	 */

	ConnectionDetector cd;
	GPSTracker gps;
	AlertDialogManager alert = new AlertDialogManager();

	static int TAKE_PICTURE = 1;
	static int TAKE_PICTURE1 = 100;
	static int TAKE_PICTURE2 = 200;
	static int TAKE_PICTURE3 = 300;
	static int RESULT_LOAD_IMAGE = 401;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crashnote);
		// mDateButton = (Button) findViewById(R.id.date_button);
		Log.i("Activity Crash Starts", "Start Successful");
		imgbutton = (ImageButton) findViewById(R.id.imageButton1);
		btnsave = (Button) findViewById(R.id.btnSave);
		imgView1 = (ImageView) findViewById(R.id.tapphoto1);
		imgView2 = (ImageView) findViewById(R.id.tapphoto2);
		imgView3 = (ImageView) findViewById(R.id.tapphoto3);
		imgView4 = (ImageView) findViewById(R.id.tapphoto4);
		edtdate = (EditText) findViewById(R.id.edtDate);
		edtlicence = (EditText) findViewById(R.id.edtLicenceNo);
		edtfullname = (EditText) findViewById(R.id.edtFullName);
		edtaddress = (EditText) findViewById(R.id.edtAddress);
		edtcontactnumber = (EditText) findViewById(R.id.edtContactNumber);
		edtregno = (EditText) findViewById(R.id.edtRegNo);
		edtcarmake = (EditText) findViewById(R.id.edtCarMake);
		edtcarmodel = (EditText) findViewById(R.id.edtModel);
		edtinsuranceco = (EditText) findViewById(R.id.edtInsuranceCompany);
		edtpoliceinc = (EditText) findViewById(R.id.edtPoliceInc);
		edtfireinc = (EditText) findViewById(R.id.edtFireInc);

		Log.i("Find view", "Success");

		Log.i("Oncreate", "Crashnote Activity");

		// getCurrentLocation(latitude,longitude);

		// creating GPS Class object
		gps = new GPSTracker(this);

		// check if GPS location can get
		if (gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude()
					+ ", longitude: " + gps.getLongitude());

		} else {
			// Can't get user's current location
			alert.showAlertDialog(CrashNote_Activity.this, "GPS Status",
					"Couldn't get location information. Please enable GPS",
					false);
			// stop executing code by return
			return;

		}
		latitude = gps.getLatitude();
		longitude = gps.getLongitude();
		Log.i("Oncreate", "getCurrent Location success");
		String parserLong = Double.toString(longitude);
		String parserLat = Double.toString(latitude);

		tvLoc = (TextView) findViewById(R.id.tvLocation);

		tvLoc.setText(parserLat + ", " + parserLong);
		Log.i("Oncreate", parserLat + ", " + parserLong);
		imgbutton.setOnClickListener(this);
		imgView1.setOnClickListener(this);
		imgView2.setOnClickListener(this);
		imgView3.setOnClickListener(this);
		imgView4.setOnClickListener(this);
		btnsave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean x = true;
				// TODO Auto-generated method stub
				if (v.getId() == R.id.btnSave) {
					
					 Log.d("Inserting", "Values");
					 Log.i("Button Save ", "EditText.toString");
						getDate();
						strnLicence = edtlicence.getText().toString();
						strnFName = edtfullname.getText().toString();
						strnAddress = edtaddress.getText().toString();
						strnContact = edtcontactnumber.getText().toString();
						strnRegNo = edtregno.getText().toString();
						strnCarMake = edtcarmake.getText().toString();
						strnCarModel = edtcarmodel.getText().toString();
						strnInsurance = edtinsuranceco.getText().toString();
						strnPoliceinc = edtpoliceinc.getText().toString();
						strnFireInc = edtfireinc.getText().toString();

						latitude = gps.getLatitude();
						longitude = gps.getLongitude();
						/*
						 * lngContact = Long.parseLong(strnContact); lngPoliceInc =
						 * Long.parseLong(strnPoliceinc); lngAccidentInc =
						 * Long.parseLong(strnFireInc); intAddress =
						 * Integer.parseInt(strnAddress);
						 */

						if (strnLicence.equals("")) {
							x = false;
							edtlicence.setError("Licence number please");
							
						}

						if (strnFName.equals("")) {
							x = false;
							edtfullname.setError("Full name is required");
						}

						if (strnAddress.equals("")) {
							x = false;
							edtaddress.setError("Address required");
						}

						if (strnContact.equals("")) {
							x = false;
							edtcontactnumber.setError("Contact number required");
						}

						if (strnRegNo.equals("")) {
							x = false;
							edtregno.setError("Registration number");
						}

						if (strnCarMake.equals("")) {
							x = false;
							edtcarmake.setError("Car make required");
						}

						if (strnCarModel.equals("")) {
							x = false;
							edtcarmodel.setError("Car model required");
						}

						if (strnInsurance.equals("")) {
							x = false;
							edtinsuranceco.setError("Insurance company required");
						}

						if (strnPoliceinc.equals("")) {
							x = false;
							edtpoliceinc.setError("Police number is required");
						}
						if (strnFireInc.equals("")) {
							x = false;
							edtfireinc.setError("Fire insurance number is required");
						}
					/*
					 * strnLicence = edtlicence.getText().toString();
						strnFName = edtfullname.getText().toString();
						strnAddress = edtaddress.getText().toString();
						strnContact = edtcontactnumber.getText().toString();
						strnRegNo = edtregno.getText().toString();
						strnCarMake = edtcarmake.getText().toString();
						strnCarModel = edtcarmodel.getText().toString();
						strnInsurance = edtinsuranceco.getText().toString();
						strnPoliceinc = edtpoliceinc.getText().toString();
						strnFireInc = edtfireinc.getText().toString();
					 * */
						DBHandler db = new DBHandler(getApplicationContext());
					/*	
						int i;
						EditText edt[]={edtlicence,edtfullname,edtaddress,edtcontactnumber,edtregno,edtcarmake,edtcarmodel,edtinsuranceco,edtpoliceinc,edtfireinc};
						String validate[] ={strnLicence,strnFName,strnAddress,strnContact,strnRegNo,strnCarMake,strnCarModel,strnInsurance,strnPoliceinc,strnFireInc};
						for(i = 0; i<10;){
						if(validate[i] != ""){
							 i++;
						}
						else{
							edt[i].setError("Fill in the details here");
						}
						
					
						}
						*/
						if (x == true){
						db.addCrashNote(new CrashNote(strNow, longitude,
								 latitude, strnFName, strnLicence, strnAddress,
								 strnContact, strnRegNo, strnCarMake, strnCarModel,
								 strnInsurance, strnPoliceinc, strnFireInc,
								 strnphotopath1, strnphotopath2, strnphotopath3,
								 strnphotopath4));
					Toast.makeText(getApplicationContext(), "Crash Quote Inserted",
							1500).show();
						}
					/*Intent next = new Intent(getApplicationContext(), Main_CrashNote.class);
					startActivity(next);*/
					Log.d("Inserting", "Success");
				}

			}
		});
		Log.d("Load Preferences", "Start");


	}
	
	
/*
	private void saveLicence(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveFullName(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveAddress(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveContact(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveRegistration(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveCarMake(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveCarModel(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveInsuranceModel(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void savePoliceIncident(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveFireIncident(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}*/
/*
	private void loadPreferences() {
		Log.d("Load Preferences", "Start Official");

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String saveLicence = pref.getString("saveLicence", "");
		edtlicence.setText(saveLicence);
		Log.d("Load Preferences", "saveLicence...");

		String saveFullName = pref.getString("saveFullName", "");
		edtfullname.setText(saveFullName);

		String saveAddress = pref.getString("saveAddress", "");
		edtaddress.setText(saveAddress);

		String saveContact = pref.getString("saveContact", "");
		edtcontactnumber.setText(saveContact);

		String saveRegistration = pref.getString("saveRegistration", "");
		edtregno.setText(saveRegistration);

		String saveCarMake = pref.getString("saveCarMake", "");
		edtcarmake.setText(saveCarMake);

		String saveCarModel = pref.getString("saveCarModel", "");
		edtcarmodel.setText(saveCarModel);

		String saveInsuranceModel = pref.getString("saveInsuranceModel", "");
		edtinsuranceco.setText(saveInsuranceModel);

		String savePoliceIncident = pref.getString("savePoliceIncident", "");
		edtpoliceinc.setText(savePoliceIncident);

		String saveFireIncident = pref.getString("saveFireIncident", "");
		edtfireinc.setText(saveFireIncident);
		Log.d("Load Preferences", "success...");

	}
*/

	/*
	 * @Override public void onBackPressed() { //
	 * 
	 * saveLicence("saveLicence", edtlicence.getText().toString());
	 * saveFullName("saveFullName", edtfullname.getText().toString());
	 * saveAddress("saveAddress", edtaddress.getText().toString());
	 * saveContact("saveContact", edtcontactnumber.getText().toString());
	 * saveRegistration("saveRegistration", edtregno.getText().toString());
	 * saveCarMake("saveCarMake", edtcarmake.getText().toString());
	 * saveCarModel("saveCarModel", edtcarmodel.getText().toString());
	 * saveInsuranceModel("saveInsuranceModel",
	 * edtinsuranceco.getText().toString());
	 * savePoliceIncident("savePoliceIncident",
	 * edtpoliceinc.getText().toString()); saveFireIncident("saveFireIncident",
	 * edtfireinc.getText().toString());
	 * 
	 * // saveSCal("Set_Cal", s_cal.toString()); // this.finish(); //
	 * startActivity(new Intent(getApplicationContext(), Main_CrashNote.class));
	 * }
	 */

	/*
	 * // method to check you have a Camera private boolean hasCamera() { return
	 * getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA); }
	 * 
	 * // method to check you have Camera Apps private boolean
	 * hasDefualtCameraApp(String action) { final PackageManager packageManager
	 * = getPackageManager(); final Intent intent = new Intent(action);
	 * List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
	 * PackageManager.MATCH_DEFAULT_ONLY);
	 * 
	 * return list.size() > 0;
	 * 
	 * }
	 */
	public void getDate() {
		now = new Date();
		strNow = new SimpleDateFormat("yyyy/M/dd\n HH:mm:ss").format(now);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.imageButton1:
			cal = Calendar.getInstance();
			day = cal.get(Calendar.DAY_OF_MONTH);
			month = cal.get(Calendar.MONTH);
			year = cal.get(Calendar.YEAR);
			edtdate = (EditText) findViewById(R.id.edtDate);
			showDialog(0);

			/*
			 * Intent intent = new Intent(getApplicationContext(),
			 * SQliteDatabaseDemoActivity.class); startActivity(intent);
			 */
			break;
		case R.id.tapphoto1:

			// create intent with ACTION_IMAGE_CAPTURE action
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			getDate();

			// to save picture remove comment
			/*
			 * file = new File(Environment.getExternalStorageDirectory(),
			 * "/CrashNote/" + strNow + jbg); photoPath11 = Uri.fromFile(file);
			 * // intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
			 */
			intent.putExtra("data", photoPath);
			// start camera activity
			startActivityForResult(intent, TAKE_PICTURE);
			break;
		case R.id.tapphoto2:

			// create intent with ACTION_IMAGE_CAPTURE action
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			getDate();
			// to save picture remove comment
			/*
			 * file = new File(Environment.getExternalStorageDirectory(),
			 * "/CrashNote/" + strNow + jbg); photoPath12 = Uri.fromFile(file);
			 * // intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
			 */
			intent.putExtra("data", photoPath);
			// start camera activity
			startActivityForResult(intent, TAKE_PICTURE1);
			break;
		case R.id.tapphoto3:

			// create intent with ACTION_IMAGE_CAPTURE action
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			getDate();
			// to save picture remove comment
			/*
			 * file = new File(Environment.getExternalStorageDirectory(),
			 * "/CrashNote/" + strNow + jbg); photoPath13 = Uri.fromFile(file);
			 * // intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
			 */
			intent.putExtra("data", photoPath);
			// start camera activity
			startActivityForResult(intent, TAKE_PICTURE2);
			break;
		case R.id.tapphoto4:

			// create intent with ACTION_IMAGE_CAPTURE action
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			getDate();
			// to save picture remove comment
			/*
			 * file = new File(Environment.getExternalStorageDirectory(),
			 * "/CrashNote/" + strNow + jbg); photoPath14 = Uri.fromFile(file);
			 * // intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
			 */
			intent.putExtra("data", photoPath);
			// start camera activity
			startActivityForResult(intent, TAKE_PICTURE3);
			break;
		}
	}

	// The Android Camera application encodes the photo in the return Intent
	// delivered to onActivityResult()
	// as a small Bitmap in the extras, under the key "data"
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK
				&& intent != null) {
			// get bundle
			Bundle extras = intent.getExtras();

			// get
			bitMap = (Bitmap) extras.get("data");
			imgView1.setImageBitmap(bitMap);

		}
		if (requestCode == TAKE_PICTURE1 && resultCode == RESULT_OK
				&& intent != null) {
			// get bundle
			Bundle extras = intent.getExtras();

			// get
			bitMap = (Bitmap) extras.get("data");
			imgView2.setImageBitmap(bitMap);

		}
		if (requestCode == TAKE_PICTURE2 && resultCode == RESULT_OK
				&& intent != null) {
			// get bundle
			Bundle extras = intent.getExtras();

			// get
			bitMap = (Bitmap) extras.get("data");
			imgView3.setImageBitmap(bitMap);

		}
		if (requestCode == TAKE_PICTURE3 && resultCode == RESULT_OK
				&& intent != null) {
			// get bundle
			Bundle extras = intent.getExtras();

			// get
			bitMap = (Bitmap) extras.get("data");
			imgView4.setImageBitmap(bitMap);

		}

	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			edtdate.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
					+ selectedYear);
		}
	};

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
