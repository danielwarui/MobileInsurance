package com.mobile.insurance;

import http.functions.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.camera.functions.CropOption;
import com.camera.functions.CropOptionAdapter;

public class Upload extends Activity implements OnClickListener {
	private Uri mImageCaptureUri;
	private ImageView mImageView;
	Button next;
	Bitmap photo;
	ImageButton help;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	public static String ba1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);

Log.i("Failed", "resultCode is not Result_OK");			
//uploading log book photo
		TextView t = (TextView) findViewById(R.id.title_text);
		t.setText("Upload Picture");

Log.i("Failed", "resultCode is not Result_OK");			
//		String[] items
		final String[] items = new String[] { "Take from camera",
				"Select from gallery" };

Log.i("Failed", "resultCode is not Result_OK");			
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, items);

Log.i("Failed", "resultCode is not Result_OK");			
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

Log.i("Failed", "resultCode is not Result_OK");			
		builder.setTitle("Select Image");

Log.i("Failed", "resultCode is not Result_OK");			
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int item) { // pick from
																	// camera

Log.i("Failed", "resultCode is not Result_OK");			
				if (item == 0) {

Log.i("Failed", "resultCode is not Result_OK");			
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

Log.i("Failed", "resultCode is not Result_OK");			
					mImageCaptureUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), "tmp_avatar_"
							+ String.valueOf(System.currentTimeMillis())
							+ ".jpg"));

Log.i("Failed", "resultCode is not Result_OK");			
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
							mImageCaptureUri);

Log.i("Failed", "resultCode is not Result_OK");			
					try {
						intent.putExtra("return-data", true);
//							pick from camera = 1;

Log.i("Failed", "resultCode is not Result_OK");			
						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();

Log.i("Failed", "resultCode is not Result_OK");			
					}
				} else { // pick from file
					Intent intent = new Intent();

Log.i("Failed", "resultCode is not Result_OK");			

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

Log.i("Failed", "resultCode is not Result_OK");			

					startActivityForResult(Intent.createChooser(intent,
//							pick from file = 3;
							"Complete action using"), PICK_FROM_FILE);

Log.i("Failed", "resultCode is not Result_OK");			
				}
			}
		});

		final AlertDialog dialog = builder.create();

Log.i("Failed", "resultCode is not Result_OK");			
		Button button = (Button) findViewById(R.id.btn_crop);
		mImageView = (ImageView) findViewById(R.id.iv_photo);

Log.i("Failed", "resultCode is not Result_OK");			

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

Log.i("Failed", "resultCode is not Result_OK");			
				dialog.show();

Log.i("Failed", "resultCode is not Result_OK");			
			}
		});
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (photo == null) {

Log.i("Failed", "resultCode is not Result_OK");			
					Toast.makeText(getApplicationContext(),
							"Please select image", Toast.LENGTH_SHORT).show();
				} else {
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					photo.compress(Bitmap.CompressFormat.JPEG, 90, bao);
					byte[] ba = bao.toByteArray();
					ba1 = Base64.encodeBytes(ba);
					saveImage("P_IMAGE", ba1);

Log.i("Failed", "resultCode is not Result_OK");			
					Upload.this.finish();
					startActivity(new Intent(getApplicationContext(),
							Insurance_Price.class));
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
//		pick from camera =1;
		/* from camera*/
		case PICK_FROM_CAMERA:

Log.i("Failed", "resultCode is not Result_OK");			
			doCrop();

			break;
//			pick from file =3;.
			/*from gallery*/
		case PICK_FROM_FILE:

Log.i("Failed", "resultCode is not Result_OK");			
			mImageCaptureUri = data.getData();

			doCrop();

			break;
			
			/*from cropping photo*/
//			crop from camera =2;
		case CROP_FROM_CAMERA:

Log.i("Failed", "resultCode is not Result_OK");			
			Bundle extras = data.getExtras();

			if (extras != null) {
				photo = extras.getParcelable("data");

				mImageView.setImageBitmap(photo);

Log.i("Failed", "resultCode is not Result_OK");			
			}

			File f = new File(mImageCaptureUri.getPath());

//			deletes the remaining file
			if (f.exists())
				f.delete();

			break;

		}
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, 0);

		int size = list.size();

		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();

			return;
		} else {

Log.i("Failed", "resultCode is not Result_OK");			
			intent.setData(mImageCaptureUri);

			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

Log.i("Failed", "resultCode is not Result_OK");			
			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));

				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(
						getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);

Log.i("Failed", "resultCode is not Result_OK");			
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int item) {
								startActivityForResult(
										
										cropOptions.get(item).appIntent,
										CROP_FROM_CAMERA);

Log.i("Failed", "resultCode is not Result_OK");			
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (mImageCaptureUri != null) {
							getContentResolver().delete(mImageCaptureUri, null,
									null);
							mImageCaptureUri = null;
						}
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}

	private void saveImage(String key, String value) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	@Override
	public void onBackPressed() {

Log.i("Failed", "resultCode is not Result_OK");			
		this.finish();
		startActivity(new Intent(getApplicationContext(), Vehicle_Info.class));
	}
}