package com.mobile.insurance;

import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.crashnote.CrashNote_Activity;
import com.example.crashnote.sqlitedb.DBHandler;
import com.mobile.insurance.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Main_CrashNote extends Activity {
	/** Get The current Date*/
	Date now = new Date();
	String strNow = new SimpleDateFormat("yyyy/MM/dd\n HH:mm:ss").format(now);
	List myList;
	
	/** Items entered by the user is stored in this ArrayList variable */
	ArrayList<String> list = new ArrayList<String>();
	/** Declaring an ArrayAdapter to set items to ListView */
	ArrayAdapter<String> adapter;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        /** Setting a custom layout for the list activity */
        setContentView(R.layout.main);
        
        /** Reference to the button of the layout main.xml */
        Button btn = (Button) findViewById(R.id.btnAdd);
        final ListView listers = (ListView) findViewById(R.id.list);
        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        
        /** Defining a click event listener for the button "Add" */
        OnClickListener listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {		
				
				adapter.notifyDataSetChanged();
				Intent intent = new Intent(getApplicationContext(),CrashNote_Activity.class);
				startActivity(intent);
			}
		};
		listers.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0,
                    View arg1, final int position, long arg3) {
                final AlertDialog.Builder b = new AlertDialog.Builder(
                        Main_CrashNote.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);
                b.setMessage("Delete this from history?");
                b.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                            	
                            	DBHandler db =new DBHandler(getApplicationContext());
                            	db.removeByDate(position);
                                adapter.notifyDataSetChanged();
                                adapter.notifyDataSetInvalidated();
                            	Toast.makeText(getApplicationContext(),
                                        "Yes", Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                b.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                dialog.cancel();
                            }
                        });

                b.show();
                return true;
            }

			
        });
		/** Setting the event listener for the add button */
        btn.setOnClickListener(listener);
        DBHandler db = new DBHandler(getApplicationContext());

		List<String> lables = db.getAllLabels();

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lables);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		listers.setAdapter(dataAdapter);
	/*	listers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});*/

    	   
    }
    public void getDate(){
    	now = new Date();
    	strNow = new SimpleDateFormat("yyyy/MM/dd\n HH:mm:ss").format(now);
    }
    
}