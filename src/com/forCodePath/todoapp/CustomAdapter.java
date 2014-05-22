package com.forCodePath.todoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<TodoListModel> {

	private TodoActivity activity;
	private ArrayList<TodoListModel> modelItems;

	Context context;

	public CustomAdapter(Context context, ArrayList<TodoListModel> resource) {
		super(context,R.layout.lv_row,resource);
        activity = (TodoActivity) context;
		this.context = context;
		this.modelItems = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.lv_row, parent, false);			
		}
		
		TextView item = (TextView) convertView.findViewById(R.id.tvItem);
		TextView itemDetails = (TextView) convertView.findViewById(R.id.tvItemDetails);
	    ImageView imageView = (ImageView) convertView.findViewById(R.id.ivMarkDone);

	    item.setText(modelItems.get(position).getTodoItem());
	    String textItemDetails = modelItems.get(position).getTodoItemDetails();
	    String substringItemDetails = textItemDetails.length()>30? (textItemDetails.substring(0, 30)+"...") : textItemDetails;
	    itemDetails.setText(substringItemDetails);

	    if(modelItems.get(position).getMarkDone() == -1)
	    	imageView.setImageResource(R.drawable.status_red);
	    else if(modelItems.get(position).getMarkDone() == 0)
	    	imageView.setImageResource(R.drawable.status_grey);
	    else
	    	imageView.setImageResource(R.drawable.status_green);     
  
	    item.setTag(new Integer(position));
	    item.setOnClickListener(new OnClickListener() { 
	    	@Override
            public void onClick(View v) {
            	Integer myPosition = (Integer) v.getTag();
            	TodoActivity sct = (TodoActivity)activity;
            	sct.onItemClick(myPosition);
            	Log.v("CustomAdapter", "Text clicked");
            }
	    });
	    itemDetails.setTag(new Integer(position));
	    itemDetails.setOnClickListener(new OnClickListener() { 
	    	@Override
            public void onClick(View v) {
            	Integer myPosition = (Integer) v.getTag();
            	TodoActivity sct = (TodoActivity)activity;
            	sct.onItemClick(myPosition);
            	Log.v("CustomAdapter", "Text clicked");
            }
	    });
	    
	    imageView.setTag(new Integer(position));
	    imageView.setOnClickListener(new OnClickListener() { 
	    	@Override
            public void onClick(View v) {
            	Integer myPosition = (Integer) v.getTag();
            	TodoActivity sct = (TodoActivity)activity;
            	sct.onStatusClick(myPosition);
            	Log.v("CustomAdapter", "Image clicked");
            }
	    });
	    
		return convertView;

	}
}