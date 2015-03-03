package com.mobile.insurance;


import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	Button quote, retrieve, pay, crash, claim, branch, contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		quote = (Button) findViewById(R.id.quote);
		quote.setOnClickListener(this);
		
		pay = (Button) findViewById(R.id.pay);
		pay.setOnClickListener(this);
		crash = (Button) findViewById(R.id.crash);
		crash.setOnClickListener(this);
		claim = (Button) findViewById(R.id.claim);
		claim.setOnClickListener(this);
		branch = (Button) findViewById(R.id.branch);
		branch.setOnClickListener(this);
		contact = (Button) findViewById(R.id.contact);
		contact.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.quote) {
			this.finish();
			startActivity(new Intent(getApplicationContext(), Get_Quote.class));
		} else if (v.getId() == R.id.claim) {
			this.finish();
			startActivity(new Intent(getApplicationContext(),
					Verify_Policy.class));
		} else if (v.getId() == R.id.pay) {
			this.finish();
			startActivity(new Intent(getApplicationContext(),
					Make_Payment.class));
		}
		else if (v.getId() == R.id.crash) {
			this.finish();
			startActivity(new Intent(getApplicationContext(), Main_CrashNote .class));
		}
		else if (v.getId() == R.id.contact) {
			this.finish();
			String phone = "+254726367047";
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
			startActivity(intent);	
		}
		else if (v.getId() == R.id.branch) {
			this.finish();
			double latitude = 40.714728;
			double longitude = -73.998672;
			String label = "ABC Label";
			String uriBegin = "geo:" + latitude + "," + longitude;
			String query = latitude + "," + longitude + "(" + label + ")";
			String encodedQuery = Uri.encode(query);
			String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
			Uri uri = Uri.parse(uriString);
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
			startActivity(intent);
			Log.i("Activity Maps", "Success");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
