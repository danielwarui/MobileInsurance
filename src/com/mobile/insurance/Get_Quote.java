package com.mobile.insurance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Get_Quote extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	Button next, cancel;
	EditText sn, fn, on, mob, id, em, kra;
	public static String s_cal;
	int yr, month, day;
	public static String Surname, Firstname, Othername, Mobile, Email, Id,
			KRAPIN = "";
	static final int DATE_DIALOG = 999;
	String smonth, sday;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_quote);
		TextView t = (TextView) findViewById(R.id.title_text);
		t.setText("Registration - Personal Information");
		
		Log.i("Oncreate", "findView by ID EditText");
		
		sn = (EditText) findViewById(R.id.surname);
		fn = (EditText) findViewById(R.id.firstname);
		on = (EditText) findViewById(R.id.othernames);
		id = (EditText) findViewById(R.id.id);
		em = (EditText) findViewById(R.id.email);
		kra = (EditText) findViewById(R.id.kra);
		mob = (EditText) findViewById(R.id.mobile);
		next = (Button) findViewById(R.id.next);
		
		next.setOnClickListener(this);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		loadPreferences();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.cancel) {
			saveSurname("P_SURNAME", sn.getText().toString());
			saveFirstname("P_FIRSTNAME", fn.getText().toString());
			saveOthername("P_OTHERNAME", on.getText().toString());
			saveMobile("P_MOBILE", mob.getText().toString());
			saveEmail("P_EMAIL", em.getText().toString());
			saveId("P_ID", id.getText().toString());
			this.finish();
			Intent back = new Intent(this, MainActivity.class);
			startActivity(back);
		} else if (v.getId() == R.id.next) {
			Log.i("Button Next", "EditText.toString");

			Surname = sn.getText().toString();
			Firstname = fn.getText().toString();
			Othername = on.getText().toString();
			Mobile = mob.getText().toString();
			Email = em.getText().toString();
			Id = id.getText().toString();
			KRAPIN = kra.getText().toString();
			if (Surname.equals("")) {
				sn.setError("Surname is required");
			}
			if (Firstname.equals("")) {
				fn.setError("First Name is required");
			}
			if (Id.equals("")) {
				id.setError("ID number required");
			}
			if (Mobile.equals("")) {
				id.setError("Mobile number required");
			}
			if (Email.equals("")) {
				em.setError("Email is required");
			}
			if (KRAPIN.equals("")) {
				kra.setError("KRA PIN is required");
			} else if (!validMail(Email)) {
				em.setError("This email address is invalid");
			} else {
				saveSurname("P_SURNAME", sn.getText().toString());
				saveFirstname("P_FIRSTNAME", fn.getText().toString());
				saveOthername("P_OTHERNAME", on.getText().toString());
				saveMobile("P_MOBILE", mob.getText().toString());
				saveEmail("P_EMAIL", em.getText().toString());
				saveId("P_ID", id.getText().toString());
				saveKRA("KRA_PIN", kra.getText().toString());
				this.finish();
				Intent next = new Intent(this, Vehicle_Info.class);
				startActivity(next);
			}
		}
	}

	private boolean validMail(String yourEmailString) {
		Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher emailMatcher = emailPattern.matcher(Email);
		return emailMatcher.matches();
	}

	private void saveSurname(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveFirstname(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveOthername(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveMobile(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveId(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveEmail(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void saveKRA(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void loadPreferences() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String SN = pref.getString("P_SURNAME", "");
		sn.setText(SN);
		String FN = pref.getString("P_FIRSTNAME", "");
		fn.setText(FN);
		String ON = pref.getString("P_OTHERNAME", "");
		on.setText(ON);
		String MOB = pref.getString("P_MOBILE", "");
		mob.setText(MOB);
		String EML = pref.getString("P_EMAIL", "");
		em.setText(EML);
		String ID = pref.getString("P_ID", "");
		id.setText(ID);
		String PIN = pref.getString("KRA_PIN", "");
		kra.setText(PIN);
	}

	@Override
	public void onBackPressed() {
		saveSurname("P_SURNAME", sn.getText().toString());
		saveFirstname("P_FIRSTNAME", fn.getText().toString());
		saveOthername("P_OTHERNAME", on.getText().toString());
		saveMobile("P_MOBILE", mob.getText().toString());
		saveId("P_ID", id.getText().toString());
		saveKRA("KRA_PIN", kra.getText().toString());
		// saveSCal("Set_Cal", s_cal.toString());
		this.finish();
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}
}