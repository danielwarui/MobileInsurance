package com.mobile.insurance;

import http.functions.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Insurance_Price extends Activity implements OnClickListener {
	Button next, cancel;
	double tt;
	double ins_rate = 0.07;
	TextView model, yom, price, plate, rate, total;
	public static String Model, Year, Price, Plate, Total, Surname, First,
			Last, Mobile, Email, KRA, National_ID, Image = "";
	public static String iframe;
	private static String KEY_SUCCESS = "success";
	private static String KEY_REDIRECT = "redirect";
	String status = "";
	SharedPreferences pref;
	int Tot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insurance_price);
		TextView t = (TextView) findViewById(R.id.title_text);
		t.setText("Registration - Insurance Price");

		model = (TextView) findViewById(R.id.model);
		yom = (TextView) findViewById(R.id.yom);
		rate = (TextView) findViewById(R.id.rate);
		price = (TextView) findViewById(R.id.price);
		plate = (TextView) findViewById(R.id.plate);
		total = (TextView) findViewById(R.id.total);
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		loadPreferences();
		tt = Integer.valueOf(Price) * ins_rate;
		Tot = (int) tt;
		Total = Integer.valueOf(Tot).toString();
		model.setText("Vehicle Model: " + Model);
		plate.setText("Number Plate: " + Plate);
		yom.setText("Year of Manufacture: " + Year);
		price.setText("Purchase Price: " + Price);
		rate.setText("Insurance Rate: 7%");
		total.setText("Total Insurance Payable: Kshs." + Total);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.cancel) {
			this.finish();
			Intent back = new Intent(this, MainActivity.class);
			startActivity(back);
		} else if (v.getId() == R.id.next) {
			checkInternetConnection();
		}
	}

	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			new SendTask().execute();
			return true;
		} else {
			Toast.makeText(
					getBaseContext(),
					"No Internet Connection. Please check your internet setttings",
					Toast.LENGTH_LONG).show();
			return false;
		}
	}

	private class SendTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		JSONObject json;
		AlertDialog.Builder alt_bld;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(Insurance_Price.this, "",
					"Registration in progress. Please wait...", true);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// String pass = "testing";
			UserFunctions userFunction = new UserFunctions();
			json = userFunction.getQuote(Surname, First, Last, Mobile, Total,
					Email, KRA, National_ID, Model, Year, Price, Plate, Image);
			try {
				if (json.get(KEY_SUCCESS) != null) {
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 100) {
						iframe = json.getString(KEY_REDIRECT);
						status = "1";
					}
				}
			} catch (JSONException e) {
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		@SuppressWarnings("deprecation")
		@SuppressLint("SetJavaScriptEnabled")
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			if (status == "1") {
				pref = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				// Editor editor = pref.edit();
				// editor.clear();
				// editor.commit();
				alt_bld = new AlertDialog.Builder(Insurance_Price.this);
				alt_bld.setMessage(
						"You have successfully registered for insurance. Do you want to proceed to payment?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										Insurance_Price.this.finish();
										startActivity(new Intent(
												getApplicationContext(),
												Payments.class));
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										Insurance_Price.this.finish();
										startActivity(new Intent(
												getApplicationContext(),
												MainActivity.class));
									}
								});
				AlertDialog alert = alt_bld.create();
				alert.setTitle("POLICY CREATION");
				alert.show();
			}
		}
	}

	private void loadPreferences() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Model = pref.getString("P_MODEL", "");
		Year = pref.getString("P_YEAR", "");
		Price = pref.getString("P_PRICE", "");
		Plate = pref.getString("P_PLATE", "");
		First = pref.getString("P_FIRSTNAME", "");
		Surname = pref.getString("P_SURNAME", "");
		Last = pref.getString("P_OTHERNAME", "");
		Mobile = pref.getString("P_MOBILE", "");
		Email = pref.getString("P_EMAIL", "");
		KRA = pref.getString("KRA_PIN", "");
		National_ID = pref.getString("P_ID", "");
		Image = pref.getString("P_IMAGE", "");
	}

	@Override
	public void onBackPressed() {
		this.finish();
		startActivity(new Intent(getApplicationContext(), Vehicle_Info.class));
	}
}