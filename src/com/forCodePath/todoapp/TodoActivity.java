package com.forCodePath.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	private ArrayList<TodoListModel> modelItems;

	//public TodoActivity CustomListView = null;
	
	private CustomAdapter todoAdapter;
	private ListView lvItems;
		//private TextView tvItem;
		//private ImageView ivStatus;
	private EditText etNewItem;
	private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        //lvItems.setOnItemClickListener(new OnItemClickListener() {
		//	@Override
		//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		//			long arg3) {
		//		Toast.makeText(TodoActivity.this, "Hello", Toast.LENGTH_SHORT).show();
		//	}
		//});
        //tvItem = (TextView) findViewById(R.id.tvItem);
        //ivStatus = (ImageView) findViewById(R.id.ivMarkDone);
        
        readItems();

        //CustomListView = this;
        
        todoAdapter = new CustomAdapter(this, modelItems);
	
        //todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
        //To add List of Lists
//        List<List<Individual>> group = new ArrayList<List<Individual>>(4);

    }


    private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				modelItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}

		});
	}
  
	public void onItemClick(int mPosition) {       
		  Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
		  i.putExtra("item", todoAdapter.getItem(mPosition).getTodoItem().toString());
		  i.putExtra("details", todoAdapter.getItem(mPosition).getTodoItemDetails().toString());
		  i.putExtra("status", todoAdapter.getItem(mPosition).getMarkDone()); 
		  i.putExtra("pos", mPosition); 
		  startActivityForResult(i, REQUEST_CODE);
	}
	public void onStatusClick(int mPosition) {
		int newStatus = todoAdapter.getItem(mPosition).getMarkDone();
		String tmpItem = todoAdapter.getItem(mPosition).getTodoItem().toString();
		String tmpItemDetails = todoAdapter.getItem(mPosition).getTodoItemDetails().toString();

		newStatus = newStatus + 1;
		if (newStatus==2) {
			newStatus = -1;
		}
    	Log.v("TodoActivity", "New Status"+newStatus);
		modelItems.set(mPosition, new TodoListModel(tmpItem, tmpItemDetails, newStatus));
		todoAdapter.notifyDataSetChanged();
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    	     // Extract name value from result extras
    		String itemName = data.getExtras().getString("item");
    		String itemDetails = data.getExtras().getString("details");
    	    int status = data.getExtras().getInt("status");
    	    int pos = data.getExtras().getInt("pos");
   	     
    	    modelItems.set(pos, new TodoListModel(itemName, itemDetails, status));
		    todoAdapter.notifyDataSetChanged();
			writeItems();
    	}
    }
    
    public void onAddedItem(View v) {
    	String itemText = etNewItem.getText().toString();
    	String itemDetailsText = "";
    	TodoListModel addItem = new TodoListModel(itemText, itemDetailsText, -1); //Default status = not started
    	modelItems.add(addItem);
		todoAdapter.notifyDataSetChanged();

    	etNewItem.setText("");
    	writeItems();
	}
    
    public void onSortItems(View v) {
    	
    	ArrayList<TodoListModel> statusRed = new ArrayList<TodoListModel>(); //-1
    	ArrayList<TodoListModel> statusGreen = new ArrayList<TodoListModel>(); //1
    	ArrayList<TodoListModel> statusGrey = new ArrayList<TodoListModel>(); //0
    	
    	int listIndx = modelItems.size();
    	
    	for (int i=0; i<listIndx; i++) {
    		int itemStatus = modelItems.get(i).getMarkDone();
    		String itemName = modelItems.get(i).getTodoItem().toString();
    		String itemDetails = modelItems.get(i).getTodoItemDetails().toString();

    		TodoListModel listItem = new TodoListModel(itemName, itemDetails, itemStatus);
    		if ( itemStatus == -1 ) {
    			statusRed.add(listItem);
    		} else if ( itemStatus == 1 ) {
    			statusGreen.add(listItem);
    		} else {
    			statusGrey.add(listItem);
    		}
    	} 	
    	
    	modelItems.clear();

    	modelItems.addAll(statusGrey);
    	modelItems.addAll(statusRed);
    	modelItems.addAll(statusGreen);
		todoAdapter.notifyDataSetChanged();
    	writeItems();
	}
    
    public void onEmailItems(View v) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // prompts email clients only
        emailIntent.setType("message/rfc822");
        
        String emailBody = new String();
    	int listIndx = modelItems.size();
    	for (int i=0; i<listIndx; i++) {
    		int itemStatus = modelItems.get(i).getMarkDone();
    		String statusText = new String(); 
    		if (itemStatus == -1) {
    			statusText = "Not Started";
    		} else if ( itemStatus == 1 ) {
    			statusText = "Done";
    		} else {
    			statusText = "In progress";
    		}
    		emailBody = emailBody + "** " + modelItems.get(i).getTodoItem().toString() + " - " + statusText + "\n"
    			      + "\t" + modelItems.get(i).getTodoItemDetails().toString() + "\n\n";
    	}
        
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Todo List");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
        
        try {
        	// the user can choose the email client
        	startActivity(Intent.createChooser(emailIntent, "Choose an email client from..."));
        } catch (android.content.ActivityNotFoundException ex) {
        	Toast.makeText(TodoActivity.this, "No email client installed.",
        	Toast.LENGTH_LONG).show();
        }	



    }
    
    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo3.txt");
    	try {
    		modelItems = new ArrayList<TodoListModel>();
    		List<String> readLines;
    		readLines = new ArrayList<String>(FileUtils.readLines(todoFile));
    		for (String str : readLines) {
    			String tmp[] = str.split("\t");
    			String item = tmp[0];
    			String details = tmp[1];
    			int status = Integer.parseInt(tmp[2]);
    			modelItems.add(new TodoListModel(item, details, status));
    		}
		} catch (IOException e) {
			modelItems = new ArrayList<TodoListModel>();
		}
	}
    
    private void writeItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo3.txt");
    	try {
    		int listSize = modelItems.size();
    		List<String> writeLines = new ArrayList<String>();
    		for (int i=0;i<listSize;i++) {
    			String tmp = todoAdapter.getItem(i).getTodoItem().toString()+"\t"
    						+todoAdapter.getItem(i).getTodoItemDetails().toString()+"\t"
    						+todoAdapter.getItem(i).getMarkDone();
    			writeLines.add(tmp);
    		}
			FileUtils.writeLines(todoFile, writeLines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
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
