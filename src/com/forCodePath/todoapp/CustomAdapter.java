package com.forCodePath.todoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<TodoListModel>{

	//TodoListModel[] modelItems = null;
	ArrayList<TodoListModel> modelItems;

	Context context;

	public CustomAdapter(Context context, ArrayList<TodoListModel> resource) {
		super(context,R.layout.lv_row,resource);
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
	    	imageView.setImageResource(R.drawable.ic_launcher);
	    else if(modelItems.get(position).getMarkDone() == 0)
	    	imageView.setImageResource(R.drawable.ic_launcher);
	    else
	    	imageView.setImageResource(R.drawable.ic_launcher);
	    	
	    
		return rowView;
	}
	
}