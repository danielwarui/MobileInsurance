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
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Verify_Policy extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	Button submit, cancel;
	EditText plate, policy;
	public static String s_cal;
	public static String NumberPlate, PolicyNumber = "";
	private static String KEY_SUCCESS = "success";
	private static String KEY_CID = "Cust_id";
	String status, cid = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verify_policy);
		TextView t = (TextView) findViewById(R.id.title_text);
		t.setText("Claim - Policy Details");

		plate = (EditText) findViewById(R.id.plate);
		policy = (EditText) findViewById(R.id.policy);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.cancel) {
			this.finish();
			Intent back = new Intent(this, MainActivity.class);
			startActivity(back);
		} else if (v.getId() == R.id.submit) {
			NumberPlate = plate.getText().toString();
			PolicyNumber = policy.getText().toString();
			if (NumberPlate.equals("")) {
				plate.setError("Number Plate is required");
			} else if (PolicyNumber.equals("")) {
				policy.setError("Policy Number is required");
			} else {
				checkInternetConnection();
			}
		}
	}

	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			new VerifyPolicy().execute();
			return true;
		} else {
			Toast.makeText(
					getBaseContext(),
					"No Internet Connection. Please check your internet setttings",
					Toast.LENGTH_LONG).show();
			return false;
		}
	}

	private class VerifyPolicy extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		JSONObject json;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(Verify_Policy.this, "",
					"Verifying Policy. Please wait...", true);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// String pass = "testing";
			UserFunctions userFunction = new UserFunctions();
			json = userFunction.verifyPolicy(PolicyNumber, NumberPlate);
			try {
				if (json.get(KEY_SUCCESS) != null) {
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 1) {
						status = "1";
						cid = json.getString(KEY_CID);
						saveCID("CID", cid);
						savePolicyId("POLICY_ID", PolicyNumber);
					} else {
						status = "0";
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
				Toast.makeText(getBaseContext(),
						"Policy Verification Successful", Toast.LENGTH_LONG)
						.show();
				Verify_Policy.this.finish();
				startActivity(new Intent(getApplicationContext(),
						Police_Abstract.class));
			} else if (status == "0") {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						Verify_Policy.this).create();
				alertDialog.setCancelable(false);
				alertDialog.setTitle("POLICY VERIFICATION");
				alertDialog.setMessage("Policy Number " + PolicyNumber
						+ " Does not exist");
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								alertDialog.dismiss();
							}
						});
				alertDialog.show();
			}
		}
	}

	private void saveCID(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void savePolicyId(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	@Override
	public void onBackPressed() {
		this.finish();
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}
}