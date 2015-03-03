package com.mobile.insurance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Vehicle_Info extends Activity implements OnClickListener {
	Button next, cancel;
	EditText model, yom, plate, price;
	public static String Model, Year, Plate, Price = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_info);
		TextView t = (TextView) findViewById(R.id.title_text);
		t.setText("Registration - Vehicle Information");

		model = (EditText) findViewById(R.id.model);
		yom = (EditText) findViewById(R.id.yom);
		plate = (EditText) findViewById(R.id.plate);
		price = (EditText) findViewById(R.id.price);
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		loadPreferences();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.cancel) {
			saveModel("P_MODEL", model.getText().toString());
			saveYear("P_YEAR", yom.getText().toString());
			savePlate("P_PLATE", plate.getText().toString());
			savePrice("P_PRICE", price.getText().toString());
			this.finish();
			Intent back = new Intent(this, Get_Quote.class);
			startActivity(back);
		} else if (v.getId() == R.id.next) {
			Model = model.getText().toString();
			Year = yom.getText().toString();
			Plate = plate.getText().toString();
			Price = price.getText().toString();
			if (Model.equals("")) {
				model.setError("Model is required");
			}
			if (Year.equals("")) {
				yom.setError("Year of manufacture is required");
			}
			if (Plate.equals("")) {
				plate.setError("Number plate is required");
			}
			if (Price.equals("")) {
				price.setError("Initial price required");
			} else {
				saveModel("P_MODEL", model.getText().toString());
				saveYear("P_YEAR", yom.getText().toString());
				savePlate("P_PLATE", plate.getText().toString());
				savePrice("P_PRICE", price.getText().toString());
				this.finish();
				Intent next = new Intent(this, Upload.class);
				startActivity(next);
			}
		}
	}

	private void saveModel(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveYear(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void savePlate(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void savePrice(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void loadPreferences() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String MODEL = pref.getString("P_MODEL", "");
		model.setText(MODEL);
		String YEAR = pref.getString("P_YEAR", "");
		yom.setText(YEAR);
		String PLATE = pref.getString("P_PLATE", "");
		plate.setText(PLATE);
		String PRICE = pref.getString("P_PRICE", "");
		price.setText(PRICE);
	}

	@Override
	public void onBackPressed() {
		saveModel("P_MODEL", model.getText().toString());
		saveYear("P_YEAR", yom.getText().toString());
		savePlate("P_PLATE", plate.getText().toString());
		savePrice("P_PRICE", price.getText().toString());
		this.finish();
		startActivity(new Intent(getApplicationContext(), Get_Quote.class));
	}
}