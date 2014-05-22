package com.forCodePath.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	private EditText etEditItem;
	private EditText etEditItemDetails;
	private int status;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItemDetails = (EditText) findViewById(R.id.etEditItemDetails);

		String editItem = getIntent().getStringExtra("item");
		String editItemDetails = getIntent().getStringExtra("details");
		status = getIntent().getIntExtra("status", 0);
		position = getIntent().getIntExtra("pos", 0);
    	etEditItem.setText(editItem);
    	etEditItemDetails.setText(editItemDetails);    	
    	etEditItem.setSelection(etEditItem.getText().length());
    	etEditItem.setFocusableInTouchMode(true);
    	etEditItem.requestFocus();
	}

	public void onSavedItem(View v) {
		EditText etItem = (EditText) findViewById(R.id.etEditItem);
		EditText etItemDetails = (EditText) findViewById(R.id.etEditItemDetails);
    	Intent data = new Intent();
    	// Pass relevant data back as a result
    	data.putExtra("item", etItem.getText().toString());
    	data.putExtra("details", etItemDetails.getText().toString());
    	data.putExtra("status", status);
    	data.putExtra("pos", position);
    	setResult(RESULT_OK, data); // set result code and bundle data for response
    	finish(); // closes the activity, pass data to parent
	}
	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
