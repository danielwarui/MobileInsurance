package com.mobile.insurance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Payments extends Activity implements OnClickListener {
	WebView web;
	ProgressBar progressBar;
	String iframe = com.mobile.insurance.Insurance_Price.iframe;
	SharedPreferences pref;
	Editor editor;
	ImageView seperator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		// pref = PreferenceManager.getDefaultSharedPreferences(this);
		// editor = pref.edit();
		// editor.clear();
		// editor.commit();
		// Db.resetTables();

		seperator = (ImageView) findViewById(R.id.seperator);
		seperator.setVisibility(View.GONE);

		String start = "<html><body>";
		String end = "</body></html>";
		String page;
		page = start + iframe + end;
		web = (WebView) findViewById(R.id.webview01);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		web.setWebViewClient(new myWebClient());
		web.getSettings().setJavaScriptEnabled(true);
		// String customHtml =
		// "<iframe src='http://docs.google.com/viewer?url=http://www.iasted.org/conferences/formatting"
		// +
		// "/presentations-tips.ppt&embedded=true' width='100%' height='100%' style='border: none;'></iframe>";
		// web.loadData(customHtml, "text/html", "UTF-8");
		web.loadDataWithBaseURL(null, page, "text/html", "UTF-8", null);
		// web.loadDataWithBaseURL(null, iframe, "text/html", "UTF-8", null);

	}

	public class myWebClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("You will be redirected back to main page")
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Payments.this.finish();
						startActivity(new Intent(getApplicationContext(),
								MainActivity.class));
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		alert.setTitle("Exit Payments!");
		alert.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}