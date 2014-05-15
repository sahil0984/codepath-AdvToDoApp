package com.forCodePath.todoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<TodoListModel> implements OnClickListener {

	private TodoActivity activity;
	//TodoListModel[] modelItems = null;
	private ArrayList<TodoListModel> modelItems;
	private final int REQUEST_CODE = 20;

	Context context;

	public CustomAdapter(Context context, ArrayList<TodoListModel> resource) {
		super(context,R.layout.lv_row,resource);
        activity = (TodoActivity) context;
		this.context = context;
		this.modelItems = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		View rowView = inflater.inflate(R.layout.lv_row, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.tvItem);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.ivMarkDone);

	    name.setText(modelItems.get(position).getTodoItem());

	    if(modelItems.get(position).getMarkDone() == -1)
	    	imageView.setImageResource(R.drawable.status_red);
	    else if(modelItems.get(position).getMarkDone() == 0)
	    	imageView.setImageResource(R.drawable.status_grey);
	    else
	    	imageView.setImageResource(R.drawable.status_green);     
  
	    rowView.setOnClickListener(new OnItemClickListener( position ));
	    
		return rowView;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Log.v("CustomAdapter", "=====Row button clicked=====");
	}
	
    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener{
        private int mPosition;

        OnItemClickListener(int position){
             mPosition = position;
        }
         
        @Override
        public void onClick(View arg0) {

   

         /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

//            if (mId==0) {
//            	sct.onItemClick(mPosition);
//            	Log.v("CustomAdapter", "Text clicked"+arg0.getId());
//            } else if (mId==1) {
//            	Log.v("CustomAdapter", "Image clicked"+arg0.getId());
//            }
        	
//            if (arg0.getId() == "tvItem") {
//            	sct.onItemClick(mPosition);
//            } else if (mId==1) {
//            	Log.v("CustomAdapter", "Image clicked");
            
            TextView tv = (TextView) (arg0.findViewById(R.id.tvItem));
            if (tv != null) {
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    	TodoActivity sct = (TodoActivity)activity;
                    	sct.onItemClick(mPosition);
                    	Log.v("CustomAdapter", "Text clicked");
                    }
                });
            } else {
            	Log.v("CustomAdapter", "Error");
            }
            
            ImageView iv = (ImageView) (arg0.findViewById(R.id.ivMarkDone));
            if (iv != null) {
                iv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    	TodoActivity sct = (TodoActivity)activity;
                    	sct.onStatusClick(mPosition);
                    	Log.v("CustomAdapter", "Image clicked");
                    }
                });
            } else {
            	Log.v("CustomAdapter", "Error");
            }
            
            }
        }               
    }
	
//    public void onClick(View arg0) {
//  	    int position = arg0.getTag() instanceof Integer ? ((Integer)arg0.getTag()).intValue() : -1;
//
//		  Intent i = new Intent(context, EditItemActivity.class);
//		  i.putExtra("item", modelItems.get(position).getTodoItem().toString());
//		  i.putExtra("status", modelItems.get(position).getMarkDone()); 
//		  i.putExtra("pos", position); 
//		  //startActivityForResult(i, REQUEST_CODE);	
//		  context.startActivity(i);
//    }
